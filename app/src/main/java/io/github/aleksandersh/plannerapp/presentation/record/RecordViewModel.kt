package io.github.aleksandersh.plannerapp.presentation.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.aleksandersh.plannerapp.presentation.BackHandler
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class RecordViewModel(private val back: () -> Unit) : BackHandler {

    var title: String = ""
    var description: String = ""

    private val dateFormatter: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    private var _date: Date = Date()
    private var _isRepeatSelected: Boolean = false

    private val _dateTitle = MutableLiveData<String>()
    private val _isCycleShowed = MutableLiveData<Boolean>()

    val date: Date get() = _date
    val isRepeatSelected: Boolean get() = _isRepeatSelected
    val dateTitle: LiveData<String> get() = _dateTitle
    val isCycleShowed get() = _isCycleShowed

    init {
        _dateTitle.value = dateFormatter.format(date)
        _isCycleShowed.value = _isRepeatSelected
    }

    override fun handleBack(): Boolean {
        back()
        return true
    }

    fun setDate(date: Date) {
        _date = date
        _dateTitle.value = dateFormatter.format(date)
    }

    fun setRepeatSelected(isSelected: Boolean) {
        _isRepeatSelected = isSelected
        _isCycleShowed.value = isSelected
    }

    fun onClickSaveChanges() {
        back()
    }
}