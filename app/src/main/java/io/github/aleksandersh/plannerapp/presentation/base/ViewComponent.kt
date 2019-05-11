package io.github.aleksandersh.plannerapp.presentation.base

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import java.util.*

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */
abstract class ViewComponent<V : View> : LifecycleOwner {

    open val layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    val view: V
        get() {
            return _view ?: run {
                val newView = buildView()
                _view = newView
                newView
            }
        }
    private var _view: V? = null

    private val lifecycle = LifecycleRegistry(this)
    private val bindings = LinkedList<ViewComponent<*>>()

    private var isFirstAttach = true
    private var isAttached = false

    abstract fun buildView(): V

    override fun getLifecycle(): Lifecycle {
        return lifecycle
    }

    fun attach(state: Lifecycle.State) {
        if (isFirstAttach) {
            isFirstAttach = false
            onFirstAttach()
        }
        if (!isAttached) {
            checkViewInitialized()
            isAttached = true
            onAttach()
            lifecycle.currentState = state
            bindings.forEach { it.attach(state) }
        }
    }

    fun detach() {
        if (isAttached) {
            lifecycle.currentState = Lifecycle.State.DESTROYED
            bindings.forEach { it.detach() }
            onDetach()
            isAttached = false
        }
    }

    fun moveToState(state: Lifecycle.State) {
        lifecycle.currentState = state
        bindings.forEach { it.moveToState(state) }
    }

    fun bind(component: ViewComponent<*>) {
        bindings.add(component)
        if (isAttached) component.attach(lifecycle.currentState)
    }

    fun unbind(component: ViewComponent<*>) {
        bindings.remove(component)
        if (isAttached) component.detach()
    }

    protected open fun onFirstAttach() {
    }

    protected open fun onAttach() {
    }

    protected open fun onDetach() {
    }

    private fun checkViewInitialized() {
        view
    }
}