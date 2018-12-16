package io.github.aleksandersh.plannerapp.presentation.recordlist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.aleksandersh.plannerapp.presentation.BaseViewModel
import io.github.aleksandersh.plannerapp.presentation.main.MainRouter
import io.github.aleksandersh.plannerapp.presentation.recordlist.model.RecordListItem
import io.github.aleksandersh.plannerapp.records.interactor.RecordsInteractor
import io.github.aleksandersh.plannerapp.records.model.Record
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.map

/**
 * Created on 01.12.2018.
 * @author AleksanderSh
 */
class RecordListViewModel(
    private val mainRouter: MainRouter,
    private val recordsInteractor: RecordsInteractor
) : BaseViewModel() {

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
        startCoroutine {
            recordsInteractor.subscribeRecords()
                .map { records -> records.map(::mapRecord) }
                .consumeEach { items -> _items.value = items }
        }
    }

    private fun mapRecord(record: Record): RecordListItem {
        return RecordListItem(record.title, View.OnClickListener { onClickRecord(record.id) })
    }
}