package io.github.aleksandersh.plannerapp.records.repository

import io.github.aleksandersh.plannerapp.records.model.Record
import kotlinx.coroutines.channels.ReceiveChannel
import java.util.*

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
interface RecordsRepository {

    fun subscribeRecords(): ReceiveChannel<List<Record>>

    fun subscribeRecordsBeforeLaunchDate(date: Date): ReceiveChannel<List<Record>>

    fun getRecord(id: Long): Record

    fun updateRecord(record: Record): Record

    fun removeRecordById(id: Long)
}