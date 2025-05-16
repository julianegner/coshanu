package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.cat
import coshanu.composeapp.generated.resources.dot_grid
import coshanu.composeapp.generated.resources.fire_pattern
import coshanu.composeapp.generated.resources.fish
import coshanu.composeapp.generated.resources.pattern_lines_crossed
import coshanu.composeapp.generated.resources.pattern_lines_up
import coshanu.composeapp.generated.resources.plant_pattern
import coshanu.composeapp.generated.resources.waves
import game.*
import game.GameStateHolder.lostGame
import game.GameStateHolder.remainingTileAmount
import game.enums.GameState
import game.enums.ShapeEnum
import isPlatformAndroid
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import util.modeDependantColor
import util.runOnMainAfter
import util.darkmodeBlue
import util.toName
import util.toPattern

@Composable
fun Tile(tileDataState: MutableState<TileData>,
         cardBorderState: MutableState<BorderStroke?>,
         boardSize: Int = GameStateHolder.boardDataState.value.size,
            modifier: Modifier = Modifier.clickable(onClick = { tileSelected(tileDataState, cardBorderState) }),
         displayText: Boolean = true,
         isNextTutorialTile: Boolean = false
         ) {


    val color = getColor(tileDataState, isNextTutorialTile)
    val additionalModifier: Modifier = getAdditionalModifier(tileDataState, isNextTutorialTile)

    when (tileDataState.value.shape) {
        ShapeEnum.CIRCLE -> polygonBox(color = color, sides = 1000, rotation = 0f,
            additionalModifier = additionalModifier,
            modifier = when (boardSize) {
                2 -> modifier.padding(20.dp)
                4 -> modifier.padding(15.dp)
                8 -> modifier.padding(5.dp)
                12 -> modifier.padding(2.dp)
                20 -> modifier // for the GameSymbol
                else -> modifier.padding(5.dp)
            })
        ShapeEnum.TRIANGLE ->   polygonBox(color = color, sides = 3, rotation = 30f,
            additionalModifier = additionalModifier,
            modifier = modifier.offset(y = if (boardSize < 8 || boardSize == 20) 15.dp else 5.dp)
        )
        ShapeEnum.SQUARE ->     polygonBox(color = color, sides = 4, rotation = 45f, additionalModifier = additionalModifier, modifier = modifier)
        ShapeEnum.PENTAGON ->   polygonBox(color = color, sides = 5, rotation = -18f, additionalModifier = additionalModifier, modifier = modifier)
        ShapeEnum.HEXAGON ->    polygonBox(color = color, sides = 6, rotation = 90f, additionalModifier = additionalModifier, modifier = modifier)
        ShapeEnum.OCTAGON ->    polygonBox(color = color, sides = 8, rotation = 22.5f, additionalModifier = additionalModifier, modifier = modifier)
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
        UiStateHolder.sound.play(SoundBytes.CLICK)
        GameStateHolder.tutorial.nextStep()
    } else if (GameStateHolder.selected.value.first == tileDataState.value) {
        // first Tile is deselected by clicking again
        GameStateHolder.resetSelected()
        setBorder(tileDataState, cardBorderState, null)
        UiStateHolder.sound.play(SoundBytes.CLICK)
    } else if (GameStateHolder.selected.value.first != null && GameStateHolder.selected.value.first!!.match(tileDataState.value)) {
        // play two matching cards
        secondTileMatchesPlayCards(tileDataState)
    } else if (GameStateHolder.selected.value.first != null && !GameStateHolder.selected.value.first!!.match(tileDataState.value)) {
        // play two non-matching cards
        secondTileDoesNotMatch(tileDataState, cardBorderState)
    }
}

private fun setBorder(
    tileDataState: MutableState<TileData>,
    cardBorderState: MutableState<BorderStroke?>,
    borderColor: Color?
) {
    if (borderColor != null) {
        tileDataState.value.borderStroke = BorderStroke(5.dp, borderColor.modeDependantColor)
        cardBorderState.value = BorderStroke(5.dp, borderColor.modeDependantColor)
    } else {
        tileDataState.value.borderStroke = null
        cardBorderState.value = null
    }
}

// second Tile does match first Tile, both are played
private fun secondTileMatchesPlayCards(tileDataState: MutableState<TileData>) {

    GameStateHolder.updateSelected(Pair(GameStateHolder.selected.value.first, tileDataState.value))

    val first = GameStateHolder.selected.value.first!!
    val second = GameStateHolder.selected.value.second!!
    first.played = true
    second.played = true
    GameStateHolder.updateSelected(Pair(first, second))
    GameStateHolder.playCard(first, second)
    GameStateHolder.addTimeToTimer() // only relevant for game with timer

    GameStateHolder.checkGameFinished()
    GameStateHolder.tutorial.nextStep()
}

// second Tile does not match first Tile
private fun secondTileDoesNotMatch(
    tileDataState: MutableState<TileData>,
    cardBorderState: MutableState<BorderStroke?>
) {
    UiStateHolder.sound.play(SoundBytes.DENY)
    setBorder(tileDataState, cardBorderState, Color.Red)
    runOnMainAfter(2000L) { cardBorderState.value = null }

    GameStateHolder.tutorial.nextStep()
}

@Composable
fun polygonBox(
    color: Color,
    sides: Int,
    rotation: Float,
    modifier: Modifier = Modifier,
    additionalModifier: Modifier = Modifier) {

    Box(modifier = modifier
        .clip(Polygon(sides = sides, rotation = rotation))
        .background(color)
        .then(additionalModifier)
    ) {}
}

@Composable
private fun getAdditionalModifier(
    tileDataState: MutableState<TileData>,
    isNextTutorialTile: Boolean
): Modifier =
    if (UiStateHolder.colorActive.value)
        Modifier
    else
        Modifier
            .paint(
                painter = painterResource(tileDataState.value.color.toPattern().drawableResource),
                colorFilter = ColorFilter.tint(
                    if (isNextTutorialTile)
                        Color.DarkGray.modeDependantColor
                    else
                        Color.LightGray.modeDependantColor
                )
            )

private fun getColor(
    tileDataState: MutableState<TileData>,
    isNextTutorialTile: Boolean
): Color =
    if (UiStateHolder.colorActive.value)
        tileDataState.value.color.modeDependantColor
    // if the next playable card in tutorial, inverse the color of the tile
    else if (isNextTutorialTile)
        Color.LightGray.modeDependantColor
    else
        Color.DarkGray.modeDependantColor
