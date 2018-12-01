package io.github.aleksandersh.plannerapp.presentation.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.aleksandersh.plannerapp.presentation.example.model.ScreenVm

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */
class MainViewModel : ViewModel() {

    private var step = 0

    private val _navigator = MutableLiveData<ScreenVm>()
    private val _stepsCount = MutableLiveData<Int>()

    val navigator: LiveData<ScreenVm> = _navigator
    val stepsCount: LiveData<Int> = _stepsCount

    init {
        _navigator.value = ScreenVm.Screen1("Hop")
        _stepsCount.value = 0
    }

    fun onClickMoveNext() {
        step++
        refreshStepsCount()
        _navigator.value = ScreenVm.Screen2(getTitle())
    }

    fun onClickMoveBack() {
        step--
        refreshStepsCount()
        _navigator.value = if (step > 0) {
            ScreenVm.Screen2(getTitle())
        } else {
            ScreenVm.Screen1(getTitle())
        }
    }

    private fun getTitle(): String {
        return buildString {
            append("Hop")
            for (step in 0 until step) {
                append("-hey")
            }
        }
    }

    private fun refreshStepsCount() {
        _stepsCount.value = step
    }
}