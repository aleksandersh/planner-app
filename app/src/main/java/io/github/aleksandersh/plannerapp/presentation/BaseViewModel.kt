package io.github.aleksandersh.plannerapp.presentation

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
open class BaseViewModel : CoroutineScope {

    override val coroutineContext: CoroutineContext get() = viewModelContext + Dispatchers.Main
    private val viewModelContext = SupervisorJob()

    protected fun launchImmediate(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(Dispatchers.Main.immediate, block = block)
    }

    // TODO: This is not called
    fun onDestroy() {
        viewModelContext.cancel()
    }
}