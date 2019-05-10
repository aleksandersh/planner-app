package io.github.aleksandersh.plannerapp.records.interactor

import io.github.aleksandersh.plannerapp.records.model.Record
import io.github.aleksandersh.plannerapp.records.repository.RecordsRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
class RecordsInteractor(private val recordsRepository: RecordsRepository) {

    fun observeRecords(): Flow<List<Record>> {
        return recordsRepository.observeRecords()
    }

    fun observeRecordsForToday(): Flow<List<Record>> {
        // TODO: Observe current date
        val calendar = GregorianCalendar()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        return recordsRepository.observeRecordsBeforeLaunchDate(calendar.time)
    }

    fun getRecord(id: Long): Record {
        return recordsRepository.getRecord(id)
    }

    fun updateRecord(record: Record): Record {
        return recordsRepository.updateRecord(record)
    }

    fun performRecordById(id: Long) {
        val record = recordsRepository.getRecord(id)
        if (record.repeat && record.cycle.isNotEmpty()) {
            val cycle = record.cycle
            var cycleStep = record.cycleStep
            val calendar = GregorianCalendar()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            do {
                calendar.add(Calendar.DATE, 1)
                if (++cycleStep >= cycle.length) {
                    cycleStep = 0
                }
            } while (cycle[cycleStep] != '1')
            val newRecord = record.copy(launchDate = calendar.time, cycleStep = cycleStep)
            recordsRepository.updateRecord(newRecord)
        } else {
            recordsRepository.removeRecordById(id)
        }
    }
}