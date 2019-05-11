package io.github.aleksandersh.plannerapp.presentation.base

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
open class BaseViewScope : ViewScope, CoroutineScope {

    override val coroutineContext: CoroutineContext get() = viewModelContext + Dispatchers.Main
    private val viewModelContext = SupervisorJob()

    protected fun launchImmediate(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(Dispatchers.Main.immediate, block = block)
    }

    override fun onFinish() {
        cancel()
    }
}