package io.github.aleksandersh.plannerapp.presentation.planner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.aleksandersh.plannerapp.presentation.main.MainViewScope
import io.github.aleksandersh.plannerapp.presentation.base.ViewScope

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class PlannerViewModel : ViewModel() {

    val finish: LiveData<Boolean> get() = _finish
    private val _finish = MutableLiveData<Boolean>()

    val mainViewScope: MainViewScope = MainViewScope()
    private var childViewScope: ViewScope = mainViewScope

    fun onClickBack() {
        if (!childViewScope.handleBack()) {
            childViewScope.onFinish()
            _finish.value = true
        }
    }
}