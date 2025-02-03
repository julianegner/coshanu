package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import game.ShapeEnum
import game.TileData
import kotlin.math.PI

@Composable
fun GameSymbol() {
    CircleOfTiles()
}

@Composable
fun CircleOfTiles() {

    val elements = listOf(
        TileData(
            color = Color.Blue,
            shape = ShapeEnum.TRIANGLE,
            number = 3,
            chosenForPlay = true,
            played = false
        ),
        TileData(
            color = Color.Red,
            shape = ShapeEnum.CIRCLE,
            number = 1,
            chosenForPlay = true,
            played = false
        ),
        TileData(
            color = Color.Cyan,
            shape = ShapeEnum.SQUARE,
            number = 2,
            chosenForPlay = true,
            played = false
        ),
        TileData(
            color = Color.Green,
            shape = ShapeEnum.HEXAGON,
            number = 3,
            chosenForPlay = true,
            played = false
        ),
        TileData(
            color = Color.DarkGray,
            shape = ShapeEnum.OKTAGON,
            number = 4,
            chosenForPlay = true,
            played = false
        ),
        TileData(
            color = Color.Magenta,
            shape = ShapeEnum.PENTAGON,
            number = 5,
            chosenForPlay = true,
            played = false
        )
    )

    val radius = 50.dp
    val centerX = 100.dp
    val centerY = 100.dp
    val angleStep = 360 / elements.size

    Box(modifier = Modifier.size(50.dp)) {
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
                                    .offset(y = -20.dp),
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

fun toRadians(deg: Double): Double = deg / 180.0 * PI
