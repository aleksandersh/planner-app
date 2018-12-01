package io.github.aleksandersh.plannerapp.presentation

import android.view.View
import android.view.ViewGroup
import androidx.core.view.get

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */
class ViewNavigator(
    private val mainComponent: ViewComponent<ViewGroup>,
    private val position: Int
) {

    private var currentComponent: ViewComponent<*>? = null

    fun navigate(component: ViewComponent<*>, params: ViewGroup.LayoutParams) {
        val parent = mainComponent.view
        val current = currentComponent
        if (current == null) {
            if (
                parent.childCount > position
                && component.id != View.NO_ID
                && parent[position].id == component.id
            ) {
                return
            }
        } else {
            parent.removeViewAt(position)
            mainComponent.unbind(current)
        }
        currentComponent = component
        mainComponent.view.addView(component.view, position, params)
        mainComponent.bind(component)
    }
}