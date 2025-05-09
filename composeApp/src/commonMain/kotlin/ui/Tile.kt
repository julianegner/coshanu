package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import game.*
import game.enums.GameState
import game.enums.ShapeEnum
import isPlatformAndroid
import util.runOnMainAfter


@Composable
fun Tile(tileDataState: MutableState<TileData>,
         cardBorderState: MutableState<BorderStroke?>,
         boardSize: Int = GameStateHolder.boardDataState.value.size,
            modifier: Modifier = Modifier.clickable(onClick = { tileSelected(tileDataState, cardBorderState) }),
         displayText: Boolean = true
         ) {

    val color = tileDataState.value.getColor()

    when (tileDataState.value.shape) {
        ShapeEnum.CIRCLE -> polygonBox(color = color, sides = 1000, rotation = 0f,
            modifier = when (boardSize) {
                2 -> modifier.padding(20.dp)
                4 -> modifier.padding(15.dp)
                8 -> modifier.padding(5.dp)
                12 -> modifier.padding(2.dp)
                20 -> modifier // for the GameSymbol
                else -> modifier.padding(5.dp)
            })
        ShapeEnum.TRIANGLE ->   polygonBox(color = color, sides = 3, rotation = 30f,
            modifier = modifier.offset(y = if (boardSize < 8 || boardSize == 20) 15.dp else 5.dp)
        )
        ShapeEnum.SQUARE ->     polygonBox(color = color, sides = 4, rotation = 45f, modifier = modifier)
        ShapeEnum.PENTAGON ->   polygonBox(color = color, sides = 5, rotation = -18f, modifier = modifier)
        ShapeEnum.HEXAGON ->    polygonBox(color = color, sides = 6, rotation = 90f, modifier = modifier)
        ShapeEnum.OCTAGON ->    polygonBox(color = color, sides = 8, rotation = 22.5f, modifier = modifier)
    }

    if (displayText) {
        TileText(
            boardSize,
            tileDataState.value.number,
            if (color == Color.DarkGray || color == Color.Blue) Color.White else if (color == darkmodeBlue) Color.LightGray else Color.Black
        )
    }
}

@Composable
fun TileText(boardSize: Int,
             tileNumber: Int,
             textColor: Color) {
    val textSize: Float =
        if (isPlatformAndroid)
            when (boardSize) {
                2 -> 12f
                4 -> 8f
                8 -> 4f
                12 -> 4f
                else -> 4f
            }
        else
            when (boardSize) {
                2 -> 6f
                4 -> 4f
                8 -> 2f
                12 -> 2f
                else -> 2f
            }

    Text(
        modifier = Modifier.wrapContentHeight(),
        text = tileNumber.toString(),
        fontSize = TextUnit(textSize, TextUnitType.Em),
        color = textColor,
        textAlign = TextAlign.Center
    )
}

fun tileSelected(
    tileDataState: MutableState<TileData>,
    cardBorderState: MutableState<BorderStroke?>,
) {
    if (GameStateHolder.isGameState(GameState.STARTING)) {
        GameStateHolder.openMenu()
        return
    }

    if (GameStateHolder.tutorial.isTutorial() && !GameStateHolder.tutorial.isAllowedTile(tileDataState.value)) {
        // todo make warning visible
        return
    }
    if (GameStateHolder.gameState.value == GameState.LOST) {
        // game is lost, no more tiles can be selected
        return
    }

    if (GameStateHolder.selected.value.first == null) {
        // select first Tile
        GameStateHolder.updateSelected(Pair(tileDataState.value, null))

        setBorder(tileDataState, cardBorderState, Color.Green)

        GameStateHolder.tutorial.nextStep()

    } else if (GameStateHolder.selected.value.first == tileDataState.value) {
        // first Tile is deselected by clicking again
        GameStateHolder.resetSelected()
        setBorder(tileDataState, cardBorderState, null)
    } else if (GameStateHolder.selected.value.first != null && GameStateHolder.selected.value.first!!.match(tileDataState.value)) {
        secondTileMatchesPlayCards(tileDataState)
    } else if (GameStateHolder.selected.value.first != null && !GameStateHolder.selected.value.first!!.match(tileDataState.value)) {
        secondTileDoesNotMatch(tileDataState, cardBorderState)
    }

    GameStateHolder.checkGameFinished()
}

private fun setBorder(
    tileDataState: MutableState<TileData>,
    cardBorderState: MutableState<BorderStroke?>,
    borderColor: Color?
) {
    if (borderColor != null) {
        if (UiStateHolder.darkModeState.value) {
            val darkModeBorderColor = when (borderColor) {
                Color.Green -> Color(0xAA00AA00)
                Color.Red -> Color(0xAAAA0000)
                else -> borderColor
            }
            tileDataState.value.borderStroke = BorderStroke(5.dp, darkModeBorderColor)
            cardBorderState.value = BorderStroke(5.dp, darkModeBorderColor)
        } else {
            tileDataState.value.borderStroke = BorderStroke(5.dp, borderColor)
            cardBorderState.value = BorderStroke(5.dp, borderColor)
        }
    } else {
        tileDataState.value.borderStroke = null
        cardBorderState.value = null
    }
}

private fun secondTileMatchesPlayCards(tileDataState: MutableState<TileData>) {
    // second Tile does match first Tile, both are played
    GameStateHolder.updateSelected(Pair(GameStateHolder.selected.value.first, tileDataState.value))

    val first = GameStateHolder.selected.value.first!!
    val second = GameStateHolder.selected.value.second!!
    first.played = true
    second.played = true
    GameStateHolder.updateSelected(Pair(first, second))
    GameStateHolder.playCard(first, second)
    GameStateHolder.addTimeToTimer() // only relevant for game with timer

    GameStateHolder.tutorial.nextStep()
}

private fun secondTileDoesNotMatch(
    tileDataState: MutableState<TileData>,
    cardBorderState: MutableState<BorderStroke?>
) {
    // second Tile does not match first Tile
    setBorder(tileDataState, cardBorderState, Color.Red)

    runOnMainAfter(2000L) { cardBorderState.value = null }

    GameStateHolder.tutorial.nextStep()
}

@Composable
fun polygonBox(color: Color, sides: Int, rotation: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .clip(Polygon(sides = sides, rotation = rotation))
        .background(color)
    ) {}
}



