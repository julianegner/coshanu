package game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.*
import game.enums.GameMode
import game.enums.ShapeEnum
import org.jetbrains.compose.resources.StringResource
import com.hyperether.resources.stringResource
import ui.UiStateHolder

data class TutorialStep(
    val step: Int,
    val stringId: StringResource,
    val tile: TileData?,
    val patternStringId: StringResource? = null,
    val isRestart: Boolean = false
    )

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
                    TutorialStep(0, Res.string.tutorial_one_0, null),
                    TutorialStep(1, Res.string.tutorial_one_1, tiles[0]),
                    TutorialStep(2, Res.string.tutorial_one_2, tiles[3]),
                    TutorialStep(3, Res.string.tutorial_one_3, tiles[2]),
                    TutorialStep(4, Res.string.tutorial_one_4, null, isRestart = true),
                    TutorialStep(5, Res.string.tutorial_one_5, tiles[0]),
                    TutorialStep(6, Res.string.tutorial_one_6, tiles[1]),
                    TutorialStep(7, Res.string.tutorial_one_7,  tiles[2], Res.string.tutorial_one_7_pattern),
                    TutorialStep(8, Res.string.tutorial_one_8,  tiles[3], Res.string.tutorial_one_8_pattern),
                    TutorialStep(9, Res.string.tutorial_one_9, null)
                    // todo add example for selecting and deselecting an element
                )
            }
            GameMode.TWO_ELEMENTS, GameMode.TWO_ELEMENTS_WITH_TIMER -> {
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

                    TutorialStep(0,
                        if (GameStateHolder.gameMode.value == GameMode.TWO_ELEMENTS_WITH_TIMER) {
                            Res.string.tutorial_two_0_with_timer
                        } else {
                            Res.string.tutorial_two_0
                        }, null),
                    TutorialStep(1, Res.string.tutorial_two_1, tiles[0]),
                    TutorialStep(2, Res.string.tutorial_two_2, tiles[1]),
                    TutorialStep(3, Res.string.tutorial_two_3, tiles[15]),
                    TutorialStep(4, Res.string.tutorial_two_4, null, isRestart = true),
                    // todo add example for selecting and deselecting an element
                    // todo show that one element is not enough
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
        // only fetch tutorial text if tutorial is active
        if (!activeState.value) {
            return ""
        }
        val stringResourceId =
            if (UiStateHolder.colorActive.value)
                currentStepState.value?.stringId
            else // for colorless mode
                currentStepState.value?.patternStringId?: currentStepState.value?.stringId

        val text = stringResourceId?.let { stringResource(it) } ?: ""
        // workaround: this only works for the first step after restart if the StringResource is fetched here
        val wedontneedthis = "one: ${Res.string.tutorial_one_5} ${stringResource(Res.string.tutorial_one_5)} two: ${Res.string.tutorial_two_5} ${stringResource(Res.string.tutorial_two_5)}"
        val returnText =  if (currentStepState.value?.tile == null) {
            text
        } else {
            text.replace("[]", currentStepState.value?.tile?.tutorialString() ?: "")
        }
        return returnText
    }

    // todo remove tutorialSteps.step - its a list (??)
    fun nextStep() {
        if (activeState.value && currentStepState.value != null) {
            val nextStep = currentStepState.value!!.step + 1
            currentStepState.value = tutorialSteps.find { it.step == nextStep }
        }
    }

    fun isAllowedTile(tileData: TileData): Boolean = currentStepState.value?.tile?.same(tileData) ?: false

    fun isRestartStep(): Boolean = isTutorial() && currentStepState.value?.tile == null && currentStepState.value?.isRestart == true

    fun getTileList(): List<TileData> = tiles

    fun isTutorial(): Boolean = activeState.value

    // needed for testing
    fun getTutorialSteps(): List<TutorialStep> = tutorialSteps
    // needed for testing
    fun getCurrentStep(): TutorialStep? = currentStepState.value
}
