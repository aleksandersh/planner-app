package io.github.aleksandersh.plannerapp.presentation.today

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.aleksandersh.plannerapp.presentation.base.BaseViewScope
import io.github.aleksandersh.plannerapp.presentation.main.MainRouter
import io.github.aleksandersh.plannerapp.presentation.today.model.TodayRecordItem
import io.github.aleksandersh.plannerapp.records.interactor.RecordsInteractor
import io.github.aleksandersh.plannerapp.records.model.Record
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
class TodayViewScope(
    private val mainRouter: MainRouter,
    private val recordsInteractor: RecordsInteractor
) : BaseViewScope() {

    val items: LiveData<List<TodayRecordItem>> get() = _items

    private val _items: MutableLiveData<List<TodayRecordItem>> = MutableLiveData()

    init {
        observeRecordsForToday()
    }

    fun onClickCreateRecord() {
        mainRouter.navigateRecordCreation()
    }

    fun onClickShowAllRecords() {
        mainRouter.navigateRecordList()
    }

    private fun observeRecordsForToday() {
        launchImmediate {
            recordsInteractor.observeRecordsForToday()
                .map { records -> records.map(::mapRecord) }
                .collect { items -> _items.value = items }
        }
    }

    private fun onClickRecord(id: Long) {
        mainRouter.navigateRecord(id)
    }

    private fun onClickRecordDone(id: Long) {
        launch(Dispatchers.IO) { recordsInteractor.performRecordById(id) }
    }

    private fun mapRecord(record: Record): TodayRecordItem {
        return TodayRecordItem(
            record.id,
            record.title,
            View.OnClickListener { onClickRecord(record.id) },
            View.OnClickListener { onClickRecordDone(record.id) }
        )
    }
}