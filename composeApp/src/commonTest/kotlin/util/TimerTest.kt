package util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration

class TimerTest {
    /*
    companion object {
        val sampleData = listOf(
            "123 abc",
            "abc 123",
            "123 ABC",
            "ABC 123"
        )
    }
     */

    @Test
    fun shouldBeRunningAfterStarted() {
        val timer = Timer()
        timer.startTimer()
        assertEquals(true, timer.isRunning())
    }

    @Test
    fun shouldBeStoppedAfterStopped() {
        val timer = Timer()
        timer.startTimer()
        timer.stopTimer()
        assertEquals(false, timer.isRunning())
    }

    @Test
    fun shouldBeResetAfterReset() {
        val timer = Timer()
        timer.startTimer()
        timer.reset()
        assertEquals(0, timer.durationState().value.inWholeSeconds)
    }

    @Test
    fun shouldBeRunningAfterStartedFromSave() {
        val timer = Timer()
        timer.startTimerFromSave(Duration.parse("10s"))
        assertEquals(true, timer.isRunning())
    }

    @Test
    fun shouldHaveCorrectDurationState() {
        val timer = Timer()
        timer.startTimer()
        runOnMainAfter(1000L) {
            assertEquals(1, timer.durationState().value.inWholeSeconds)
        }
    }
}
