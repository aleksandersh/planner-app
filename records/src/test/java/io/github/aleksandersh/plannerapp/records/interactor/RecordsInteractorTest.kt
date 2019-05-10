package io.github.aleksandersh.plannerapp.records.interactor

import com.nhaarman.mockitokotlin2.*
import io.github.aleksandersh.plannerapp.records.model.Record
import io.github.aleksandersh.plannerapp.records.repository.RecordsRepository
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.*

object RecordsInteractorTest : Spek({

    val recordsRepository by memoized { mock<RecordsRepository>() }
    val interactor by memoized { RecordsInteractor(recordsRepository) }

    describe("performRecordById") {
        val recordId = 10L
        context("Record without repeat") {
            val repeat = false
            val cycle = "1"
            val record = Record(recordId, Date(), Date(), "title", "descr", repeat, cycle, 1)
            beforeEach {
                whenever(recordsRepository.getRecord(recordId)).thenReturn(record)
                interactor.performRecordById(recordId)
            }
            it("Record removed because of not repeated") {
                verify(recordsRepository).removeRecordById(recordId)
                verify(recordsRepository, never()).updateRecord(any())
            }
        }
        context("Record without repeat") {
            val repeat = true
            val cycle = ""
            val record = Record(recordId, Date(), Date(), "title", "descr", repeat, cycle, 1)
            beforeEach {
                whenever(recordsRepository.getRecord(recordId)).thenReturn(record)
                interactor.performRecordById(recordId)
            }
            it("Record removed because of empty cycle") {
                verify(recordsRepository).removeRecordById(recordId)
                verify(recordsRepository, never()).updateRecord(any())
            }
        }
    }
})