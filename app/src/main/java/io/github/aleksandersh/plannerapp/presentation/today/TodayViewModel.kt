package io.github.aleksandersh.plannerapp.presentation.today

import io.github.aleksandersh.plannerapp.presentation.BaseViewModel
import io.github.aleksandersh.plannerapp.presentation.main.MainRouter
import io.github.aleksandersh.plannerapp.records.interactor.RecordsInteractor

/**
 * Created on 16.12.2018.
 * @author AleksanderSh
 */
class TodayViewModel(
    private val mainRouter: MainRouter,
    private val recordsInteractor: RecordsInteractor
) : BaseViewModel() {

    init {
        recordsInteractor.subscribeRecords()
    }

    fun onClickCreateRecord() {
        mainRouter.navigateRecordCreation()
    }

    fun onClickShowAllRecords() {
        mainRouter.navigateRecordList()
    }
}