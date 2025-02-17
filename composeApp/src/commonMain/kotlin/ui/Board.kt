package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import game.*
import game.GameStateHolder.gameMode
import game.GameStateHolder.level
import org.jetbrains.compose.resources.stringResource
import util.runOnMainAfter
import coshanu.composeapp.generated.resources.*
import game.GameStateHolder.gameState
import game.GameStateHolder.remainingTileAmount
import game.enums.GameMode
import game.enums.GameState
import game.enums.ScreenType

@Composable
fun Board() {

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        if (GameStateHolder.isGameState(GameState.LOST) || GameStateHolder.isGameState(GameState.RUNNING)) {
            Button(border = if (GameStateHolder.tutorial.isTutorial() && GameStateHolder.tutorial.isRestartAllowed()) {
                BorderStroke(
                    2.dp,
                    if (UiStateHolder.darkModeState.value) Color(0xCC00CC00) else Color.Green
                )
            } else {
                null
            },
                onClick = { restartGame() }) {
                Text(stringResource(Res.string.restart_game))
            }
        }
        if (GameStateHolder.isGameState(GameState.WON) || GameStateHolder.isGameState(GameState.LOST) || GameStateHolder.isGameState(
                GameState.RUNNING)) {
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

    if (UiStateHolder.screenType.value == ScreenType.PORTRAIT) {
        Column {  // desktop and portrait
            GridAndTutorial()
        }
    } else {
        Row { // mobile
            GridAndTutorial()
        }
    }
}

@Composable
fun GridAndTutorial() {
    LazyVerticalGrid(
        modifier = Modifier
            .aspectRatio(1f)
            .size(800.dp)
            .border(width = 1.dp, color =
                if (UiStateHolder.darkModeState.value) Color.LightGray else Color.Black),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.SpaceBetween,
        columns = GridCells.Fixed(GameStateHolder.boardDataState.value.size)
    ) {
        items(GameStateHolder.listState.value.size) { index ->
            TileCard(GameStateHolder.listState.value.get(index))
        }
    }
    Column(
        modifier = Modifier.padding( start = 20.dp, top = if (UiStateHolder.screenType.value == ScreenType.PORTRAIT) { 20.dp } else { 0.dp } )
    ) {
        GameModeSymbol(Modifier.padding(bottom = 20.dp))
        GameStateTextElement()
        Text(
            modifier = Modifier.padding(top = 10.dp).width(400.dp),
            text = GameStateHolder.tutorial.getCurrentTutorialText()
        )
    }
}

@Composable
fun GameStateTextElement() {
    Text(
        text = getGameStateText(),
        fontSize = TextUnit(
            if (GameStateHolder.isGameState(GameState.WON) || GameStateHolder.isGameState(GameState.LOST)) {
                2f
            } else {
                1.5f
            },
            TextUnitType.Em
        )
    )
}

@Composable
fun getGameStateText(): String = when (gameState.value) {
    GameState.RUNNING -> {
        if (remainingTileAmount.value == 0) {
            stringResource(Res.string.won) // before the state is set to Won
        } else {
            "${stringResource(Res.string.running)} ${stringResource(Res.string.remaining_tiles)} ${remainingTileAmount.value}"
        }
    }
    else -> stringResource(gameState.value.resourceId)
}
// todo move the following functions to a separate file (not UI)
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
