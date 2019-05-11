package io.github.aleksandersh.plannerapp.presentation.base

import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.collections.forEachReversedByIndex
import java.util.*

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */
class ViewNavigator(private val mainComponent: ViewComponent<ViewGroup>) {

    private val childStack = LinkedList<ViewComponent<*>>()

    fun restoreStack(stack: List<ViewComponent<*>>) {
        stack.forEachReversedByIndex(::addToStack)
    }

    fun addToStack(component: ViewComponent<*>) {
        childStack.firstOrNull()?.view?.visibility = View.GONE
        mainComponent.view.addView(component.view, component.layoutParams)
        mainComponent.bind(component)
        childStack.push(component)
    }

    fun popBackStack() {
        if (childStack.isNotEmpty()) {
            val child = childStack.pop()
            mainComponent.unbind(child)
            mainComponent.view.removeView(child.view)
            childStack.firstOrNull()?.view?.visibility = View.VISIBLE
        }
    }
}