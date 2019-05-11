package io.github.aleksandersh.plannerapp.presentation.recordlist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.aleksandersh.plannerapp.presentation.base.BaseViewScope
import io.github.aleksandersh.plannerapp.presentation.main.MainRouter
import io.github.aleksandersh.plannerapp.presentation.recordlist.model.RecordListItem
import io.github.aleksandersh.plannerapp.records.interactor.RecordsInteractor
import io.github.aleksandersh.plannerapp.records.model.Record
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

/**
 * Created on 01.12.2018.
 * @author AleksanderSh
 */
class RecordListViewScope(
    private val mainRouter: MainRouter,
    private val recordsInteractor: RecordsInteractor
) : BaseViewScope() {

    val items: LiveData<List<RecordListItem>> get() = _items
    private val _items: MutableLiveData<List<RecordListItem>> = MutableLiveData()

    init {
        subscribeRecords()
    }

    fun onClickCreateRecord() {
        mainRouter.navigateRecordCreation()
    }

    private fun onClickRecord(id: Long) {
        mainRouter.navigateRecord(id)
    }

    private fun subscribeRecords() {
        launchImmediate {
            recordsInteractor.observeRecords()
                .map { records -> records.map(::mapRecord) }
                .collect { items -> _items.value = items }
        }
    }

    private fun mapRecord(record: Record): RecordListItem {
        return RecordListItem(
            record.id,
            record.title,
            View.OnClickListener { onClickRecord(record.id) })
    }
}