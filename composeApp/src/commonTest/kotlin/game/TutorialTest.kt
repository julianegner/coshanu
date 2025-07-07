package game

import androidx.compose.ui.graphics.Color
import coshanu.composeapp.generated.resources.*
import game.enums.GameMode
import game.enums.ShapeEnum
import kotlin.test.*

class TutorialTest {

    private lateinit var tutorial: Tutorial

    @BeforeTest
    fun setUp() {
        tutorial = Tutorial()
    }

    @Test
    fun testStartTutorialSingleElement() {
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        tutorial.startTutorial()
        assertTrue(tutorial.isTutorial())
        assertEquals(4, tutorial.getTileList().size)

        val tiles = listOf(
            newTileData(Color.Green, ShapeEnum.TRIANGLE, 3),
            newTileData(Color.Yellow, ShapeEnum.TRIANGLE, 3),
            newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3),
            newTileData(Color.Blue, ShapeEnum.CIRCLE, 1)
        )

        assertEquals(tiles, tutorial.getTileList())

        assertEquals(listOf(
            TutorialStep(1, Res.string.tutorial_one_1, tiles[0]),
            TutorialStep(2, Res.string.tutorial_one_2, tiles[3]),
            TutorialStep(3, Res.string.tutorial_one_3, tiles[2]),
            TutorialStep(4, Res.string.tutorial_one_4, null, isRestart = true),
            TutorialStep(5, Res.string.tutorial_one_5, tiles[0]),
            TutorialStep(6, Res.string.tutorial_one_6, tiles[1]),
            TutorialStep(7, Res.string.tutorial_one_7,  tiles[2], Res.string.tutorial_one_7_pattern),
            TutorialStep(8, Res.string.tutorial_one_8,  tiles[3], Res.string.tutorial_one_8_pattern),
            TutorialStep(9, Res.string.tutorial_one_9, null)
        ), tutorial.getTutorialSteps())

    }

    @Test
    fun testStartTutorialTwoElements() {
        GameStateHolder.gameMode.value = GameMode.TWO_ELEMENTS
        tutorial.startTutorial()
        assertTrue(tutorial.isTutorial())
        assertEquals(16, tutorial.getTileList().size)
        assertEquals(4, tutorial.getTileList()[0].number)

        val tiles = listOf(
            newTileData(Color.Yellow, ShapeEnum.TRIANGLE, 4),
            newTileData(Color.Green, ShapeEnum.SQUARE, 4),
            newTileData(Color.Blue, ShapeEnum.HEXAGON, 3),
            newTileData(Color.Blue, ShapeEnum.OCTAGON, 1),
            newTileData(Color.Blue, ShapeEnum.CIRCLE, 1),
            newTileData(Color.Red, ShapeEnum.HEXAGON, 3),
            newTileData(Color.Red, ShapeEnum.TRIANGLE, 3),
            newTileData(Color.Blue, ShapeEnum.OCTAGON, 4),
            newTileData(Color.Yellow, ShapeEnum.TRIANGLE, 2),
            newTileData(Color.Blue, ShapeEnum.SQUARE, 4),
            newTileData(Color.Green, ShapeEnum.HEXAGON, 4),
            newTileData(Color.Green, ShapeEnum.PENTAGON, 2),
            newTileData(Color.Yellow, ShapeEnum.PENTAGON, 4),
            newTileData(Color.Green, ShapeEnum.SQUARE, 2),
            newTileData(Color.Blue, ShapeEnum.PENTAGON, 3),
            newTileData(Color.Yellow, ShapeEnum.SQUARE, 4)
        )

        assertEquals(tiles, tutorial.getTileList())

        assertEquals(listOf(
            TutorialStep(1, Res.string.tutorial_two_1, tiles[0]),
            TutorialStep(2, Res.string.tutorial_two_2, tiles[1]),
            TutorialStep(3, Res.string.tutorial_two_3, tiles[15]),
            TutorialStep(4, Res.string.tutorial_two_4, null, isRestart = true),
            TutorialStep(5, Res.string.tutorial_two_5, tiles[0]),
            TutorialStep(6, Res.string.tutorial_two_6, tiles[8]),
            TutorialStep(7, Res.string.tutorial_two_7, tiles[12]),
            TutorialStep(8, Res.string.tutorial_two_8, tiles[15]),
            TutorialStep(9, Res.string.tutorial_two_9, tiles[5], Res.string.tutorial_two_9_pattern),
            TutorialStep(10, Res.string.tutorial_two_10, tiles[6]),
            TutorialStep(11, Res.string.tutorial_two_11, tiles[1], Res.string.tutorial_two_11_pattern),
            TutorialStep(12, Res.string.tutorial_two_12, tiles[10]),
            TutorialStep(13, Res.string.tutorial_two_13, tiles[11], Res.string.tutorial_two_13_pattern),
            TutorialStep(14, Res.string.tutorial_two_14, tiles[13]),
            TutorialStep(15, Res.string.tutorial_two_15, tiles[3], Res.string.tutorial_two_15_pattern),
            TutorialStep(16, Res.string.tutorial_two_16, tiles[4]),
            TutorialStep(17, Res.string.tutorial_two_17, tiles[2], Res.string.tutorial_two_17_pattern),
            TutorialStep(18, Res.string.tutorial_two_18, tiles[14]),
            TutorialStep(19, Res.string.tutorial_two_19, tiles[7]),
            TutorialStep(20, Res.string.tutorial_two_20, tiles[9]),
            TutorialStep(21, Res.string.tutorial_two_21, null)
        ), tutorial.getTutorialSteps())
    }

    @Test
    fun testEndTutorial() {
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        tutorial.startTutorial()
        tutorial.endTutorial()
        assertFalse(tutorial.isTutorial())
    }

    @Test
    fun testNextStep() {
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        tutorial.startTutorial()
        tutorial.nextStep()
        val steps = tutorial.getTutorialSteps()

        assertEquals(steps[1], tutorial.getCurrentStep())
        tutorial.nextStep()
        assertEquals(steps[2], tutorial.getCurrentStep())
        assertEquals(steps[2], tutorial.getCurrentStep())
        tutorial.nextStep()
        assertEquals(steps[3], tutorial.getCurrentStep())
        tutorial.nextStep()
        assertEquals(steps[4], tutorial.getCurrentStep())
    }

    @Test
    fun nextStepMoreTanExistingShouldNotCrash() {
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        tutorial.startTutorial()
        val steps = tutorial.getTutorialSteps()
        assertEquals(steps[0], tutorial.getCurrentStep(), "Current step should be the first step")
        steps.forEach { tutorial.nextStep() }
        // after the last step
        assertNull(tutorial.getCurrentStep())

        // getting next step should not crash despite the fact that there are no more steps
        tutorial.nextStep()
        assertNull(tutorial.getCurrentStep())
    }

    @Test
    fun testIsAllowedTile() {
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        tutorial.startTutorial()
        val tile = TileData(color = Color.Green, shape = ShapeEnum.TRIANGLE, number = 3, chosenForPlay = true, played = false)
        assertTrue(tutorial.isAllowedTile(tile))
    }

    @Test
    fun testIsRestartStep() {
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        tutorial.startTutorial()
        tutorial.nextStep()
        tutorial.nextStep()
        tutorial.nextStep()
        assertTrue(tutorial.isRestartStep())
    }
}
