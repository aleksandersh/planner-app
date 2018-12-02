package io.github.aleksandersh.plannerapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.aleksandersh.plannerapp.presentation.BackHandler
import io.github.aleksandersh.plannerapp.presentation.main.model.MainScreen
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewModel
import io.github.aleksandersh.plannerapp.presentation.recordlist.RecordListViewModel

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class MainViewModel(private val back: () -> Unit) : BackHandler {

    private val _router = MutableLiveData<MainScreen>()
    val router: LiveData<MainScreen> = _router

    private val mainRouter = object : MainRouter {

        override fun navigateRecordCreation() = this@MainViewModel.navigateRecordCreation()

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

    fun onClickCreateRecord() {
        navigateRecordCreation()
    }

    private fun navigateRecordList() {
        backHandler = null
        _router.value = MainScreen.RecordList(RecordListViewModel(mainRouter))
    }

    private fun navigateRecordCreation() {
        val currentBackHandler = backHandler
        val currentScreen = _router.value
        val screenVm = RecordViewModel {
            backHandler = currentBackHandler
            _router.value = currentScreen
        }
        backHandler = screenVm
        _router.value = MainScreen.NewRecord(screenVm)
    }
}