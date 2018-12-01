package io.github.aleksandersh.plannerapp.presentation.main

import android.animation.LayoutTransition
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import io.github.aleksandersh.plannerapp.R
import io.github.aleksandersh.plannerapp.presentation.ViewComponent
import io.github.aleksandersh.plannerapp.presentation.ViewNavigator
import io.github.aleksandersh.plannerapp.presentation.planner.model.PlannerScreen
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewComponent
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewModel
import io.github.aleksandersh.plannerapp.utils.button
import io.github.aleksandersh.plannerapp.utils.dip
import io.github.aleksandersh.plannerapp.utils.frameLayoutParams

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class MainViewComponent(
    private val context: Context,
    private val viewModel: MainViewModel
) : ViewComponent<ViewGroup>(R.id.main_component) {

    private val observer = Observer(::onScreenChanged)
    private val navigator: ViewNavigator by lazy { ViewNavigator(this, 0) }

    override fun buildView(): ViewGroup {
        return FrameLayout(context).apply {
            layoutTransition = LayoutTransition()
        }
    }

    override fun onAttach() {
        viewModel.router.observe(this, observer)
    }

    private fun onScreenChanged(screen: PlannerScreen) {
        when (screen) {
            is PlannerScreen.Main -> navigateMainScreen(navigator)
            is PlannerScreen.NewRecord -> navigateRecordScreen(navigator, screen.recordViewModel)
        }
    }

    private fun navigateMainScreen(navigator: ViewNavigator) {
        navigator.navigate(
            getMainScreenComponent(),
            frameLayoutParams(MATCH_PARENT, MATCH_PARENT) {
                gravity = Gravity.CENTER
            }
        )
    }

    private fun navigateRecordScreen(navigator: ViewNavigator, recordViewModel: RecordViewModel) {
        val dip16 = context.dip(16)
        navigator.navigate(
            RecordViewComponent(context, recordViewModel),
            frameLayoutParams(MATCH_PARENT, WRAP_CONTENT) {
                setMargins(dip16, dip16, dip16, dip16)
                gravity = Gravity.CENTER
            }
        )
    }

    private fun getMainScreenComponent(): ViewComponent<*> {
        return object : ViewComponent<ViewGroup>() {

            override fun buildView(): ViewGroup {
                val dip8 = context.dip(8)
                return FrameLayout(context).apply {
                    addView(
                        context.button {
                            text = "create"
                            setOnClickListener { viewModel.onClickCreateRecord() }
                        },
                        frameLayoutParams(WRAP_CONTENT, WRAP_CONTENT) {
                            setMargins(dip8, dip8, dip8, dip8)
                            gravity = Gravity.CENTER
                        }
                    )
                }
            }
        }
    }
}