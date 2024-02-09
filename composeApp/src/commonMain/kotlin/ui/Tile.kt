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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import game.ShapeEnum
import game.TileData
import game.match
import util.runOnMainAfter


@Composable
fun Tile(tileDataState: MutableState<TileData>,
         selected: MutableState<Pair<TileData?,TileData?>>,
         cardBorderState: MutableState<BorderStroke?>,
         gameState: MutableState<String>
         ) {

    val modifier = Modifier
         .clickable(onClick = { tileSelected(tileDataState, selected, cardBorderState, gameState) })

    when (tileDataState.value.shape) {
        ShapeEnum.CIRCLE -> {
            Canvas(
                modifier = modifier
            ) {
                drawCircle(
                    radius = 150f,
                    color = tileDataState.value.color)
            }
        }

        ShapeEnum.TRIANGLE ->   polygonBox(color = tileDataState.value.color, sides = 3, rotation = 30f, modifier = modifier.offset(y = 20.dp))
        ShapeEnum.SQUARE ->     polygonBox(color = tileDataState.value.color, sides = 4, rotation = 45f, modifier = modifier)
        ShapeEnum.PENTAGON ->   polygonBox(color = tileDataState.value.color, sides = 5, rotation = -18f, modifier = modifier)
        ShapeEnum.HEXAGON ->    polygonBox(color = tileDataState.value.color, sides = 6, rotation = 90f, modifier = modifier)
        ShapeEnum.OKTAGON ->    polygonBox(color = tileDataState.value.color, sides = 8, rotation = 22.5f, modifier = modifier)
    }

    val xOffset = if (tileDataState.value.number < 10) {70.dp} else {30.dp}
    val yOffset = if (tileDataState.value.shape == ShapeEnum.TRIANGLE) {60.dp} else {50.dp}
    Text(
        modifier = Modifier.offset(x = xOffset, y = yOffset),
        text = tileDataState.value.number.toString(),
        fontSize = TextUnit(4f, TextUnitType.Em)
    )
}

fun tileSelected(
    tileDataState: MutableState<TileData>,
    selected: MutableState<Pair<TileData?,TileData?>>,
    cardBorderState: MutableState<BorderStroke?>,
    gameState: MutableState<String>
) {
    if (selected.value.first == null) {
        // select first Tile
        selected.value = Pair(tileDataState.value, null)

        tileDataState.value.borderStroke = BorderStroke(5.dp, Color.Green)
        cardBorderState.value = BorderStroke(5.dp, Color.Green)

    } else if (selected.value.first == tileDataState.value) {
        // first Tile is deselected by clicking again
        selected.value = Pair(null, null)
        tileDataState.value.borderStroke = null
        cardBorderState.value = null
    } else if (selected.value.first != null && selected.value.first!!.match(tileDataState.value)) {
        // second Tile does match first Tile, both are played
        selected.value = Pair(selected.value.first, tileDataState.value)

        // todo check which is needed
        val first = selected.value.first!!
        val second = selected.value.second!!
        first.played = true
        second.played = true
        selected.value = Pair(first, second)

    } else if (selected.value.first != null && !selected.value.first!!.match(tileDataState.value)) {
        // second Tile does not match first Tile
        tileDataState.value.borderStroke = BorderStroke(5.dp, Color.Red)
        cardBorderState.value = BorderStroke(5.dp, Color.Red)

        runOnMainAfter(2000L) {cardBorderState.value = null}
    }

    checkGameFinished(gameState)
}

@Composable
fun polygonBox(color: Color, sides: Int, rotation: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .clip(Polygon(sides = sides, rotation = rotation))
        .background(color)
    ) {}
}

fun checkGameFinished(gameText: MutableState<String>) {
    val tilesInGame = boardData.tiles
        .filter { it.chosenForPlay }
        .filter { !it.played }

    if (boardData.tiles
        .filter { it.chosenForPlay }
        .filter { !it.played }
            .isEmpty()) {
        gameText.value = "W"
    } else if (boardData.lostGame()) {
        gameText.value = "L"
    } else {
        gameText.value = "R" + tilesInGame.size
    }
}

