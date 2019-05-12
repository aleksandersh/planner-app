package io.github.aleksandersh.plannerapp.presentation.base

import android.content.Context
import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

/**
 * Created on 11.05.2019.
 *
 * Base [ViewComponent] implementation, which is using Anko to build the component view.
 *
 * @author AleksanderSh
 */
abstract class AnkoViewComponent<V : View>(
    private val context: Context
) : ViewComponent<V>(), AnkoComponent<Context> {

    final override fun createView(): V {
        return createView(AnkoContext.create(context))
    }

    abstract override fun createView(ui: AnkoContext<Context>): V
}