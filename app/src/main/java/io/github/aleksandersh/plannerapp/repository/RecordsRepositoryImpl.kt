package io.github.aleksandersh.plannerapp.repository

import io.github.aleksandersh.plannerapp.plannerdb.dao.RecordsDao
import io.github.aleksandersh.plannerapp.plannerdb.entity.RecordEntity
import io.github.aleksandersh.plannerapp.records.model.Record
import io.github.aleksandersh.plannerapp.records.repository.RecordsRepository

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
class RecordsRepositoryImpl(
    private val recordsDao: RecordsDao
) : RecordsRepository {

    override fun getRecords(): List<Record> {
        return recordsDao.selectRecords().map(::mapEntityToRecord)
    }

    override fun getRecord(id: Long): Record {
        return mapEntityToRecord(recordsDao.requireRecordById(id))
    }

    override fun updateRecord(record: Record) {
        recordsDao.insertRecord(mapRecordToEntity(record))
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