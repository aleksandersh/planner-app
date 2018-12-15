package io.github.aleksandersh.plannerapp.repository

import io.github.aleksandersh.plannerapp.plannerdb.dao.RecordsDao
import io.github.aleksandersh.plannerapp.plannerdb.entity.RecordEntity
import io.github.aleksandersh.plannerapp.records.model.Record
import io.github.aleksandersh.plannerapp.records.repository.RecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.distinct
import kotlinx.coroutines.launch
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

    override fun getRecords(): List<Record> {
        return recordsDao.selectRecords().map(::mapEntityToRecord)
    }

    override fun subscribeRecords(): ReceiveChannel<List<Record>> {
        if (isChannelInitialized.compareAndSet(false, true)) {
            refreshRecords()
        }
        return recordsChannel.openSubscription().distinct()
    }

    override fun getRecord(id: Long): Record {
        return mapEntityToRecord(recordsDao.requireRecordById(id))
    }

    override fun updateRecord(record: Record) {
        recordsDao.insertRecord(mapRecordToEntity(record))
        refreshRecords()
    }

    private fun refreshRecords() {
        GlobalScope.launch(Dispatchers.IO) { recordsChannel.send(getRecords()) }
    }

    private fun mapRecordToEntity(record: Record): RecordEntity {
        return RecordEntity(
            record.id,
            record.date,
            record.title,
            record.description,
            record.repeat,
            record.cycle
        )
    }

    private fun mapEntityToRecord(entity: RecordEntity): Record {
        return Record(
            entity.id,
            entity.date,
            entity.title,
            entity.description,
            entity.repeat,
            entity.cycle
        )
    }
}