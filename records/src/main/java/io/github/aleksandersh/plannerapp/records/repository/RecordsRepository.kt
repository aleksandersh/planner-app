package io.github.aleksandersh.plannerapp.records.repository

import io.github.aleksandersh.plannerapp.records.model.Record

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
interface RecordsRepository {

    fun getRecords(): List<Record>

    fun getRecord(id: Long): Record

    fun updateRecord(record: Record)
}