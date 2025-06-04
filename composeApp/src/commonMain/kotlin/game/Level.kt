package game

import androidx.compose.ui.graphics.Color
import game.enums.GameMode
import kotlin.time.Duration.Companion.seconds

data class CountdownTimerValues(
    val addTime: Long = 10,
    val startTime: Long = 60)

enum class LevelEnum(
    val number: Int,
    val gameMode: GameMode,
    val isTutorial: Boolean,
    val boardSize: Int,
    val maxNumber: Int,
    val colors: List<Color>,
    val countdownTimerValues: CountdownTimerValues = CountdownTimerValues()) {
    LEVEL_0(0, GameMode.SINGLE_ELEMENT, true, 2, 4, listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)),
    LEVEL_1(1, GameMode.SINGLE_ELEMENT, false, 4, 4, listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)),
    LEVEL_2(2, GameMode.SINGLE_ELEMENT, false, 4, 10, listOf(Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan)),
    LEVEL_3(3, GameMode.SINGLE_ELEMENT, false, 8, 10, listOf(Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan)),
    LEVEL10(10, GameMode.TWO_ELEMENTS, true, 4, 4, listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)),
    LEVEL11(11, GameMode.TWO_ELEMENTS, false, 4, 4, listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)),
    LEVEL12(12, GameMode.TWO_ELEMENTS, false, 4, 10, listOf(Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan)),
    LEVEL13(13, GameMode.TWO_ELEMENTS, false, 8, 10, listOf(Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan)),
    LEVEL20(20, GameMode.TWO_ELEMENTS_WITH_TIMER, true, 4, 4, listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)),
    LEVEL21(21, GameMode.TWO_ELEMENTS_WITH_TIMER, false, 4, 4, listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)),
    LEVEL22(22, GameMode.TWO_ELEMENTS_WITH_TIMER, false, 4, 10, listOf(Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan)),
    LEVEL23(23, GameMode.TWO_ELEMENTS_WITH_TIMER, false, 8, 10, listOf(Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan)),
    LEVEL24(24, GameMode.TWO_ELEMENTS_WITH_TIMER, false, 4, 4, listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red), CountdownTimerValues(5, 30)),
    LEVEL25(25, GameMode.TWO_ELEMENTS_WITH_TIMER, false, 4, 10, listOf(Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan), CountdownTimerValues(5, 30)),
    LEVEL26(26, GameMode.TWO_ELEMENTS_WITH_TIMER, false, 8, 10, listOf(Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan), CountdownTimerValues(5, 30))
}

class Level(levelNumber: Int) {
    val existingLevels: List<LevelEnum> = LevelEnum.entries

    var levelNumber = levelNumber
        private set

    fun getNextLevel(): Int {
        // get correct LevelEnum for levelNumber
        val level = existingLevels.find { it.number == levelNumber }
            ?: throw IllegalArgumentException("Level number $levelNumber is not defined in existing levels.")

        var nextLevel: Int = 0
        existingLevels.getOrNull(level.ordinal + 1)?.let {
            nextLevel = it.number
        } ?: throw IllegalArgumentException("No next level available for level number $levelNumber.")

        return nextLevel
    }

    val generator = LevelGenerator()

    init {
        println("Level initialized with level number: $levelNumber")

        // get correct LevelEnum for levelNumber
        val level = existingLevels.find { it.number == levelNumber }
            ?: throw IllegalArgumentException("Level number $levelNumber is not defined in existing levels.")

        GameStateHolder.gameMode.value = level.gameMode
        GameStateHolder.addTime.value = level.countdownTimerValues.addTime.seconds
        GameStateHolder.startTime.value = level.countdownTimerValues.startTime.seconds

        if (level.isTutorial) {
            generator.generateTutorial(level.boardSize)
        } else {
            GameStateHolder.saveNewBoard(generator.createBoard(level.boardSize, level.maxNumber, level.colors))
        }
    }

    val isMaximumLevel: Boolean
        get() = levelNumber >= existingLevels.maxOf { it.number }
}
