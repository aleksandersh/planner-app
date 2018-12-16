package io.github.aleksandersh.plannerapp.presentation.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.aleksandersh.plannerapp.presentation.BackHandler
import io.github.aleksandersh.plannerapp.presentation.BaseViewModel
import io.github.aleksandersh.plannerapp.records.interactor.RecordsInteractor
import io.github.aleksandersh.plannerapp.records.model.Record
import io.github.aleksandersh.plannerapp.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class RecordViewModel(
    recordId: Long?,
    private val recordsInteractor: RecordsInteractor,
    private val back: () -> Unit
) : BaseViewModel(), BackHandler {

    val dateTitle: LiveData<String> get() = _dateTitle
    val isCycleShowed: LiveData<Boolean> get() = _isCycleShowed
    val refreshRecord: LiveData<Record> get() = _refreshRecord
    val showDateSelectionDialog: LiveData<Date> get() = _showDateSelectionDialog

    private val dateFormatter: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    private val _dateTitle = MutableLiveData<String>()
    private val _isCycleShowed = MutableLiveData<Boolean>()
    private val _refreshRecord = SingleLiveEvent<Record>()
    private val _showDateSelectionDialog = SingleLiveEvent<Date>()

    private var _title: String = ""
    private var _description: String = ""
    private var _date: Date = Date()
    private var _isRepeatSelected: Boolean = false

    private lateinit var currentRecord: Record

    init {
        if (recordId != null) {
            loadRecord(recordId)
        } else {
            showEmptyRecord()
        }
    }

    override fun handleBack(): Boolean {
        back()
        return true
    }

    fun setDate(date: Date) {
        _date = date
        _dateTitle.value = dateFormatter.format(date)
    }

    fun setTitle(title: String) {
        _title = title
    }

    fun setDescription(description: String) {
        _description = description
    }

    fun setRepeatSelected(isSelected: Boolean) {
        _isRepeatSelected = isSelected
        _isCycleShowed.value = isSelected
    }

    fun onClickChooseDate() {
        _showDateSelectionDialog.value = _date
    }

    fun onClickSaveChanges() {
        startCoroutine {
            withContext(Dispatchers.IO) {
                val record = currentRecord.copy(
                    date = _date,
                    title = _title,
                    description = _description,
                    repeat = _isRepeatSelected,
                    cycle = "1"
                )
                recordsInteractor.updateRecord(record)
            }
            back()
        }
    }

    private fun loadRecord(id: Long) {
        startCoroutine {
            val result = runCatching {
                withContext(Dispatchers.IO) {
                    recordsInteractor.getRecord(id)
                }
            }
                .onFailure { error -> Timber.e(error) }
            result.getOrNull()
                ?.let(::showRecord)
                ?: showEmptyRecord()
        }
    }

    private fun showRecord(record: Record) {
        currentRecord = record
        setTitle(record.title)
        setDescription(record.description)
        setDate(record.date)
        setRepeatSelected(record.repeat)
        requestUiRefresh(record)
    }

    private fun showEmptyRecord() {
        currentRecord = Record(
            id = 0,
            date = _date,
            title = _title,
            description = _description,
            repeat = _isRepeatSelected,
            cycle = "1"
        )
        _dateTitle.value = dateFormatter.format(_date)
        _isCycleShowed.value = _isRepeatSelected
        requestUiRefresh(currentRecord)
    }

    private fun requestUiRefresh(record: Record) {
        _refreshRecord.value = record
    }
}