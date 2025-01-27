package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import game.*
import game.GameStateHolder.level
import game.GameStateHolder.tutorialTextState
import tutorialPart1
import tutorialPart2
import tutorialPart3
import tutorialPart3b
import tutorialPart4
import tutorialPart5
import util.runOnMainAfter


@Composable
fun Tile(tileDataState: MutableState<TileData>,
         cardBorderState: MutableState<BorderStroke?>,
         boardSize: Int = GameStateHolder.boardDataState.value.size,
            modifier: Modifier = Modifier.clickable(onClick = { tileSelected(tileDataState, cardBorderState) }),
         displayText: Boolean = true
         ) {

    when (tileDataState.value.shape) {
        ShapeEnum.CIRCLE -> {
            Canvas(
                modifier = modifier
            ) {
                drawCircle(
                    radius = getCircleRadius(boardSize),
                    color = tileDataState.value.color)
            }
        }

        ShapeEnum.TRIANGLE ->   polygonBox(color = tileDataState.value.color, sides = 3, rotation = 30f, modifier = modifier.offset(y = 20.dp))
        ShapeEnum.SQUARE ->     polygonBox(color = tileDataState.value.color, sides = 4, rotation = 45f, modifier = modifier)
        ShapeEnum.PENTAGON ->   polygonBox(color = tileDataState.value.color, sides = 5, rotation = -18f, modifier = modifier)
        ShapeEnum.HEXAGON ->    polygonBox(color = tileDataState.value.color, sides = 6, rotation = 90f, modifier = modifier)
        ShapeEnum.OKTAGON ->    polygonBox(color = tileDataState.value.color, sides = 8, rotation = 22.5f, modifier = modifier)
    }

    if (displayText) {
        TileText(
            boardSize,
            tileDataState.value.number, tileDataState.value.shape == ShapeEnum.TRIANGLE
        )
    }
}

fun getCircleRadius(boardSize: Int) = when (boardSize) {
    2 -> 150f
    4 -> 150f
    8 -> 70f
    12 -> 40f
    20 -> 30f // for the GameSymbol
    else -> 40f
}

@Composable
fun TileText(boardSize: Int,
             tileNumber: Int, isTriangle: Boolean) {
    var xOffset: Dp
    var yOffset: Dp
    var textSize: Float

    when (boardSize) {
        2 -> {
            xOffset = if (tileNumber < 10) {150.dp} else {80.dp}
            yOffset = if (isTriangle) {140.dp} else {120.dp}
            textSize = 6f
        }
        4 -> {
            xOffset = if (tileNumber< 10) {70.dp} else {30.dp}
            yOffset = if (isTriangle) {60.dp} else {50.dp}
            textSize = 4f
        }
        8 -> {
            xOffset = if (tileNumber < 10) {30.dp} else {20.dp}
            yOffset = if (isTriangle) {40.dp} else {20.dp}
            textSize = 2f
        }
        12 -> {
            xOffset = if (tileNumber < 10) {10.dp} else {0.dp}
            yOffset = if (isTriangle) {20.dp} else {0.dp}
            textSize = 2f
        }
        else -> {
            xOffset = if (tileNumber < 10) {30.dp} else {0.dp}
            yOffset = if (isTriangle) {40.dp} else {20.dp}
            textSize = 2f
        }
    }

    Text(
        modifier = Modifier.offset(x = xOffset, y = yOffset),
        text = tileNumber.toString(),
        fontSize = TextUnit(textSize, TextUnitType.Em)
    )
}

fun tileSelected(
    tileDataState: MutableState<TileData>,
    cardBorderState: MutableState<BorderStroke?>,
) {
    if (GameStateHolder.selected.value.first == null) {
        // select first Tile
        GameStateHolder.updateSelected(Pair(tileDataState.value, null))

        tileDataState.value.borderStroke = BorderStroke(5.dp, Color.Green)
        cardBorderState.value = BorderStroke(5.dp, Color.Green)

        if (level.value == 0) {
            if (GameStateHolder.selected.value.first != null
                && GameStateHolder.selected.value.second == null
                && tutorialTextState.value == tutorialPart1
                && GameStateHolder.selected.value.first!!.number == 3
                && GameStateHolder.selected.value.first!!.shape == ShapeEnum.TRIANGLE
                && GameStateHolder.selected.value.first!!.color == Color.Green) {
                tutorialTextState.value = tutorialPart2
            }
        }

    } else if (GameStateHolder.selected.value.first == tileDataState.value) {
        // first Tile is deselected by clicking again
        GameStateHolder.resetSelected()
        tileDataState.value.borderStroke = null
        cardBorderState.value = null
    } else if (GameStateHolder.selected.value.first != null && GameStateHolder.selected.value.first!!.match(tileDataState.value)) {
        // second Tile does match first Tile, both are played
        GameStateHolder.updateSelected(Pair(GameStateHolder.selected.value.first, tileDataState.value))

        val first = GameStateHolder.selected.value.first!!
        val second = GameStateHolder.selected.value.second!!
        first.played = true
        second.played = true
        GameStateHolder.updateSelected(Pair(first, second))
        GameStateHolder.playCard(first, second)

        if (GameStateHolder.level.value == 0) {
            if (GameStateHolder.tutorialTextState.value == tutorialPart4
                && GameStateHolder.selected.value.first!!.number == 3
                && GameStateHolder.selected.value.first!!.shape == ShapeEnum.TRIANGLE
                && GameStateHolder.selected.value.first!!.color == Color.Green
                && tileDataState.value.number == 3
                && tileDataState.value.shape == ShapeEnum.TRIANGLE
                && tileDataState.value.color == Color.Yellow) {
                GameStateHolder.updateTutorialText(tutorialPart5)
            } else if (GameStateHolder.tutorialTextState.value == tutorialPart3
                && GameStateHolder.selected.value.first!!.number == 3
                && GameStateHolder.selected.value.first!!.shape == ShapeEnum.TRIANGLE
                && GameStateHolder.selected.value.first!!.color == Color.Green
                && tileDataState.value.number == 3
                && tileDataState.value.shape == ShapeEnum.TRIANGLE
                && tileDataState.value.color == Color.Blue) {
                GameStateHolder.updateTutorialText(tutorialPart3b)
            }
        }

    } else if (GameStateHolder.selected.value.first != null && !GameStateHolder.selected.value.first!!.match(tileDataState.value)) {
        // second Tile does not match first Tile
        tileDataState.value.borderStroke = BorderStroke(5.dp, Color.Red)
        cardBorderState.value = BorderStroke(5.dp, Color.Red)

        runOnMainAfter(2000L) { cardBorderState.value = null }

        if (GameStateHolder.level.value == 0) {
            if (GameStateHolder.tutorialTextState.value == tutorialPart2
                && GameStateHolder.selected.value.first!!.number == 3
                && GameStateHolder.selected.value.first!!.shape == ShapeEnum.TRIANGLE
                && GameStateHolder.selected.value.first!!.color == Color.Green
                && tileDataState.value.number == 1
                && tileDataState.value.shape == ShapeEnum.CIRCLE
                && tileDataState.value.color == Color.Blue) {
                GameStateHolder.updateTutorialText(tutorialPart3)
            }
        }
    }

    GameStateHolder.checkGameFinished()
}

@Composable
fun polygonBox(color: Color, sides: Int, rotation: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .clip(Polygon(sides = sides, rotation = rotation))
        .background(color)
    ) {}
}



