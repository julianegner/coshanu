package util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import coshanu.composeapp.generated.resources.*
import com.hyperether.resources.stringResource

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
