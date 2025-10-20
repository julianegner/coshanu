package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.Octicons
import compose.icons.octicons.ArrowRight24
import compose.icons.octicons.X24
import game.enums.GameMode
import game.enums.ShapeEnum
import game.newTileData
import util.colorFilter
import util.modeDependantColor

val gameRuleElementSize = 150.dp

@Composable
fun GameRuleVisualization(gameMode: GameMode) {

    /*
    todo implement visialization of game rules
    for SINGLE_ELEMENT:
    examples of same color, shape or number does fit
    examples of something that does not fit
    for TWO_ELEMENTS & TWO_ELEMENTS_WITH_TIMER:
    examples of two elements fitting (color & shape, color & number, shape & number)
    examples of something that does not fit, but fits for SINGLE_ELEMENT
     */

    Box(
        modifier = Modifier
            .height(gameRuleElementSize * 4)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {

        when (gameMode) {
            GameMode.SINGLE_ELEMENT -> {
                // Visualization for SINGLE_ELEMENT

                // todo add description text
                // todo refactor to avoid code duplication
                Column {
                    Row(modifier = Modifier.height(gameRuleElementSize)) {
                        TileCard(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3))
                        FitsImage()
                        TileCard(newTileData(Color.Blue, ShapeEnum.CIRCLE, 1))
                    }
                    Row(modifier = Modifier.height(gameRuleElementSize)) {
                        TileCard(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3))
                        FitsImage()
                        TileCard(newTileData(Color.Red, ShapeEnum.TRIANGLE, 1))
                    }
                    Row(modifier = Modifier.height(gameRuleElementSize)) {
                        TileCard(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3))
                        FitsImage()
                        TileCard(newTileData(Color.Red, ShapeEnum.CIRCLE, 3))
                    }
                    Row(modifier = Modifier.height(gameRuleElementSize)) {
                        TileCard(newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3))
                        DoesNotFitImage()
                        TileCard(newTileData(Color.Red, ShapeEnum.CIRCLE, 1))
                    }
                }
            }

            GameMode.TWO_ELEMENTS, GameMode.TWO_ELEMENTS_WITH_TIMER -> {
                // Visualization for TWO_ELEMENTS and TWO_ELEMENTS_WITH_TIMER

                // todo
            }
        }
    }

}

/*
thumbs up
https://commons.wikimedia.org/wiki/File:Noto_Emoji_Oreo_1f44d.svg
thumbs down
https://commons.wikimedia.org/wiki/File:Noto_Emoji_Oreo_1f44e.svg

https://commons.wikimedia.org/w/index.php?title=Category:Noto_Color_Emoji_Pie&filefrom=Noto+Emoji+Pie+1f530.svg#mw-category-media
 */
@Composable
fun FitsImage() {
    /*
    Image( // source: https://commons.wikimedia.org/wiki/File:Noto_Emoji_Oreo_1f449.svg
        // todo find good image
        painter = painterResource(Res.drawable.Noto_Emoji_Fingerpointing),
        contentDescription = null,
        modifier = Modifier.size(gameRuleElementSize)
    )
     */

    Image(
        imageVector = Octicons.ArrowRight24,
        contentDescription = "arrow right",
        modifier = Modifier.size(gameRuleElementSize),
        colorFilter = colorFilter(Color.Green.modeDependantColor)
    )
}

@Composable
fun DoesNotFitImage() {
    /*
    Image( // source: https://commons.wikimedia.org/wiki/File:Noto_Emoji_Oreo_1f449.svg
        // todo find good image
        painter = painterResource(Res.drawable.stopwatch),
        contentDescription = null,
        modifier = Modifier.size(gameRuleElementSize)
    )
     */
    Image(
        imageVector = Octicons.X24,
        contentDescription = "x",
        modifier = Modifier.size(gameRuleElementSize),
        colorFilter = colorFilter(Color.Red.modeDependantColor)
    )
}
