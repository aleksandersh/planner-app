package io.github.aleksandersh.plannerapp.repository

import io.github.aleksandersh.plannerapp.plannerdb.dao.RecordsDao
import io.github.aleksandersh.plannerapp.plannerdb.entity.RecordEntity
import io.github.aleksandersh.plannerapp.records.model.Record
import io.github.aleksandersh.plannerapp.records.repository.RecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.distinct
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
class RecordsRepositoryImpl(
    private val recordsDao: RecordsDao
) : RecordsRepository {

    private val isChannelInitialized = AtomicBoolean(false)
    private val recordsChannel = ConflatedBroadcastChannel<List<Record>>()
    private val beforeLaunchDateSubscriptions = CopyOnWriteArrayList<BeforeLaunchDateSubscription>()

    override fun subscribeRecords(): ReceiveChannel<List<Record>> {
        if (isChannelInitialized.compareAndSet(false, true)) {
            onRecordsChanged()
        }
        return recordsChannel.openSubscription().distinct()
    }

    override fun subscribeRecordsBeforeLaunchDate(date: Date): ReceiveChannel<List<Record>> {
        val channel = Channel<List<Record>>(Channel.CONFLATED)
        val subscription = BeforeLaunchDateSubscription(date, channel)
        refreshRecordsForSubscription(subscription)
        channel.invokeOnClose { beforeLaunchDateSubscriptions.remove(subscription) }
        beforeLaunchDateSubscriptions.add(subscription)
        return channel.distinct()
    }

    override fun getRecord(id: Long): Record {
        return mapEntityToRecord(recordsDao.requireRecordById(id))
    }

    override fun updateRecord(record: Record): Record {
        val id = recordsDao.insertRecord(mapRecordToEntity(record))
        onRecordsChanged()
        onRecordsScheduleChanged()
        return record.copy(id = id)
    }

    override fun removeRecordById(id: Long) {
        recordsDao.deleteRecordById(id)
        onRecordsChanged()
        onRecordsScheduleChanged()
    }

    private fun onRecordsChanged() {
        GlobalScope.launch(Dispatchers.IO) { recordsChannel.send(getRecords()) }
    }

    private fun onRecordsScheduleChanged() {
        beforeLaunchDateSubscriptions.forEach(::refreshRecordsForSubscription)
    }

    private fun refreshRecordsForSubscription(subscription: BeforeLaunchDateSubscription) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = getRecordsBeforeLaunchDate(subscription.date)
            subscription.channel.offer(result)
        }
    }

    private fun getRecords(): List<Record> {
        return recordsDao.selectRecords().map(::mapEntityToRecord)
    }

    private fun getRecordsBeforeLaunchDate(date: Date): List<Record> {
        return recordsDao.selectRecordsByNextLaunch(date).map(::mapEntityToRecord)
    }

    private fun mapRecordToEntity(record: Record): RecordEntity {
        return RecordEntity(
            record.id,
            record.creationDate,
            record.launchDate,
            record.title,
            record.description,
            record.repeat,
            record.cycle,
            record.cycleStep
        )
    }

    private fun mapEntityToRecord(entity: RecordEntity): Record {
        return Record(
            entity.id,
            entity.creationDate,
            entity.nextLaunchDate,
            entity.title,
            entity.description,
            entity.repeat,
            entity.cycle,
            entity.cycleStep
        )
    }

    private class BeforeLaunchDateSubscription(val date: Date, val channel: Channel<List<Record>>)
}