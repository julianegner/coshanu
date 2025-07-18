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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import game.*
import game.GameStateHolder.level
import com.hyperether.resources.stringResource
import coshanu.composeapp.generated.resources.*
import game.GameStateHolder.gameState
import game.GameStateHolder.remainingTileAmount
import game.GameStateHolder.timer
import game.GameStateHolder.tutorial
import game.enums.GameMode
import game.enums.GameState
import gameSaveAndLoadOption
import isPlatformAndroid
import isPortrait
import landscapeOrAndroid
import org.jetbrains.compose.resources.painterResource
import ui.UiStateHolder.largerTextSize
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize
import ui.UiStateHolder.titleTextSize
import util.clickableHoverIcon
import util.modeDependantColor
import kotlin.time.Duration.Companion.seconds

@Composable
fun Fingerpointing() =
    //android does not support SVGs
    if (isPlatformAndroid) {
        Image( // source: https://commons.wikimedia.org/wiki/File:Noto_Emoji_Oreo_1f449.svg
            painter = painterResource(Res.drawable.Noto_Emoji_Fingerpointing),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    } else {
        Image( // source: https://commons.wikimedia.org/wiki/File:Noto_Emoji_Oreo_1f449.svg
        painter = painterResource(Res.drawable.Noto_Emoji_Oreo_1f449),
        contentDescription = null,
        modifier = Modifier.size(50.dp)
        )
    }

@Composable
fun Board() {
    StartButtonRow()

    if (isPortrait) {
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
                        Color.Green.modeDependantColor
                    )
                } else {
                    null
                },
                modifier = Modifier.clickableHoverIcon(),
                onClick = { restartGame() }) {
                Text(
                    fontSize = standardTextSize.value,
                    text = stringResource(Res.string.restart_game)
                )
            }
        }
        if (GameStateHolder.isGameState(GameState.WON) || GameStateHolder.isGameState(GameState.LOST) || GameStateHolder.isGameState(GameState.RUNNING)) {
            Button(
                    onClick = { endGame() },
                    modifier = Modifier.clickableHoverIcon()
                ) {
                Text(
                    fontSize = standardTextSize.value,
                    text = stringResource(Res.string.end_game)
                )
            }
        }
        if (GameStateHolder.isGameState(GameState.WON)) {
            NextLevelButtonElement()
        }
        if (GameStateHolder.isGameState(GameState.LEVEL_CHANGE)) {
            Button(
                enabled = level.value != null,
                modifier = Modifier.clickableHoverIcon(level.value != null),
                onClick = { newGame() }) {
                Text(
                    fontSize = standardTextSize.value,
                    text = stringResource(Res.string.start_game)
                )
            }
            if (gameSaveAndLoadOption) {
                Button(
                    modifier = Modifier.clickableHoverIcon(),
                    onClick = { loadGame() }) {
                    Text(
                        fontSize = standardTextSize.value,
                        text = stringResource(Res.string.load_game)
                    )
                }
            }
        }
        if (gameSaveAndLoadOption && GameStateHolder.isGameState(GameState.RUNNING)) {
            Button(
                onClick = { saveGame() },
                 modifier = Modifier.clickableHoverIcon(),
            ) {
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
                        Color.Red.modeDependantColor
                        // } else if (GameStateHolder.isGameState(GameState.WON)) {
                        // Color.Green.modeDependantColor
                        //     }
                    } else
                        if (isPlatformAndroid)
                            Color.Transparent
                        else
                            Color.Black.modeDependantColor
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
                top = if (isPortrait) {
                    20.dp
                } else {
                    0.dp
                }
            )
        ) {
            Row(modifier =
                if (landscapeOrAndroid) {
                        Modifier.defaultMinSize(400.dp)
                    } else {
                        Modifier.fillMaxWidth()
                    },
                horizontalArrangement = Arrangement.SpaceBetween) {
                LevelText()
                TimerDisplay(Modifier.padding(horizontal = 20.dp))
                if (GameStateHolder.gameMode.value == GameMode.TWO_ELEMENTS_WITH_TIMER) {
                    CountdownTimerDisplay(Modifier.padding(horizontal = 20.dp))
                }
            }

            GameModeSymbol(Modifier.padding(bottom = 20.dp))
            GameStateTextElement()

            if (GameStateHolder.gameMode.value == GameMode.TWO_ELEMENTS_WITH_TIMER &&
                GameStateHolder.isGameState(GameState.LOST) &&
                GameStateHolder.totalAllowedTime.value - timer.durationState().value == 0.seconds
                ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = stringResource(Res.string.time_is_up),
                    fontSize = largerTextSize.value,
                    color = Color.Red
                )
            }
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
