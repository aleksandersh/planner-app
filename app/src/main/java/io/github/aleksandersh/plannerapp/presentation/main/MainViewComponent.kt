package io.github.aleksandersh.plannerapp.presentation.main

import android.animation.LayoutTransition
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import io.github.aleksandersh.plannerapp.R
import io.github.aleksandersh.plannerapp.presentation.ViewComponent
import io.github.aleksandersh.plannerapp.presentation.ViewNavigator
import io.github.aleksandersh.plannerapp.presentation.main.model.MainScreen
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewComponent
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewModel
import io.github.aleksandersh.plannerapp.presentation.recordlist.RecordListViewComponent
import io.github.aleksandersh.plannerapp.presentation.recordlist.RecordListViewModel
import io.github.aleksandersh.plannerapp.utils.dip
import io.github.aleksandersh.plannerapp.utils.frameLayoutParams
import io.github.aleksandersh.plannerapp.utils.observe

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class MainViewComponent(
    private val context: Context,
    private val viewModel: MainViewModel
) : ViewComponent<ViewGroup>(R.id.main_component) {

    private val navigator: ViewNavigator by lazy { ViewNavigator(this, 0) }

    override fun buildView(): ViewGroup {
        return FrameLayout(context).apply {
            layoutTransition = LayoutTransition()
        }
    }

    override fun onAttach() {
        viewModel.router.observe(this, ::onScreenChanged)
    }

    private fun onScreenChanged(screen: MainScreen) {
        when (screen) {
            is MainScreen.RecordList -> navigateRecordListScreen(navigator, screen.viewModel)
            is MainScreen.NewRecord -> navigateRecordScreen(navigator, screen.viewModel)
        }
    }

    private fun navigateRecordListScreen(
        navigator: ViewNavigator,
        recordListViewModel: RecordListViewModel
    ) {
        navigator.navigate(
            RecordListViewComponent(context, recordListViewModel),
            frameLayoutParams(MATCH_PARENT, MATCH_PARENT) {
                gravity = Gravity.CENTER
            }
        )
    }

    private fun navigateRecordScreen(
        navigator: ViewNavigator,
        recordViewModel: RecordViewModel
    ) {
        val dip16 = context.dip(16)
        navigator.navigate(
            RecordViewComponent(context, recordViewModel),
            frameLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                setMargins(dip16, dip16, dip16, dip16)
                gravity = Gravity.CENTER
            }
        )
    }
}