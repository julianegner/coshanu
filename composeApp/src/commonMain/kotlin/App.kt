import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.*
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.title
import game.*
import game.enums.GameState
import game.enums.ScreenType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.*

object AppInitializer {
    var called = false

    fun initialize(darkTheme: Boolean) {
        if (!called) {
            // Code to run only once at the start of the program
            called = true
            println("AppInitializer init block. darkTheme: $darkTheme")
            if (darkTheme) {
                UiStateHolder.darkModeState.value = true
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    AppInitializer.initialize(isSystemInDarkTheme())

    MaterialTheme(colors = if (UiStateHolder.darkModeState.value) darkColors() else lightColors()) {
        Scaffold { // Scaffold is needed for the dark mode switch to work
            // we need this box to get the screen size
            BoxWithConstraints(Modifier.fillMaxSize(), propagateMinConstraints = true) {
                UiStateHolder.setScreenType(if (this.maxWidth > this.maxHeight) { ScreenType.LANDSCAPE } else { ScreenType.PORTRAIT })

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
                            Board()
                        }
                    }
                }
            }
        }
    }
}
