package io.github.aleksandersh.plannerapp.presentation.main

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import io.github.aleksandersh.plannerapp.presentation.base.AnkoViewComponent
import io.github.aleksandersh.plannerapp.presentation.base.ViewComponent
import io.github.aleksandersh.plannerapp.presentation.base.ViewNavigator
import io.github.aleksandersh.plannerapp.presentation.main.model.MainScreen
import io.github.aleksandersh.plannerapp.presentation.record.RecordViewComponent
import io.github.aleksandersh.plannerapp.presentation.recordlist.RecordListViewComponent
import io.github.aleksandersh.plannerapp.presentation.today.TodayViewComponent
import io.github.aleksandersh.plannerapp.utils.observeNotNull
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout

/**
 * Created on 25.11.2018.
 * @author AleksanderSh
 */
class MainViewComponent(
    private val context: Context,
    private val viewScope: MainViewScope
) : AnkoViewComponent<ViewGroup>(context) {

    private lateinit var navigator: ViewNavigator
    private lateinit var toolbar: Toolbar
    private lateinit var titleTextSwitcher: TextSwitcher
    private lateinit var childContainer: ViewGroup

    override fun buildAnkoView(ui: AnkoContext<Context>): ViewGroup = with(ui) {
        verticalLayout {
            appBarLayout {
                setLiftable(true)
                setLifted(true)
                elevation = dip(4).toFloat()
                toolbar = toolbar {
                    titleTextSwitcher = textSwitcher {
                        inAnimation = AnimationUtils.makeInAnimation(context, false)
                        outAnimation = AnimationUtils.makeOutAnimation(context, false)
                        setFactory {
                            TextView(context).apply {
                                typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                                textColor = Color.WHITE
                                textSize = 20f
                                singleLine = true
                                ellipsize = TextUtils.TruncateAt.END
                            }
                        }
                    }.lparams(matchParent, wrapContent)
                }.lparams(matchParent, wrapContent)
            }.lparams(matchParent, wrapContent)
            childContainer = frameLayout {
                layoutTransition = LayoutTransition()
            }.lparams(matchParent, matchParent)
            navigator = ViewNavigator(this@MainViewComponent, childContainer)
        }
    }

    override fun onFirstAttach() {
        val stack = viewScope.getCurrentStack()
        titleTextSwitcher.setCurrentText(stack.firstOrNull()?.title)
        navigator.restoreStack(stack.map(::getViewComponent))
    }

    override fun onAttach() {
        observeNotNull(viewScope.router, ::navigateForward)
        observeNotNull(viewScope.back, ::navigateBackward)
    }

    private fun navigateForward(screen: MainScreen) {
        titleTextSwitcher.setText(screen.title)
        navigator.addToStack(getViewComponent(screen))
    }

    private fun navigateBackward(screen: MainScreen) {
        titleTextSwitcher.setText(screen.title)
        navigator.popBackStack()
    }

    private fun getViewComponent(screen: MainScreen): ViewComponent<*> {
        return when (screen) {
            is MainScreen.RecordList -> RecordListViewComponent(context, screen.viewScope)
            is MainScreen.NewRecord -> RecordViewComponent(context, screen.viewScope)
            is MainScreen.Today -> TodayViewComponent(context, screen.viewScope)
        }
    }
}