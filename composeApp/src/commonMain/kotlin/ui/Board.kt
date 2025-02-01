package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import game.*
import game.GameStateHolder.gameMode
import game.GameStateHolder.level
import util.runOnMainAfter

@Composable
fun Board() {

    /* todo
        add my width of field for tutorial text, so that the game board does not move around
        mark restart game button in tutorial
        dark mode?
        language support?
        .
        generate APK for Android
        generate for JVM
        get domain
        deploy to web
        deploy to Android (game store)
        deploy to iOS (app store)
        deploy to apt
        deploy to snap
        deploy to flatpak
        deploy to mac store
        deploy to windows store
        deploy to steam
        deploy to nintendo
        deploy to xbox
        deploy to playstation
        add GNU license
        make repository public
     */

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        if (GameStateHolder.isGameState(GameState.LOST) || GameStateHolder.isGameState(GameState.RUNNING)) {
            Button(onClick = { restartGame() }) {
                Text("Restart Game")
            }
        }
        if (GameStateHolder.isGameState(GameState.WON) || GameStateHolder.isGameState(GameState.LOST) || GameStateHolder.isGameState(GameState.RUNNING)) {
            Button(onClick = { endGame() }) {
                Text("End Game")
            }
        }
        if (GameStateHolder.isGameState(GameState.LEVEL_CHANGE)) {
            Button(onClick = { newGame() }) {
                Text("Start Game")
            }
        }
    }

    Row() {
        LazyVerticalGrid(
            modifier = Modifier.size(800.dp).border(width = 1.dp, color = Color.Black),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalArrangement = Arrangement.SpaceBetween,
            columns = GridCells.Fixed(GameStateHolder.boardDataState.value.size)
        ) {
            items(GameStateHolder.listState.value.size) { index ->
                card(GameStateHolder.listState.value.get(index))
            }
        }
        Column {
            GameModeSymbol(Modifier.padding(horizontal = 20.dp).padding(bottom = 20.dp))
            Text(
                modifier = Modifier.padding(horizontal = 20.dp).width(400.dp), // todo depends on used device. we may have to shange the apearance of the ttorial text for mobile use
                text = GameStateHolder.tutorial.getCurrentTutorialText()
            )
        }
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
            backgroundColor = if (GameStateHolder.tutorial.isTutorial() && GameStateHolder.tutorial.isAllowedTile(tileDataState.value)) {
                Color.DarkGray
            } else {
                Color.LightGray
            },
            border = cardBorderState.value

        ) {
            Tile(tileDataState, cardBorderState)
        }
    } else {
        Card(
            modifier = cardModifier,
            elevation = 0.dp) {}
    }
}
