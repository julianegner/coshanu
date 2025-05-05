package ui

import DARK_MODE
import SOUND_ACTIVE
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import game.enums.ScreenType
import isPlatformAndroid
import settings

object UiStateHolder {
    val darkModeState: MutableState<Boolean> = mutableStateOf(false)
    val screenType: MutableState<ScreenType> = mutableStateOf(ScreenType.LANDSCAPE)
    val standardTextSize: MutableState<TextUnit> = mutableStateOf(TextUnit(1f, TextUnitType.Em))
    val standardLineHeight: MutableState<TextUnit> = mutableStateOf(TextUnit(1.5f, TextUnitType.Em))
    val titleTextSize: MutableState<TextUnit> = mutableStateOf(TextUnit(2f, TextUnitType.Em))
    val subtitleTextSize: MutableState<TextUnit> = mutableStateOf(TextUnit(1.8f, TextUnitType.Em))
    val largerTextSize: MutableState<TextUnit> = mutableStateOf(TextUnit(1.5f, TextUnitType.Em))
    val menuRowTextWidth: MutableState<Dp> = mutableStateOf(100.dp)
    val menuButtonWidth: MutableState<Dp> = mutableStateOf(60.dp)
    val displayInfoArea: MutableState<Boolean> = mutableStateOf(false)
    val displayImpressum: MutableState<Boolean> = mutableStateOf(false)
    val displaySettingsArea: MutableState<Boolean> = mutableStateOf(false)
    val soundActive: MutableState<Boolean> = mutableStateOf(true)

    fun setScreenType(newScreenType: ScreenType) {
        screenType.value = newScreenType
        // todo use real device type instead of screen size
        //  https://www.npmjs.com/package/ua-parser-js for web
        standardTextSize.value = if (screenType.value == ScreenType.LANDSCAPE) TextUnit(1f, TextUnitType.Em) else TextUnit(2.4f, TextUnitType.Em)
        standardLineHeight.value = if (screenType.value == ScreenType.LANDSCAPE) TextUnit(1.5f, TextUnitType.Em) else TextUnit(1.5f, TextUnitType.Em)
        titleTextSize.value = if (isPlatformAndroid) TextUnit(5f, TextUnitType.Em) else
            if (screenType.value == ScreenType.LANDSCAPE) TextUnit(2f, TextUnitType.Em) else TextUnit(3.2f, TextUnitType.Em)
        subtitleTextSize.value = if (isPlatformAndroid) TextUnit(4f, TextUnitType.Em) else
            if (screenType.value == ScreenType.LANDSCAPE) TextUnit(1.8f, TextUnitType.Em) else TextUnit(2.6f, TextUnitType.Em)
        largerTextSize.value = if (screenType.value == ScreenType.LANDSCAPE) TextUnit(1.5f, TextUnitType.Em) else TextUnit(3f, TextUnitType.Em)
        menuRowTextWidth.value = if (isPlatformAndroid) 50.dp else if (screenType.value == ScreenType.LANDSCAPE) 100.dp else 200.dp
        menuButtonWidth.value = if (isPlatformAndroid) 50.dp else if (screenType.value == ScreenType.LANDSCAPE) 60.dp else 100.dp
    }

    fun setDarkModeState(value: Boolean) {
        darkModeState.value = value
        // Save to settings
        settings.putBoolean(DARK_MODE, value)
    }

    fun setSoundActive(value: Boolean) {
        soundActive.value = value
        // Save to settings
        settings.putBoolean(SOUND_ACTIVE, value)
    }

}
