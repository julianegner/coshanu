package ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
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
import game.GameStateHolder.tutorial
import game.enums.GameMode
import game.enums.GameState
import game.enums.ScreenType
import io.ktor.http.ContentDisposition.Companion.File
import org.jetbrains.compose.resources.painterResource
import ui.UiStateHolder.largerTextSize
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize
import ui.UiStateHolder.titleTextSize
import util.toClipboard

@Composable
fun Fingerpointing() = Image( // source: https://commons.wikimedia.org/wiki/File:Noto_Emoji_Oreo_1f449.svg
    painter = painterResource(Res.drawable.Noto_Emoji_Oreo_1f449),
    contentDescription = null,
    modifier = Modifier.size(50.dp)
)

@Composable
fun Board() {
    StartButtonRow()

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
fun StartButtonRow() {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        if (GameStateHolder.isGameState(GameState.LOST) ||
            (GameStateHolder.isGameState(GameState.RUNNING) &&
                    (!tutorial.isTutorial() || tutorial.isRestartStep()))) {
            if (tutorial.isRestartStep()) Fingerpointing()

            Button(
                border = if (tutorial.isRestartStep()) {
                    BorderStroke(
                        2.dp,
                        if (UiStateHolder.darkModeState.value) Color(0xCC00CC00) else Color.Green
                    )
                } else {
                    null
                },
                onClick = { restartGame() }) {
                Text(
                    fontSize = standardTextSize.value,
                    text = stringResource(Res.string.restart_game)
                )
            }
        }
        if (GameStateHolder.isGameState(GameState.WON) || GameStateHolder.isGameState(GameState.LOST) || GameStateHolder.isGameState(
                GameState.RUNNING
            )
        ) {
            Button(onClick = { endGame() }) {
                Text(
                    fontSize = standardTextSize.value,
                    text = stringResource(Res.string.end_game)
                )
            }
        }
        if (GameStateHolder.isGameState(GameState.LEVEL_CHANGE)) {
            Button(
                enabled = level.value != null,
                onClick = { newGame() }) {
                Text(
                    fontSize = standardTextSize.value,
                    text = stringResource(Res.string.start_game)
                )
            }
        }
        if (GameStateHolder.isGameState(GameState.RUNNING)) {
            Button(onClick = { saveGame() }) {
                Text(
                    fontSize = standardTextSize.value,
                    text = stringResource(Res.string.save_game)
                )
            }
        }
    }
}

@Composable
fun GridAndTutorial() {
    if (GameStateHolder.getRemainingTileAmount() > 0) {
        val basegridmodifier = Modifier
            .padding(top = 10.dp)
            .aspectRatio(1f)
            .size(800.dp)
            .border(
                width = 1.dp, color =
                    if (GameStateHolder.isGameState(GameState.LOST)) {
                        if (UiStateHolder.darkModeState.value) {
                            darkmodeRed
                        } else {
                            Color.Red
                        }
                        // } else if (GameStateHolder.isGameState(GameState.WON)) {
                        //     if (UiStateHolder.darkModeState.value) {
                        //         darkmodeGreen
                        //     } else {
                        //         Color.Green
                        //     }
                    } else
                        if (UiStateHolder.darkModeState.value)
                            Color.LightGray
                        else
                            Color.Black
            )


        val gridmodifier = if (GameStateHolder.isGameState(GameState.LOST)) {
            basegridmodifier.paint(painterResource(Res.drawable.lost), contentScale = ContentScale.FillBounds)
        } else {
            basegridmodifier
        }

        LazyVerticalGrid(
            modifier = gridmodifier,
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalArrangement = Arrangement.SpaceBetween,
            columns = GridCells.Fixed(GameStateHolder.boardDataState.value.size),

        ) {
            items(GameStateHolder.listState.value.size) { index ->
                TileCard(GameStateHolder.listState.value.get(index))
            }
        }
    } else if (GameStateHolder.isGameState(GameState.WON) || GameStateHolder.isGameState(GameState.RUNNING)) {
        WonAnimation()
    }
    if (!GameStateHolder.isGameState(GameState.LEVEL_CHANGE)) {
        Column(
            modifier = Modifier.padding(
                start = 20.dp,
                top = if (UiStateHolder.screenType.value == ScreenType.PORTRAIT) {
                    20.dp
                } else {
                    0.dp
                }
            )
        ) {
            Row(modifier =
                if (UiStateHolder.screenType.value == ScreenType.LANDSCAPE) {
                        Modifier.defaultMinSize(400.dp)
                    } else {
                        Modifier.fillMaxWidth()
                    },
                horizontalArrangement = Arrangement.SpaceBetween) {
                LevelText()
                TimerDisplay(Modifier.padding(horizontal = 20.dp))
            }

            GameModeSymbol(Modifier.padding(bottom = 20.dp))
            GameStateTextElement()
            TutorialText()
        }
    }
}

@Composable
fun TutorialText() {
    Text(
        fontSize = standardTextSize.value,
        lineHeight = standardLineHeight.value,
        modifier = Modifier.padding(top = 10.dp),
        text = tutorial.getCurrentTutorialText()
    )
}

@Composable
fun LevelText() {
    when (level.value) {
        0 -> {
            Text(
                text = stringResource(Res.string.single_element_tutorial),
                fontSize = largerTextSize.value
            )
        }
        10 -> {
            Text(
                text = stringResource(Res.string.two_elements_tutorial),
                fontSize = largerTextSize.value
            )
        }
        else -> {
            Text(
                text = stringResource(Res.string.level) + " ${level.value}",
                fontSize = largerTextSize.value
            )
        }
    }

}

@Composable
fun GameStateTextElement() {
    Text(
        text = getGameStateText(),
        fontSize = if (GameStateHolder.isGameState(GameState.WON) || GameStateHolder.isGameState(GameState.LOST)) {
                titleTextSize.value
            } else {
                largerTextSize.value
            }
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
    remainingTileAmount.value = tilesForGame.size

    GameStateHolder.updateBoard(board, GameState.RESTART)

    println("restartGame:level: ${level.value}")

    tutorial.nextStep()
    // runOnMainAfter(200L) {
        GameStateHolder.updateGameState(GameState.RUNNING)
    // }
    GameStateHolder.timer.startTimer()
}

fun endGame(
) {
    // level.value = null
    gameState.value = GameState.LEVEL_CHANGE
    GameStateHolder.resetBoard()
    GameStateHolder.gameStateText.value = ""
    tutorial.endTutorial()

    GameStateHolder.timer.stopTimer()
    tutorial.endTutorial()
}

fun newGame(
) {
    println("newGame:level: ${level.value}")

    if (level.value !== null) {
        when (level.value) {
            0, 1, 2, 3 -> gameMode.value = GameMode.SINGLE_ELEMENT
            10, 11, 12, 13 -> gameMode.value = GameMode.TWO_ELEMENTS
            else -> gameMode.value = GameMode.SINGLE_ELEMENT
        }
        GameStateHolder.resetBoard()
        LevelGenerator().generateLevel(level.value!!)
        GameStateHolder.updateGameState(GameState.RUNNING)

        GameStateHolder.timer.startTimer()
    }
}

fun saveGame() {
    // todo implement save game
    // todo save this to clipboard or file

    val saveGameString = """saveGame
        level: ${level.value}
        rem. tiles: ${remainingTileAmount.value}
        timer: ${GameStateHolder.timer.durationState().value}
        board size: ${GameStateHolder.boardDataState.value.size}
        board: ${GameStateHolder.boardDataState.value.tiles.map { it.toSaveString() }}
        )
        """

    println(saveGameString)
    saveGameString.toClipboard()
}
