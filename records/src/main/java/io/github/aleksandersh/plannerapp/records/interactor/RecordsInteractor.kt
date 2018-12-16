package io.github.aleksandersh.plannerapp.records.interactor

import io.github.aleksandersh.plannerapp.records.model.Record
import io.github.aleksandersh.plannerapp.records.repository.RecordsRepository
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
class RecordsInteractor(private val recordsRepository: RecordsRepository) {

    fun getRecords(): List<Record> {
        return recordsRepository.getRecords()
    }

    fun subscribeRecords(): ReceiveChannel<List<Record>> {
        return recordsRepository.subscribeRecords()
    }

    fun getRecord(id: Long): Record {
        return recordsRepository.getRecord(id)
    }

    fun updateRecord(record: Record): Record {
        return recordsRepository.updateRecord(record)
    }
}