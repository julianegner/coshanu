package game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

const val tutorialPart1 = "Welcome to the Tutorial!\nPlease click on the upper left green Triangle with Number 3."
const val tutorialPart2 = "you see that the Frame of that Tile is green now.\n" +
        "Now click on the lower right blue Circle with number 1\n"
const val tutorialPart3 =
    "you see that the frame of that tile is red for a moment.\n" +
            "That is because the Tiles do not fit together.\n" +
            "For Tiles to fit, the must have the same Color, Shape or Number, therefore the Name CoShaNu.\n" +
            "Now click on the lower left blue triangle with the Number 3.\n"
const val tutorialPart3b =
    "Both Tiles disappear, because this fits.\n" +
            "Yes, this game was lost.\n" +
            "But no problem, just click on 'restart Game'\n"
const val tutorialPart4 = "Your goal to win is to remove all Tiles. But be careful to not remove the wrong ones, as shown before.\n" +
        "Now choose the upper left green Triangle with Number 3 ...\n"
const val tutorialPart4b = "Your goal to win is to remove all Tiles. But be careful to not remove the wrong ones, as shown before.\n" +
        "... and then the yellow Triangle with the Number 3.\n"
const val tutorialPart5 =
    "These Tiles are played and only the Blue Tiles are there.\n" +
            "Now click on the lower left blue Triangle with the Number 3."
const val tutorialPart5b = "These Tiles are played and only the Blue Tiles are there.\n" +
        "Now click on the lower right blue Circle with the Number 1."
const val tutorialPart6 = "Congratulations! You have finished the Tutorial.\n" +
        "Now you know how to play the game.\n" +
        "You can start a new game with the Menu above."


// todo add a list of tiles the user is allowed to click on
data class TutorialStep(val step: Int, val text: String, val tile: TileData?)

class Tutorial {
    private var tutorialSteps: List<TutorialStep> = listOf()
    private var tiles: List<TileData> = listOf()

    private val currentStepState: MutableState<TutorialStep?> = mutableStateOf(null)
    private val activeState: MutableState<Boolean> = mutableStateOf(false)
    fun startTutorial() {
        activeState.value = true
        when (GameStateHolder.gameMode.value) {
            GameMode.SINGLE_ELEMENT -> {
                tiles = listOf(
                    TileData(
                        color = Color.Green,
                        shape = ShapeEnum.TRIANGLE,
                        number = 3,
                        chosenForPlay = true,
                        played = false
                    ),
                    TileData(
                        color = Color.Yellow,
                        shape = ShapeEnum.TRIANGLE,
                        number = 3,
                        chosenForPlay = true,
                        played = false
                    ),
                    TileData(
                        color = Color.Blue,
                        shape = ShapeEnum.TRIANGLE,
                        number = 3,
                        chosenForPlay = true,
                        played = false
                    ),
                    TileData(
                        color = Color.Blue,
                        shape = ShapeEnum.CIRCLE,
                        number = 1,
                        chosenForPlay = true,
                        played = false
                    )
                )
                tutorialSteps = listOf(
                    TutorialStep(1, tutorialPart1, tiles[0]), // #0
                    TutorialStep(2, tutorialPart2, tiles[3]), // last tile #3
                    TutorialStep(3, tutorialPart3, tiles[2]), // 3rd tile #2
                    TutorialStep(4, tutorialPart3b, null), // no tiles, only restart game
                    TutorialStep(5, tutorialPart4, tiles[0]), // 1st tile #0
                    TutorialStep(6, tutorialPart4b, tiles[1]), // 2nd tile #1
                    TutorialStep(7, tutorialPart5,  tiles[2]), // 3rd tile #2
                    TutorialStep(8, tutorialPart5b,  tiles[3]), // 4th tile #3
                    TutorialStep(9, tutorialPart6, null) // end of tutorial

                    // todo add example for selecting and deselecting an element
                )
            }
            GameMode.TWO_ELEMENTS -> {
                // todo add tutorial for two elements
            }
            else -> {
                // should not happen
                throw RuntimeException("GameMode not valid")
            }
        }

        currentStepState.value = tutorialSteps[0]
    }

    fun endTutorial() {
        activeState.value = false
        currentStepState.value = null

    }

    fun getCurrentTutorialText(): String = currentStepState.value?.text ?: ""

    // todo remove tutorialSteps.step - its a list (??)
    fun nextStep() {
        if (activeState.value && currentStepState.value != null) {
            val nextStep = currentStepState.value!!.step + 1
            currentStepState.value = tutorialSteps.find { it.step == nextStep }
        }
    }

    fun isAllowedTile(tileData: TileData): Boolean = currentStepState.value?.tile?.same(tileData) ?: false

    fun getTileList(): List<TileData> = tiles

    fun isTutorial(): Boolean = activeState.value
}
