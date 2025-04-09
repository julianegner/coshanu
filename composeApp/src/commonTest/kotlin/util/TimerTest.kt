package util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
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

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

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
