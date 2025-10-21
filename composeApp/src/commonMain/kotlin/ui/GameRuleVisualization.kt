package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hyperether.resources.stringResource
import compose.icons.Octicons
import compose.icons.octicons.ArrowRight24
import compose.icons.octicons.X24
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.next_step
import coshanu.composeapp.generated.resources.tutorial_rule_examples
import game.GameStateHolder
import game.enums.GameMode
import game.enums.ShapeEnum
import game.newTileData
import game.TileData
import game.enums.GameState
import game.enums.ScreenType
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize
import util.colorFilter
import util.modeDependantColor

var gameRuleElementSize = 150.dp

data class RuleExample(
    val left: TileData,
    val right: TileData,
    val fits: Boolean
)

@Composable
fun GameRuleVisualization(gameMode: GameMode) {

    /*
    for SINGLE_ELEMENT:
    examples of same color, shape or number does fit
    examples of something that does not fit
    for TWO_ELEMENTS & TWO_ELEMENTS_WITH_TIMER:
    examples of two elements fitting (color & shape, color & number, shape & number)
    examples of something that does not fit, but fits for SINGLE_ELEMENT
     */

    val ruleExamples = when (gameMode) {
        GameMode.SINGLE_ELEMENT -> listOf(
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Blue, ShapeEnum.CIRCLE, 1), true),
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Red, ShapeEnum.TRIANGLE, 1), true),
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Red, ShapeEnum.CIRCLE, 3), true),
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Red, ShapeEnum.CIRCLE, 1), false)
        )

        GameMode.TWO_ELEMENTS, GameMode.TWO_ELEMENTS_WITH_TIMER -> listOf(
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Blue, ShapeEnum.TRIANGLE, 1), true),
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Blue, ShapeEnum.CIRCLE, 3), true),
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Red, ShapeEnum.TRIANGLE, 3), true),
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Blue, ShapeEnum.CIRCLE, 1), false),
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Red, ShapeEnum.TRIANGLE, 1), false),
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Red, ShapeEnum.CIRCLE, 3), false),
            RuleExample(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3), newTileData(Color.Red, ShapeEnum.CIRCLE, 1), false),
        )
    }

    gameRuleElementSize = if (gameMode == GameMode.SINGLE_ELEMENT) 150.dp else 100.dp

    if (GameStateHolder.isGameState(GameState.RUNNING)) {
        Button(onClick = { GameStateHolder.tutorial.nextStep() }) {
            Text(stringResource(Res.string.next_step))
        }
    }

    Box(
        modifier = Modifier
            .height(gameRuleElementSize * ruleExamples.size)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        RuleExamplesArea(ruleExamples)
    }
}

@Composable
fun RuleExamplesArea(examples: List<RuleExample>) {
    Column {
        Text(stringResource(Res.string.tutorial_rule_examples),
            fontSize = standardTextSize.value,
            lineHeight = standardLineHeight.value
        )

        when (UiStateHolder.screenType.value) {
            ScreenType.PORTRAIT -> {
                for (example in examples) {
                    Row(modifier = Modifier.height(gameRuleElementSize)) {
                        RuleExampleRow(example)
                    }
                }
            }
            ScreenType.LANDSCAPE -> {
                Row {
                    Column {
                        for (example in examples.filterIndexed { index, _ -> index % 2 == 0 }) {
                            Row(modifier = Modifier.height(gameRuleElementSize)) {
                                RuleExampleRow(example)
                            }

                        }
                    }
                    Column {
                        for (example in examples.filterIndexed { index, _ -> index % 2 == 1 }) {
                            Row(modifier = Modifier.height(gameRuleElementSize)) {
                                RuleExampleRow(example)
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RuleExampleRow(example: RuleExample) {
    TileCard(example.left)
    if (example.fits) {
        FitsImage()
    } else {
        DoesNotFitImage()
    }
    TileCard(example.right)
}

@Composable
fun FitsImage() {
    Image(
        imageVector = Octicons.ArrowRight24,
        contentDescription = "arrow right",
        modifier = Modifier.size(gameRuleElementSize),
        colorFilter = colorFilter(Color.Green.modeDependantColor)
    )
}

@Composable
fun DoesNotFitImage() {
    Image(
        imageVector = Octicons.X24,
        contentDescription = "x",
        modifier = Modifier.size(gameRuleElementSize),
        colorFilter = colorFilter(Color.Red.modeDependantColor)
    )
}
