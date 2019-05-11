package io.github.aleksandersh.plannerapp.presentation.main

import android.animation.LayoutTransition
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import io.github.aleksandersh.plannerapp.presentation.base.ViewComponent
import io.github.aleksandersh.plannerapp.presentation.base.ViewNavigator
import io.github.aleksandersh.plannerapp.presentation.main.model.MainScreen
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewComponent
import io.github.aleksandersh.plannerapp.presentation.recordlist.RecordListViewComponent
import io.github.aleksandersh.plannerapp.presentation.today.TodayViewComponent
import io.github.aleksandersh.plannerapp.utils.observeNotNull

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class MainViewComponent(
    private val context: Context,
    private val viewScope: MainViewScope
) : ViewComponent<ViewGroup>() {

    private val navigator = ViewNavigator(this)

    override fun buildView(): ViewGroup {
        return FrameLayout(context).apply {
            layoutTransition = LayoutTransition()
        }
    }

    override fun onFirstAttach() {
        navigator.restoreStack(viewScope.childStack.map(::getViewComponent))
    }

    override fun onAttach() {
        observeNotNull(viewScope.router, ::onScreenChanged)
        observeNotNull(viewScope.back) {
            navigator.popBackStack()
        }
    }

    private fun onScreenChanged(screen: MainScreen) {
        navigator.addToStack(getViewComponent(screen))
    }

    private fun getViewComponent(screen: MainScreen): ViewComponent<*> {
        return when (screen) {
            is MainScreen.RecordList -> RecordListViewComponent(context, screen.viewScope)
            is MainScreen.NewRecord -> RecordViewComponent(context, screen.viewScope)
            is MainScreen.Today -> TodayViewComponent(context, screen.viewScope)
        }
    }
}