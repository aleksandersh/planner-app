package io.github.aleksandersh.plannerapp.presentation.base

/**
 * Created on 11.05.2019.
 * @author AleksanderSh
 */
interface ViewScope {

    /**
     * Invokes to handle back request.
     * @return true if request handled by the scope, false - otherwise.
     */
    fun handleBack(): Boolean = false

    /**
     * Invokes when the scope closed.
     */
    fun onFinish() {}
}