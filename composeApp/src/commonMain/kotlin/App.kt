import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.*
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.title
import game.*
import game.enums.GameState
import game.enums.ScreenType
import org.jetbrains.compose.resources.stringResource
import ui.*
import ui.UiStateHolder.screenType
import ui.UiStateHolder.subtitleTextSize
import ui.UiStateHolder.titleTextSize

val gameSaveAndLoadOption = false

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

@Composable
fun App() {
    AppInitializer.initialize(isSystemInDarkTheme())

    MaterialTheme(colors = if (UiStateHolder.darkModeState.value) darkColors() else lightColors()) {
        Scaffold { // Scaffold is needed for the dark mode switch to work
            // we need this box to get the screen size
            BoxWithConstraints(Modifier.fillMaxSize(), propagateMinConstraints = true) {
                UiStateHolder.setScreenType(
                    if (this.maxWidth > this.maxHeight) {
                        ScreenType.LANDSCAPE
                    } else {
                        ScreenType.PORTRAIT
                    }
                )

                val verticalScrollModifier = mutableStateOf(
                    if (UiStateHolder.displayInfoArea.value || screenType.value == ScreenType.PORTRAIT) Modifier.verticalScroll(
                        rememberScrollState()
                    ) else Modifier
                )
                Main(verticalScrollModifier)
            }
        }
    }
}

@Composable
private fun Main(verticalScrollModifier: MutableState<Modifier>) {
    if (UiStateHolder.displayInfoArea.value) {
        InfoAreaWrapper(verticalScrollModifier.value)
    } else if (GameStateHolder.isGameState(GameState.STARTING)) {

        Column(
            modifier = Modifier.padding(top =
                if (UiStateHolder.screenType.value == ScreenType.LANDSCAPE) 48.dp else 65.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InfoAreaWrapper(verticalScrollModifier.value)
            Title()
            GameSymbol()
        }
    } else {
        Column {
            Column(
                modifier = verticalScrollModifier.value
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    //GameSymbol()
                    InfoSymbol(
                        Modifier
                            .clickable(onClick = { UiStateHolder.displayInfoArea.value = true })
                            .padding(10.dp)
                    )
                    DarkModeSwitch()
                }
                Title()
            }
            Column(
                modifier = verticalScrollModifier.value
                    .fillMaxSize(),
                // .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (GameStateHolder.gameState.value) {
                    GameState.RUNNING -> {
                        Board()
                    }

                    GameState.WON -> {
                        if (!GameStateHolder.tutorial.isTutorial()) {
                            Menu()
                        }
                        StartButtonRow()
                        GameStateTextElement()
                        TimerDisplay()
                        WonAnimation()
                        NextLevelButtonElement()
                        if (GameStateHolder.tutorial.isTutorial()) {
                            TutorialText()
                        }
                    }

                    GameState.LOST -> {
                        if (!GameStateHolder.tutorial.isTutorial()) {
                            Menu()
                        }
                        // if (GameStateHolder.tutorial.isRestartStep()) {
                        //     StartButtonRow()
                        //     TutorialText()
                        // }
                        // LostImage()
                        Board()
                    }

                    GameState.STARTING -> {
                        // nothing. handled above
                        // todo fixme bug: not shown in portrait mode
                        // GameSymbol()
                    }

                    GameState.LEVEL_CHANGE -> {
                        Menu()
                        StartButtonRow()
                    }

                    GameState.RESTART -> {
                        Menu()
                        Board()
                    }
                    GameState.LOAD_GAME -> {
                        LoadGameInputField()
                    }
                }
            }
        }
    }
}

@Composable
private fun Title() {
    Column(modifier = Modifier
        .clickable(interactionSource = null, indication = null) {
            GameStateHolder.openMenu()
        }
        .padding(vertical = 20.dp)) {
        Text(
            text = stringResource(Res.string.title),
            fontSize = titleTextSize.value
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(Res.string.subtitle),
            fontSize = subtitleTextSize.value
        )
    }
}
