package io.github.aleksandersh.plannerapp.records.repository

import io.github.aleksandersh.plannerapp.records.model.Record
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
interface RecordsRepository {

    fun observeRecords(): Flow<List<Record>>

    fun observeRecordsBeforeLaunchDate(date: Date): Flow<List<Record>>

    fun getRecord(id: Long): Record

    fun updateRecord(record: Record): Record

    fun removeRecordById(id: Long)
}