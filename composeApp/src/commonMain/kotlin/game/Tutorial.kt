package game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

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
                    newTileData(Color.Green, ShapeEnum.TRIANGLE, 3),
                    newTileData(Color.Yellow, ShapeEnum.TRIANGLE, 3),
                    newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3),
                    newTileData(Color.Blue, ShapeEnum.CIRCLE, 1)
                )
                tutorialSteps = listOf(
                    TutorialStep(1, "Welcome to the Tutorial!\nPlease click on the upper left [].", tiles[0]),
                    TutorialStep(2, "you see that the Frame of that Tile is green now.\n" +
                            "Now click on the lower right []\n", tiles[3]),
                    TutorialStep(3, "you see that the frame of that tile is red for a moment.\n" +
                            "That is because the Tiles do not fit together.\n" +
                            "For Tiles to fit, the must have the same Color, Shape or Number, therefore the Name CoShaNu.\n" +
                            "Now click on the lower left [].\n", tiles[2]),
                    TutorialStep(4, "Both Tiles disappear, because this fits.\n" +
                            "Yes, this game was lost.\n" +
                            "But no problem, just click on 'restart Game'\n", null),
                    TutorialStep(5, "Your goal to win is to remove all Tiles. But be careful to not remove the wrong ones, as shown before.\n" +
                            "Now choose the upper left [] ...\n", tiles[0]),
                    TutorialStep(6, "Your goal to win is to remove all Tiles. But be careful to not remove the wrong ones, as shown before.\n" +
                            "... and then the [].\n", tiles[1]),
                    TutorialStep(7, "These Tiles are played and only the Blue Tiles are there.\n" +
                            "Now click on the lower left [].",  tiles[2]),
                    TutorialStep(8, "These Tiles are played and only the Blue Tiles are there.\n" +
                            "Now click on the lower right [].",  tiles[3]),
                    TutorialStep(9, "Congratulations! You have finished the Tutorial.\n" +
                            "Now you know how to play the game.\n" +
                            "You can start a new game with the Menu above.", null)

                    // todo add example for selecting and deselecting an element
                )
            }
            GameMode.TWO_ELEMENTS -> {
                tiles = listOf(
                    newTileData(Color.Yellow, ShapeEnum.TRIANGLE, 4),
                    newTileData(Color.Green, ShapeEnum.SQUARE, 4),
                    newTileData(Color.Blue, ShapeEnum.HEXAGON, 3),
                    newTileData(Color.Blue, ShapeEnum.OKTAGON, 1),
                    newTileData(Color.Blue, ShapeEnum.CIRCLE, 1),
                    newTileData(Color.Red, ShapeEnum.HEXAGON, 3),
                    newTileData(Color.Red, ShapeEnum.TRIANGLE, 3),
                    newTileData(Color.Blue, ShapeEnum.OKTAGON, 4),
                    newTileData(Color.Yellow, ShapeEnum.TRIANGLE, 2),
                    newTileData(Color.Blue, ShapeEnum.SQUARE, 4),
                    newTileData(Color.Green, ShapeEnum.HEXAGON, 4),
                    newTileData(Color.Green, ShapeEnum.PENTAGON, 2),
                    newTileData(Color.Yellow, ShapeEnum.PENTAGON, 4),
                    newTileData(Color.Green, ShapeEnum.SQUARE, 2),
                    newTileData(Color.Blue, ShapeEnum.PENTAGON, 3),
                    newTileData(Color.Yellow, ShapeEnum.SQUARE, 4)
                )

                tutorialSteps = listOf(
                    TutorialStep(1, "Welcome to the Tutorial!\nPlease click on the upper left [].", tiles[0]),
                    TutorialStep(2, "Now click on the [] right to the first Tile", tiles[1]),
                    TutorialStep(3,     "You see that the frame of that tile is red for a moment.\n" +
                            "That is because the Tiles do not fit together.\n\n" +
                            "For Tiles to fit, two of the elements Color, Shape or Number must be equal.\n" +
                            "That's why the Name is CoShaNu.\n" +
                            "Now click on the lower right [].\n", tiles[15]
                    ),
                    TutorialStep(4, "Both Tiles disappear, because this fits.\n" +
                            "Yes, this game was lost.\n" +
                            "But no problem, just click on 'restart Game'\n", null),
                    // todo add example for selecting and deselecting an element
                    // todo show that one element is not enough

                    TutorialStep(5, "Your goal to win is to remove all Tiles. But be careful to not remove the wrong ones, as shown before.\n" +
                            "Now choose the upper left [] ...\n", tiles[0]),
                    TutorialStep(6, "now choose the fitting [].\n", tiles[8]),
                    TutorialStep(7, "These Tiles are played - let's play the next pair!\n" +
                            "Now click on the [] and ...\n", tiles[12]),
                    TutorialStep(8, "... the [].\n", tiles[15]),
                    TutorialStep(9, "These Tiles are played - let's play the red pair!\n" +
                            "Now click on the [] and ...\n", tiles[5]),
                    TutorialStep(10, "... the [].\n", tiles[6]),
                    TutorialStep(11, "These Tiles are played - let's play the first green pair!\n" +
                            "Now click on the [] and ...\n", tiles[1]),
                    TutorialStep(12, "... the [].\n", tiles[10]),
                    TutorialStep(13, "These Tiles are played - let's play the green pair!\n" +
                            "Now click on the [] and ...\n", tiles[11]),
                    TutorialStep(14, "... the [].\n", tiles[13]),
                    TutorialStep(15, "These Tiles are played - let's play one of the blue pairs!\n" +
                            "Now click on the [] and ...\n", tiles[3]),
                    TutorialStep(16, "... the [].\n", tiles[4]),
                    TutorialStep(17, "These Tiles are played - let's play the second to last blue pair!\n" +
                            "Now click on the [] and ...\n", tiles[2]),
                    TutorialStep(18, "... the [].\n", tiles[14]),
                    TutorialStep(19, "These Tiles are played - let's play the last pair!\n" +
                            "Now click on the [] and ...\n", tiles[7]),
                    TutorialStep(20, "... the [].\n", tiles[9]),
                    TutorialStep(21, "Congratulations! You have finished the Tutorial.\n" +
                            "Now you know how to play the game.\n" +
                            "You can start a new game with the Menu above.", null)
                )
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

    fun getCurrentTutorialText(): String {
        val text = currentStepState.value?.text ?: ""
        if (currentStepState.value?.tile == null) {
            return text
        } else {
            return text.replace("[]", currentStepState.value?.tile?.tutorialString() ?: "")
        }
    }

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

    private fun newTileData(color: Color, shape: ShapeEnum, number: Int): TileData =
        TileData(
            color = color,
            shape = shape,
            number = number,
            chosenForPlay = true,
            played = false
        )
}
