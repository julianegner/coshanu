package game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

data class TutorialStep(val step: Int, val stringId: StringResource, val tile: TileData?)

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
                    TutorialStep(1, Res.string.tutorial_one_1, tiles[0]),
                    TutorialStep(2, Res.string.tutorial_one_2, tiles[3]),
                    TutorialStep(3, Res.string.tutorial_one_3, tiles[2]),
                    TutorialStep(4, Res.string.tutorial_one_4, null),
                    TutorialStep(5, Res.string.tutorial_one_5, tiles[0]),
                    TutorialStep(6, Res.string.tutorial_one_6, tiles[1]),
                    TutorialStep(7, Res.string.tutorial_one_7,  tiles[2]),
                    TutorialStep(8, Res.string.tutorial_one_8,  tiles[3]),
                    TutorialStep(9, Res.string.tutorial_one_9, null)
                    // todo add example for selecting and deselecting an element
                )
            }
            GameMode.TWO_ELEMENTS -> {
                tiles = listOf(
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

                tutorialSteps = listOf(
                    TutorialStep(1, Res.string.tutorial_two_1, tiles[0]),
                    TutorialStep(2, Res.string.tutorial_two_2, tiles[1]),
                    TutorialStep(3, Res.string.tutorial_two_3, tiles[15]),
                    TutorialStep(4, Res.string.tutorial_two_4, null),
                    // todo add example for selecting and deselecting an element
                    // todo show that one element is not enough
                    TutorialStep(5, Res.string.tutorial_two_5, tiles[0]),
                    TutorialStep(6, Res.string.tutorial_two_6, tiles[8]),
                    TutorialStep(7, Res.string.tutorial_two_7, tiles[12]),
                    TutorialStep(8, Res.string.tutorial_two_8, tiles[15]),
                    TutorialStep(9, Res.string.tutorial_two_9, tiles[5]),
                    TutorialStep(10, Res.string.tutorial_two_10, tiles[6]),
                    TutorialStep(11, Res.string.tutorial_two_11, tiles[1]),
                    TutorialStep(12, Res.string.tutorial_two_12, tiles[10]),
                    TutorialStep(13, Res.string.tutorial_two_13, tiles[11]),
                    TutorialStep(14, Res.string.tutorial_two_14, tiles[13]),
                    TutorialStep(15, Res.string.tutorial_two_15, tiles[3]),
                    TutorialStep(16, Res.string.tutorial_two_16, tiles[4]),
                    TutorialStep(17, Res.string.tutorial_two_17, tiles[2]),
                    TutorialStep(18, Res.string.tutorial_two_18, tiles[14]),
                    TutorialStep(19, Res.string.tutorial_two_19, tiles[7]),
                    TutorialStep(20, Res.string.tutorial_two_20, tiles[9]),
                    TutorialStep(21, Res.string.tutorial_two_21, null)
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

    @Composable
    fun getCurrentTutorialText(): String {
        val text = currentStepState.value?.stringId?.let { stringResource(it) } ?: ""
        return if (currentStepState.value?.tile == null) {
            text
        } else {
            text.replace("[]", currentStepState.value?.tile?.tutorialString() ?: "")
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

    fun isRestartAllowed(): Boolean = currentStepState.value?.tile == null

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
