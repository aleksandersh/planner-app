package io.github.aleksandersh.plannerapp.presentation.main

import androidx.lifecycle.LiveData
import io.github.aleksandersh.plannerapp.app.Dependencies
import io.github.aleksandersh.plannerapp.presentation.base.ViewScope
import io.github.aleksandersh.plannerapp.presentation.main.model.MainScreen
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
    val back: LiveData<MainScreen> get() = _back

    private val _router = SingleLiveEvent<MainScreen>()
    private val _back = SingleLiveEvent<MainScreen>()

    private val childStack = LinkedList<MainScreen>()
    private val recordsInteractor = Dependencies.recordsInteractor

    private val mainRouter = object : MainRouter {

        override fun navigateRecordCreation() = this@MainViewScope.navigateRecord(null)

        override fun navigateRecord(id: Long) = this@MainViewScope.navigateRecord(id)

        override fun navigateRecordList() = this@MainViewScope.navigateRecordList()
    }

    init {
        childStack.push(MainScreen.Today(TodayViewScope(mainRouter, recordsInteractor)))
    }

    override fun handleBack(): Boolean {
        if (childStack.isEmpty()) return false
        if (childStack.first.viewScope.handleBack()) return true
        childStack.pop().viewScope.onFinish()
        if (childStack.isNotEmpty()) {
            _back.value = childStack.first
            return true
        }
        return false
    }

    fun getCurrentStack(): List<MainScreen> {
        return childStack.toList()
    }

    private fun navigateRecordList() {
        navigate(MainScreen.RecordList(RecordListViewScope(mainRouter, recordsInteractor)))
    }

    private fun navigateRecord(id: Long?) {
        navigate(MainScreen.NewRecord("Record $id", RecordViewScope(id, recordsInteractor)))
    }

    private fun navigate(screen: MainScreen) {
        childStack.push(screen)
        _router.value = screen
    }
}