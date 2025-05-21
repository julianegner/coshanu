package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hyperether.resources.stringResource
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.click_anywhere
import coshanu.composeapp.generated.resources.tap_anywhere
import game.GameStateHolder
import game.TileData
import game.enums.ShapeEnum
import game.newTileData
import isClickPlatform
import isLandscape
import kotlin.math.PI
import landscapeOrAndroid
import ui.UiStateHolder.standardTextSize
import util.clickableHoverIcon


@Composable
fun GameSymbol() {

    val hintTextResource = if (isClickPlatform) {
        Res.string.click_anywhere

    } else {
        Res.string.tap_anywhere
    }

    Text(
        text = stringResource(hintTextResource),
        fontSize = standardTextSize.value,
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .offset(y =
                if (isLandscape)
                    250.dp
                else
                    820.dp
                    // 500.dp - 500 is inside of the circle
            )
    )
    CircleOfTiles(
        elements = listOf(
            newTileData(Color.Blue, ShapeEnum.TRIANGLE, 3),
            newTileData(Color.Red, ShapeEnum.CIRCLE, 1),
            newTileData(Color.Cyan, ShapeEnum.SQUARE, 2),
            newTileData(Color.Green, ShapeEnum.HEXAGON, 3),
            newTileData(Color.DarkGray, ShapeEnum.OCTAGON, 4),
            newTileData(Color.Magenta, ShapeEnum.PENTAGON, 5)
        ),
        modifier =
        Modifier
            .fillMaxSize()
            .clickable(interactionSource = null, indication = null) {
                GameStateHolder.openMenu()
            }
            .clickableHoverIcon()
    )
}

@Composable
fun CircleOfTiles(elements: List<TileData>,
                  modifier: Modifier = Modifier) {
    val radius = if (landscapeOrAndroid) 50.dp else 220.dp
    val centerX = 0.dp
    val centerY = if (landscapeOrAndroid) 100.dp else 400.dp
    val angleStep = 360 / elements.size

    Box(modifier = modifier,
        Alignment.TopCenter
    ) {
        Box(modifier = Modifier
            .size(
                if (landscapeOrAndroid) 50.dp else 150.dp)
        ) {
            elements.forEachIndexed { index, element ->
                val angle = toRadians((index * angleStep).toDouble())
                val xOffset = (radius.value * kotlin.math.cos(angle)).dp
                val yOffset = (radius.value * kotlin.math.sin(angle)).dp

                Box(
                    modifier = Modifier
                        .offset(x = centerX + xOffset, y = centerY + yOffset)
                ) {
                    Card(
                        border = null,
                        elevation = 0.dp,
                        backgroundColor = Color.Transparent,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(10.dp)
                            .size(100.dp)
                    ) {
                        when (element.shape) {
                            ShapeEnum.TRIANGLE -> {
                                Tile(
                                    tileDataState = mutableStateOf(element),
                                    cardBorderState = mutableStateOf(null),
                                    modifier = Modifier.size(100.dp)
                                        .offset(y = -5.dp),
                                    boardSize = 12,
                                    displayText = false
                                )
                            }

                            ShapeEnum.CIRCLE -> {
                                Tile(
                                    tileDataState = mutableStateOf(element),
                                    cardBorderState = mutableStateOf(null),
                                    boardSize = 20,
                                    displayText = false
                                )
                            }

                            else -> {
                                Tile(
                                    tileDataState = mutableStateOf(element),
                                    cardBorderState = mutableStateOf(null),
                                    modifier = Modifier,
                                    boardSize = 12,
                                    displayText = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun toRadians(deg: Double): Double = deg / 180.0 * PI
