package io.github.aleksandersh.plannerapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.aleksandersh.plannerapp.app.Dependencies
import io.github.aleksandersh.plannerapp.presentation.BackHandler
import io.github.aleksandersh.plannerapp.presentation.main.model.MainScreen
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewModel
import io.github.aleksandersh.plannerapp.presentation.recordlist.RecordListViewModel

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class MainViewModel(private val back: () -> Unit) : BackHandler {

    val router: LiveData<MainScreen> get() = _router

    private val _router = MutableLiveData<MainScreen>()

    private val recordsInteractor = Dependencies.recordsInteractor

    private val mainRouter = object : MainRouter {

        override fun navigateRecordCreation() = this@MainViewModel.navigateRecord(null)

        override fun navigateRecord(id: Long) = this@MainViewModel.navigateRecord(id)

        override fun navigateRecordList() = this@MainViewModel.navigateRecordList()
    }

    private var backHandler: BackHandler? = null

    init {
        navigateRecordList()
    }

    override fun handleBack(): Boolean {
        if (backHandler?.handleBack() != true) {
            back()
        }
        return true
    }

    private fun navigateRecordList() {
        backHandler = null
        _router.value = MainScreen.RecordList(RecordListViewModel(mainRouter, recordsInteractor))
    }

    private fun navigateRecord(id: Long?) {
        val currentBackHandler = backHandler
        val currentScreen = _router.value
        val screenVm = RecordViewModel(id, recordsInteractor) {
            backHandler = currentBackHandler
            _router.value = currentScreen
        }
        backHandler = screenVm
        _router.value = MainScreen.NewRecord(screenVm)
    }
}