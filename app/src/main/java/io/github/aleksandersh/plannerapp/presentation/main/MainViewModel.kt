package io.github.aleksandersh.plannerapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.aleksandersh.plannerapp.presentation.BackHandler
import io.github.aleksandersh.plannerapp.presentation.planner.model.PlannerScreen
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewModel

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class MainViewModel(private val back: () -> Unit) : BackHandler {

    private val _router = MutableLiveData<PlannerScreen>()

    val router: LiveData<PlannerScreen> = _router

    private var backHandler: BackHandler? = null

    init {
        navigateMainScreen()
    }

    override fun handleBack(): Boolean {
        if (backHandler?.handleBack() != true) {
            back()
        }
        return true
    }

    fun onClickCreateRecord() {
        navigateRecordCreation()
    }

    private fun navigateMainScreen() {
        backHandler = null
        _router.value = PlannerScreen.Main
    }

    private fun navigateRecordCreation() {
        val currentBackHandler = backHandler
        val currentScreen = _router.value
        val screenVm = RecordViewModel {
            backHandler = currentBackHandler
            _router.value = currentScreen
        }
        backHandler = screenVm
        _router.value = PlannerScreen.NewRecord(screenVm)
    }
}