package util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import coshanu.composeapp.generated.resources.*
import com.hyperether.resources.stringResource
import game.enums.Pattern
import ui.UiStateHolder

val darkmodeBlue = Color(0xAA0000FF)
val darkmodeRed = Color(0xAAAA0000)
val darkmodeGreen = Color(0xAA00AA00)
val darkmodeYellow = Color(0xAAAAAA00)
val darkmodeLinkBlue = Color(0xAA00FFFF)
val lightBlue = Color(0xCC3333FF)
val lightLightBlue = Color(0xEE5533FF)

val Color.modeDependantColor: Color
    get() =
        if (UiStateHolder.darkModeState.value) {
            when (this) {
                Color.Blue -> darkmodeBlue
                Color.Green -> darkmodeGreen
                Color.Red -> darkmodeRed
                Color.Yellow -> darkmodeYellow
                Color.LightGray -> Color.DarkGray
                Color.DarkGray -> Color.LightGray
                Color.Magenta -> Color(0xAAAA00AA)
                Color.Cyan -> Color(0xAA00AAAA)
                Color.Black -> Color.LightGray
                else -> Color.Black
            }
        } else {
            this
        }

@Composable
fun Color.toName(): String =
    when (this) {
        Color.Blue -> stringResource(Res.string.color_blue)
        Color.Green -> stringResource(Res.string.color_green)
        Color.Red -> stringResource(Res.string.color_red)
        Color.Yellow -> stringResource(Res.string.color_yellow)
        Color.LightGray -> stringResource(Res.string.color_light_gray)
        Color.DarkGray -> stringResource(Res.string.color_dark_gray)
        Color.Magenta -> stringResource(Res.string.color_magenta)
        Color.Cyan -> stringResource(Res.string.color_cyan)
        else -> stringResource(Res.string.color_unknown)
    }

fun Color.toSaveName(): String =
    when (this) {
        Color.Blue -> "blue"
        Color.Green -> "green"
        Color.Red -> "red"
        Color.Yellow -> "yellow"
        Color.LightGray -> "light_gray"
        Color.DarkGray -> "dark_gray"
        Color.Magenta -> "magenta"
        Color.Cyan -> "cyan"
        else -> "unknown"
    }

fun stringToColor(colorString: String): Color =
    when (colorString) {
        "blue" -> Color.Blue
        "green" -> Color.Green
        "red" -> Color.Red
        "yellow" -> Color.Yellow
        "light_gray" -> Color.LightGray
        "dark_gray" -> Color.DarkGray
        "magenta" -> Color.Magenta
        "cyan" -> Color.Cyan
        else -> Color.Unspecified
    }

fun Color.toPattern(): Pattern =
    when (this) {
        Color.Blue -> Pattern.Waves
        Color.Green -> Pattern.Plant
        Color.Red -> Pattern.Fire
        Color.Yellow -> Pattern.DotGrid
        Color.DarkGray -> Pattern.LinesUp
        Color.Magenta -> Pattern.LinesCrossed
        Color.Cyan -> Pattern.Fish
        else -> Pattern.Cat // should never happen
    }
