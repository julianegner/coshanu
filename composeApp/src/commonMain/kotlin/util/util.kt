package util

import kotlinx.coroutines.*

/**
 * Run [runnable] on the main dispatcher after an [interval] given in ms.
 */
fun runOnMainAfter(interval: Long, runnable: () -> Unit): Job {
    return CoroutineScope(Dispatchers.Main).launch {
        delay(interval)
        runnable()
    }
}

expect fun callUrl(url: String)
