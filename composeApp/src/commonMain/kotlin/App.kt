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

enum class ScreenType {
    PORTRAIT,
    LANDSCAPE
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    AppInitializer.initialize(isSystemInDarkTheme())

    MaterialTheme(colors = if (GameStateHolder.darkModeState.value) darkColors() else lightColors()) {
        Scaffold { // Scaffold is needed for the dark mode switch to work
            // we need this box to get the screen size
            BoxWithConstraints(Modifier.fillMaxSize(), propagateMinConstraints = true) {
                val maxHeight = this.maxHeight
                val maxWidth = this.maxWidth

                val screenType = if (maxWidth > maxHeight) { ScreenType.LANDSCAPE } else { ScreenType.PORTRAIT }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                ) {
                    GameSymbol()
                    DarkModeSwitch()

                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Column(modifier = Modifier.padding(vertical = 20.dp)) {
                            Text(
                                text = stringResource(Res.string.title),
                                fontSize = TextUnit(2f, TextUnitType.Em)
                            )
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = stringResource(Res.string.subtitle),
                                fontSize = TextUnit(1.8f, TextUnitType.Em)
                            )
                        }

                        if (!(GameStateHolder.isGameState(GameState.RUNNING) || GameStateHolder.isGameState(GameState.LOST))) {
                            Menu()
                        }

                        // Text(getPlatform().name, modifier = Modifier.padding(vertical = 5.dp))

                        // if level is chosen, display the board
                        if (GameStateHolder.level.value != null) {
                            Board(screenType)
                        }
                    }
                }
            }
        }
    }
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
