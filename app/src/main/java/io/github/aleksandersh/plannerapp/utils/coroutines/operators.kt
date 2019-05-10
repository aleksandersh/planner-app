import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.doFinally(block: () -> Unit): Flow<T> = flow {
    try {
        collect { value -> emit(value) }
    } finally {
        block()
    }
}