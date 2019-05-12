package io.github.aleksandersh.plannerapp.presentation.base

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import java.util.*

/**
 * Created on 24.11.2018.
 *
 * Base unit for building application UI. It's similar to fragments in Android SDK.
 *
 * @author AleksanderSh
 */
abstract class ViewComponent<V : View> : LifecycleOwner {

    /**
     * Default [ViewGroup.LayoutParams] of the [ViewComponent] layout. This can be overridden.
     */
    open val layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    /**
     * Get view of the [ViewComponent].
     */
    val view: V
        get() {
            return _view ?: run {
                val newView = createView()
                _view = newView
                newView
            }
        }
    private var _view: V? = null

    private val lifecycle = LifecycleRegistry(this)
    private val bindings = LinkedList<ViewComponent<*>>()

    private var isFirstAttach = true
    private var isAttached = false

    /**
     * @return new view of the [ViewComponent].
     */
    abstract fun createView(): V

    final override fun getLifecycle(): Lifecycle {
        return lifecycle
    }

    /**
     * Notify the [ViewComponent], that it's attached to application activity
     * and set current lifecycle [state].
     */
    fun attach(state: Lifecycle.State) {
        if (isFirstAttach) {
            checkViewInitialized()
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

    /**
     * Notify the [ViewComponent], that it's detached from application activity.
     */
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

    /**
     * Bind [child] component.
     */
    fun bind(child: ViewComponent<*>) {
        bindings.add(child)
        if (isAttached) child.attach(lifecycle.currentState)
    }

    /**
     * Unbind [child] child.
     */
    fun unbind(child: ViewComponent<*>) {
        bindings.remove(child)
        if (isAttached) child.detach()
    }

    /**
     * Invokes only for the first time, when the [ViewComponent] attaches to application activity.
     */
    protected open fun onFirstAttach() {
    }

    /**
     * Invokes, when the [ViewComponent] attaches to application activity.
     */
    protected open fun onAttach() {
    }

    /**
     * Invokes, when the [ViewComponent] detaches from application activity.
     */
    protected open fun onDetach() {
    }

    private fun checkViewInitialized() {
        view
    }
}