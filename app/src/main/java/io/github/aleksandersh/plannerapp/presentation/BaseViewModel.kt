package io.github.aleksandersh.plannerapp.presentation

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created on 15.12.2018.
 * @author AleksanderSh
 */
open class BaseViewModel : CoroutineScope {

    override val coroutineContext: CoroutineContext get() = viewModelContext + Dispatchers.Main
    private val viewModelContext = Job()

    protected fun startCoroutine(block: suspend CoroutineScope.() -> Unit): Job {
        return launch(start = CoroutineStart.UNDISPATCHED, block = block)
    }

    fun onDestroy() {
        viewModelContext.cancel()
    }
}