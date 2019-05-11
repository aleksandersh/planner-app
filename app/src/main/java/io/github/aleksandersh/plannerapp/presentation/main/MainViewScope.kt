package io.github.aleksandersh.plannerapp.presentation.main

import androidx.lifecycle.LiveData
import io.github.aleksandersh.plannerapp.app.Dependencies
import io.github.aleksandersh.plannerapp.presentation.main.model.MainScreen
import io.github.aleksandersh.plannerapp.presentation.base.ViewScope
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewScope
import io.github.aleksandersh.plannerapp.presentation.recordlist.RecordListViewScope
import io.github.aleksandersh.plannerapp.presentation.today.TodayViewScope
import io.github.aleksandersh.plannerapp.utils.SingleLiveEvent
import java.util.*

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class MainViewScope : ViewScope {

    val router: LiveData<MainScreen> get() = _router
    val back: LiveData<Boolean> get() = _back
    val childStack: List<MainScreen> get() = _childStack.toList()

    private val _router = SingleLiveEvent<MainScreen>()
    private val _back = SingleLiveEvent<Boolean>()
    private val _childStack = LinkedList<MainScreen>()
    private val recordsInteractor = Dependencies.recordsInteractor

    private val mainRouter = object : MainRouter {

        override fun navigateRecordCreation() = this@MainViewScope.navigateRecord(null)

        override fun navigateRecord(id: Long) = this@MainViewScope.navigateRecord(id)

        override fun navigateRecordList() = this@MainViewScope.navigateRecordList()
    }

    init {
        navigateToday()
    }

    override fun handleBack(): Boolean {
        if (_childStack.isEmpty()) return false
        if (_childStack.first.viewScope.handleBack()) return true
        _childStack.pop().viewScope.onFinish()
        _back.value = true
        return _childStack.isNotEmpty()
    }

    private fun navigateRecordList() {
        navigate(MainScreen.RecordList(RecordListViewScope(mainRouter, recordsInteractor)))
    }

    private fun navigateRecord(id: Long?) {
        navigate(MainScreen.NewRecord(RecordViewScope(id, recordsInteractor)))
    }

    private fun navigateToday() {
        navigate(MainScreen.Today(TodayViewScope(mainRouter, recordsInteractor)))
    }

    private fun navigate(screen: MainScreen) {
        _childStack.push(screen)
        _router.value = screen
    }
}