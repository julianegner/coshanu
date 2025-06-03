import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.subtitle
import coshanu.composeapp.generated.resources.title
import game.*
import game.enums.GameMode
import game.enums.GameState
import game.enums.ScreenType
import ui.*
import ui.UiStateHolder.screenType
import ui.UiStateHolder.subtitleTextSize
import ui.UiStateHolder.titleTextSize

import com.hyperether.resources.AppLocale
import com.hyperether.resources.stringResource
import com.hyperether.resources.currentLanguage
import com.russhwolf.settings.Settings
import util.clickableHoverIcon
import util.onClick

val programVersion = "1.2.0"
val gameSaveAndLoadOption = false

val settings: Settings = Settings()
val DARK_MODE = "dark_mode"
val SOUND_ACTIVE = "sound_active"
val CUSTOM_LOCALE = "custom_locale"

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

            setAppLocale(Locale.current)

            setSettingsFromSavedSettings()
        }
    }

    private fun setSettingsFromSavedSettings() {
        // Only override if set in settings
        if (settings.hasKey(DARK_MODE)) {
            UiStateHolder.darkModeState.value = settings.getBoolean(DARK_MODE, false)
            println("set dark mode from settings: ${UiStateHolder.darkModeState.value}")
        }

        if (settings.hasKey(CUSTOM_LOCALE)) {
            val localeFromSettings = settings.getString(CUSTOM_LOCALE, "en")
            setAppLocale(Locale(localeFromSettings))
            println("set locale from settings: $localeFromSettings")
        }

        UiStateHolder.soundActive.value = settings.getBoolean(SOUND_ACTIVE, true)
    }

    private fun setAppLocale(locale: Locale) {
        currentLanguage.value = AppLocale.findByCode(locale.language)
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
                    if (
                        UiStateHolder.displayInfoArea.value ||
                        UiStateHolder.displayStickerArea.value ||
                        isPlatformAndroid ||
                        screenType.value == ScreenType.PORTRAIT
                    )
                        Modifier.verticalScroll( rememberScrollState())
                    else
                        Modifier
                )
                Main(verticalScrollModifier)
            }
        }
    }
}

@Composable
private fun Main(verticalScrollModifier: MutableState<Modifier>) {
    val infoSymbolModifier = Modifier
        .onClick(onClick = { UiStateHolder.displayInfoArea.value = true })
        .padding(10.dp)

    // todo add navigation to access this without changing the code
    // UiStateHolder.displayStickerArea.value = true // set to true to show the sticker image
    if (UiStateHolder.displayStickerArea.value) {
        StickerImage(verticalScrollModifier)
    } else if (UiStateHolder.displayInfoArea.value) {
        Row (
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = verticalScrollModifier.value
                .fillMaxSize()
                .border(1.dp, Color.Gray)
        )
        {
            InfoArea()
        }
    } else if (GameStateHolder.isGameState(GameState.STARTING)) {
        Column(
            modifier = Modifier.padding(top =
                if (isLandscape) 48.dp else 65.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isPlatformWasm) {
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    InfoSymbol(infoSymbolModifier)
                    Title()
                    if (isLandscape ||
                        !UiStateHolder.displaySettingsArea.value) {
                        SettingsAreaWrapper()
                    }
                }
                if (UiStateHolder.displaySettingsArea.value && isPortrait) {
                    SettingsAreaWrapper()
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    SettingsAreaWrapper()
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Title()
                }
            }
            GameSymbol()
        }
        ImpressumWrapper(if (isLandscape) Modifier.fillMaxWidth(0.5f) else Modifier.fillMaxWidth())
    } else {
        Column {
            Column(
                modifier = verticalScrollModifier.value
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = if (isPlatformWasm) Arrangement.SpaceBetween else Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp)
                ) {
                    //GameSymbol()

                    if (isPlatformWasm) {
                        InfoSymbol(infoSymbolModifier)
                        ImpressumWrapper(Modifier.fillMaxWidth(0.5f))
                    }
                    SettingsAreaWrapper()
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
                        Row(modifier = Modifier.padding(top = 10.dp)) {
                            TimerDisplay()
                            if (GameStateHolder.gameMode.value == GameMode.TWO_ELEMENTS_WITH_TIMER) {
                                CountdownTimerDisplay(modifier = Modifier.padding(start = 10.dp))
                            }
                        }
                        WonAnimation()
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
        .clickableHoverIcon()
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
