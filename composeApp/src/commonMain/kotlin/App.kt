import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.*
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.title
import game.*
import game.GameStateHolder.gameState
import game.GameStateHolder.remainingTileAmount
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.Board
import ui.DarkModeSwitch
import ui.GameSymbol

object AppInitializer {
    var called = false

    fun initialize(darkTheme: Boolean) {
        if (!called) {
            // Code to run only once at the start of the program
            called = true
            println("AppInitializer init block. darkTheme: $darkTheme")
            if (darkTheme) {
                GameStateHolder.darkModeState.value = true
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    AppInitializer.initialize(isSystemInDarkTheme())

    MaterialTheme(colors = if (GameStateHolder.darkModeState.value) darkColors() else lightColors()) {
        Scaffold { // Scaffold is needed for the dark mode switch to work
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                GameSymbol()
                DarkModeSwitch()

                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.padding(vertical = 20.dp),
                        text = stringResource(Res.string.title),
                        fontSize = TextUnit(2f, TextUnitType.Em)
                    )

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

                    if (!(GameStateHolder.isGameState(GameState.RUNNING) || GameStateHolder.isGameState(GameState.LOST))) {
                        Menu()
                    }

                    // Text(getPlatform().name, modifier = Modifier.padding(vertical = 5.dp))

                    // if level is chosen, display the board
                    if (GameStateHolder.level.value != null) {
                        Board()
                    }
                }
            }
        }
    }
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

@Composable
fun Menu() {

    Text(stringResource(Res.string.choose_level))

    Row {
        Text(stringResource(Res.string.single_element), modifier = Modifier.padding(5.dp).width(100.dp))
        (0..3).forEach { i ->
            Button(
                onClick = {
                    GameStateHolder.changeLevel(i)
                }) { Text(if (i == 0) stringResource(Res.string.tutorial) else i.toString()) }
        }
    }

    val LightBlue = Color(0xCC3333FF)

    Row {
        Text(stringResource(Res.string.two_elements), modifier = Modifier.padding(5.dp).width(100.dp))
        (10..13).forEach { i ->
            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = if (GameStateHolder.darkModeState.value) Color.Black else Color.White,
                    backgroundColor = if (GameStateHolder.darkModeState.value) LightBlue else Color.Blue
                ),
                onClick = {
                    GameStateHolder.changeLevel(i)
                }) { Text(if (i == 10) stringResource(Res.string.tutorial) else i.toString()) }
        }
    }
}
