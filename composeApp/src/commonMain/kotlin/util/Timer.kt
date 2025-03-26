package util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.seconds

class Timer {
    private var timer: Job? = null
    private var duration: Duration = Duration.ZERO
    private var isRunning = false

    private val durationState: MutableState<Duration> = mutableStateOf(ZERO)

    init {
        var counter = 1000
        timer = CoroutineScope(Dispatchers.Main).launch {
            repeat(counter) {
                if (isRunning) {
                    duration += 1.seconds
                    durationState.value = duration
                }
                counter += 1
                delay(1000)
            }
        }
    }

    fun startTimer() {
        reset()
        isRunning = true
    }

    fun stopTimer() {
        isRunning = false
    }

    fun reset() {
        duration = Duration.ZERO
        durationState.value = duration
    }

    fun startTimerFromSave(duration: Duration) {
        this.duration = duration
        durationState.value = duration
        isRunning = true
    }

    fun durationState(): MutableState<Duration> {
        return durationState
    }

    fun isRunning(): Boolean {
        return isRunning
    }
}



