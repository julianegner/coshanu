package ui

import ScreenType
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import game.*
import game.GameStateHolder.gameMode
import game.GameStateHolder.level
import org.jetbrains.compose.resources.stringResource
import util.runOnMainAfter
import coshanu.composeapp.generated.resources.*

@Composable
fun Board(screenType: ScreenType) {

    /* todo
        on mobile, put the game status text below the game board
        .
        bug: red border comes back on when playing other tiles
        .
        remove not needed printlines
        .
        .
        add language chooser ?
        .
        .
        .
        generate APK for Android
        generate for JVM
        get domain
        deploy to web
        add robots.txt for web, so that the search engines can index the site
        deploy to Android (game store)
        deploy to iOS (app store)
        deploy to apt
        deploy to snap
        deploy to flatpak
        deploy to mac store
        deploy to windows store
        deploy to steam
        deploy to nintendo ??
        deploy to xbox
        deploy to playstation
        add GNU license
        make repository public
     */

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        if (GameStateHolder.isGameState(GameState.LOST) || GameStateHolder.isGameState(GameState.RUNNING)) {
            Button(border = if (GameStateHolder.tutorial.isTutorial() && GameStateHolder.tutorial.isRestartAllowed()) {
                BorderStroke(
                    2.dp,
                    if (GameStateHolder.darkModeState.value) Color(0xCC00CC00) else Color.Green
                )
            } else {
                null
            },
                onClick = { restartGame() }) {
                Text(stringResource(Res.string.restart_game))
            }
        }
        if (GameStateHolder.isGameState(GameState.WON) || GameStateHolder.isGameState(GameState.LOST) || GameStateHolder.isGameState(GameState.RUNNING)) {
            Button(onClick = { endGame() }) {
                Text(stringResource(Res.string.end_game))
            }
        }
        if (GameStateHolder.isGameState(GameState.LEVEL_CHANGE)) {
            Button(onClick = { newGame() }) {
                Text(stringResource(Res.string.start_game))
            }
        }
    }

    if (screenType == ScreenType.PORTRAIT) {
        Column {  // desktop and portrait
            GridAndTutorial(screenType)
        }
    } else {
        Row { // mobile
            GridAndTutorial(screenType)
        }
    }
}

@Composable
fun GridAndTutorial(screenType: ScreenType) {
    LazyVerticalGrid(
        modifier = Modifier
            .aspectRatio(1f)
            .size(800.dp)
            .border(width = 1.dp, color =
                if (GameStateHolder.darkModeState.value) Color.LightGray else Color.Black),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.SpaceBetween,
        columns = GridCells.Fixed(GameStateHolder.boardDataState.value.size)
    ) {
        items(GameStateHolder.listState.value.size) { index ->
            card(GameStateHolder.listState.value.get(index))
        }
    }
    Column(
        modifier = Modifier.padding( top = if (screenType == ScreenType.PORTRAIT) { 20.dp } else { 0.dp } )
    ) {
        GameModeSymbol(Modifier.padding(horizontal = 20.dp).padding(bottom = 20.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp).width(400.dp), // todo depends on used device. we may have to shange the apearance of the ttorial text for mobile use
            text = GameStateHolder.tutorial.getCurrentTutorialText()
        )
    }
}

fun restartGame(
) {
    GameStateHolder.updateGameState(GameState.RESTART)

    val tilesForGame = GameStateHolder.listState.value
        .filter { tileData -> tileData.chosenForPlay }

    tilesForGame.forEach {
        it.played = false
        it.borderStroke = null
    }

    val board = GameStateHolder.boardDataState.value

    board.tiles = tilesForGame
    GameStateHolder.listState.value = tilesForGame
    GameStateHolder.remainingTileAmount.value = tilesForGame.size

    GameStateHolder.updateBoard(board, GameState.RESTART)

    println("restartGame:level: ${GameStateHolder.level.value}")

    GameStateHolder.tutorial.nextStep()
    runOnMainAfter(200L) {
        GameStateHolder.updateGameState(GameState.RUNNING)
    }
}

fun endGame(
) {
    level.value = null
    GameStateHolder.gameState.value = GameState.LEVEL_CHANGE
    GameStateHolder.resetBoard()
    GameStateHolder.gameStateText.value = ""
    GameStateHolder.tutorial.endTutorial()
}

fun newGame(
) {
    println("newGame:level: ${GameStateHolder.level.value}")

    if (GameStateHolder.level.value !== null) {
        when (GameStateHolder.level.value) {
            0,1,2,3 -> gameMode.value = GameMode.SINGLE_ELEMENT
            10,11,12,13 -> gameMode.value = GameMode.TWO_ELEMENTS
            else -> gameMode.value = GameMode.SINGLE_ELEMENT
        }
        GameStateHolder.resetBoard()
        LevelGenerator().generateLevel(GameStateHolder.level.value!!)
        GameStateHolder.updateGameState(GameState.RUNNING)
    }
}

@Composable
private fun card(
    tileData: TileData) {
    val tileDataState: MutableState<TileData> = remember { mutableStateOf(tileData) }

    val played = remember { mutableStateOf(tileDataState.value.played) }

    if (GameStateHolder.selected.value.first != null && GameStateHolder.selected.value.second != null) {
        if (GameStateHolder.selected.value.first!!.same(tileDataState.value) || GameStateHolder.selected.value.second!!.same(tileDataState.value)) {
            played.value = true

            runOnMainAfter(200L) {
                GameStateHolder.resetSelected()
            }
        }
    }

    if (GameStateHolder.gameState.value == GameState.RESTART) {
        played.value = false
    }

    val cardModifier = Modifier
        .aspectRatio(1f)
        .padding(10.dp)

    if (!played.value) {
        val cardBorderState = remember { mutableStateOf(tileDataState.value.borderStroke) }

        Card(
            modifier = cardModifier
                .clickable(onClick = { tileSelected(tileDataState, cardBorderState) }),
            backgroundColor = if (
                    GameStateHolder.tutorial.isTutorial() &&
                    GameStateHolder.tutorial.isAllowedTile(tileDataState.value)
                ) {
                if (GameStateHolder.darkModeState.value) { Color.LightGray } else { Color.DarkGray }
            } else {
                if (GameStateHolder.darkModeState.value) { Color.DarkGray } else { Color.LightGray }
            },
            border = cardBorderState.value,
        ) {
            Tile(tileDataState, cardBorderState)
        }
    } else {
        Card(
            modifier = cardModifier,
            elevation = 0.dp) {}
    }
}
