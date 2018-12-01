package io.github.aleksandersh.plannerapp.presentation

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import java.util.*

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */
abstract class ViewComponent<V : View>(val id: Int = View.NO_ID) : LifecycleOwner {

    private var _view: V? = null
    val view: V
        get() {
            return _view ?: kotlin.run {
                val newView = buildView()
                if (id != View.NO_ID) {
                    newView.id = id
                }
                _view = newView
                newView
            }
        }

    private val lifecycle = LifecycleRegistry(this)
    private val bindings = LinkedList<ViewComponent<*>>()

    private var isAttached = false

    abstract fun buildView(): V

    override fun getLifecycle(): Lifecycle {
        return lifecycle
    }

    fun attach(state: Lifecycle.State) {
        if (!isAttached) {
            checkViewInitialized()
            isAttached = true
            lifecycle.markState(state)
            onAttach()
            bindings.forEach { it.attach(state) }
        }
    }

    fun detach() {
        if (isAttached) {
            isAttached = false
            lifecycle.markState(Lifecycle.State.DESTROYED)
            onDetach()
            bindings.forEach { it.detach() }
        }
    }

    fun moveToState(state: Lifecycle.State) {
        lifecycle.markState(state)
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

    protected open fun onAttach() {
    }

    protected open fun onDetach() {
    }

    private fun checkViewInitialized() {
        view
    }
}