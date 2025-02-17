package ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import game.enums.ScreenType

object UiStateHolder {
    val darkModeState: MutableState<Boolean> = mutableStateOf(false)
    val screenType: MutableState<ScreenType> = mutableStateOf(ScreenType.LANDSCAPE)
    val standardTextStyle: MutableState<TextStyle> = mutableStateOf(TextStyle(fontSize = TextUnit(1f, TextUnitType.Em)))

    fun setScreenType(newScreenType: ScreenType) {
        screenType.value = newScreenType
        standardTextStyle.value = if (screenType.value == ScreenType.LANDSCAPE) TextStyle(fontSize = TextUnit(1f, TextUnitType.Em)) else TextStyle(fontSize = TextUnit(3f, TextUnitType.Em))
    }

}
