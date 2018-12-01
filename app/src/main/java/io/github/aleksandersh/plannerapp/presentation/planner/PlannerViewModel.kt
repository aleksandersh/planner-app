package io.github.aleksandersh.plannerapp.presentation.planner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.aleksandersh.plannerapp.presentation.BackHandler
import io.github.aleksandersh.plannerapp.presentation.main.MainViewModel

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class PlannerViewModel : ViewModel(), BackHandler {

    private val _finish = MutableLiveData<Boolean>()
    val finish: LiveData<Boolean> = _finish

    val mainViewModel: MainViewModel = MainViewModel { handleBack() }
    private var backHandler: BackHandler = mainViewModel

    override fun handleBack(): Boolean {
        _finish.value = true
        return true
    }

    fun onClickBack() {
        backHandler.handleBack()
    }
}