package io.github.aleksandersh.plannerapp.presentation.base

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewPropertyAnimatorCompat
import org.jetbrains.anko.collections.forEachReversedByIndex
import java.util.*

/**
 * Created on 24.11.2018.
 * @author AleksanderSh
 */

typealias TransitionFactory = (View) -> ViewPropertyAnimatorCompat

class ViewNavigator(
    private val mainComponent: ViewComponent<ViewGroup>,
    private val childContainer: ViewGroup
) {

    private val childStack = LinkedList<StackElement>()

    fun restoreStack(stack: List<Transaction>) {
        stack.forEachReversedByIndex { transaction ->
            val component = transaction.component
            childContainer.addView(component.view, component.layoutParams)
            mainComponent.bind(component)
            val newcomer = StackElement(
                component,
                transaction.newcomerExitTransition,
                transaction.elderEnterTransition
            )
            childStack.push(newcomer)
        }
    }

    fun addToStack(transaction: Transaction) {
        val component = transaction.component
        val newcomerEnterTransition = transaction.newcomerEnterTransition
        val elderExitTransition = transaction.elderExitTransition
        val newcomerExitTransition = transaction.newcomerExitTransition
        val elderEnterTransition = transaction.elderEnterTransition

        childStack.firstOrNull()?.let { element ->
            if (elderExitTransition != null) {
                elderExitTransition(element.component.view).start()
            }
        }
        childContainer.addView(component.view, component.layoutParams)
        newcomerEnterTransition?.invoke(component.view)?.start()
        mainComponent.bind(component)
        val newcomer = StackElement(component, newcomerExitTransition, elderEnterTransition)
        childStack.push(newcomer)
    }

    fun popBackStack() {
        if (childStack.isNotEmpty()) {
            val element = childStack.pop()
            val exitTransition = element.newcomerExitTransition
            if (exitTransition != null) {
                exitTransition(element.component.view).withEndAction {
                    mainComponent.unbind(element.component)
                    childContainer.removeView(element.component.view)
                }
            } else {
                mainComponent.unbind(element.component)
                childContainer.removeView(element.component.view)
            }
            childStack.firstOrNull()?.let { elderElement ->
                val enterTransition = element.elderEnterTransition
                val elderElementView = elderElement.component.view
                if (enterTransition != null) {
                    enterTransition(elderElementView)
                }
            }
        }
    }

    class Transaction(
        val component: ViewComponent<*>,
        val newcomerEnterTransition: TransitionFactory? = null,
        val elderExitTransition: TransitionFactory? = null,
        val newcomerExitTransition: TransitionFactory? = null,
        val elderEnterTransition: TransitionFactory? = null
    )

    private class StackElement(
        val component: ViewComponent<*>,
        val newcomerExitTransition: TransitionFactory? = null,
        val elderEnterTransition: TransitionFactory? = null
    )
}