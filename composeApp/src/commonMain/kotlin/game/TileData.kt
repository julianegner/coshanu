package game

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

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

data class TileData(
    val color: Color,
    val number: Int,
    val shape: ShapeEnum,
    var chosenForPlay: Boolean,
    var played: Boolean,
    var borderStroke: BorderStroke? = null
)

/* todo
localization german
das grüne Dreieck
den rote Kreis
das dunkelgraue Sechseck

-> bei Circle "der", sonst "das"
-> nach der Farbe immer noch ein "e", bei Circle "en"
 */
@Composable
fun TileData.tutorialString(): String {
    var colorPrefix = ""
    var colorPostfix = ""
    if (Locale.current.language == "de") {
        colorPrefix = if (this.shape == ShapeEnum.CIRCLE) "den " else "das "
        colorPostfix = if (this.shape == ShapeEnum.CIRCLE) "en" else "e"
    }

    return "${colorPrefix}${this.color.toName().lowercase()}${colorPostfix} " +
            "${stringResource(this.shape.resourceId)} " +
            "${stringResource(Res.string.with_number)} ${this.number}"
}

fun TileData.same(secondTileData: TileData): Boolean = ((this.color == secondTileData.color) && (this.shape == secondTileData.shape) && (this.number == secondTileData.number))

fun TileData.match(secondTileData: TileData): Boolean {
    when (GameStateHolder.gameMode.value) {
        GameMode.SINGLE_ELEMENT -> {
            return matchOne(secondTileData)
        }
        GameMode.TWO_ELEMENTS -> {
            return matchTwo(secondTileData)
        }
        else -> {
            // this should never happen
            throw RuntimeException("GameMode not valid")
        }
    }
}

fun TileData.getColor(): Color {
    return if (GameStateHolder.darkModeState.value) {
        when (this.color) {
            Color.Blue -> Color(0xAA0000FF)
            Color.Green -> Color(0xAA00AA00)
            Color.Red -> Color(0xAAAA0000)
            Color.Yellow -> Color(0xAAAAAA00)
            Color.LightGray -> Color.DarkGray
            Color.DarkGray -> Color.LightGray // Color(0xAA444444)
            Color.Magenta -> Color(0xAAAA00AA)
            Color.Cyan -> Color(0xAA00AAAA)
            else -> Color.Black
        }
    } else {
        this.color
    }
}

private fun TileData.matchOne(secondTileData: TileData): Boolean {
    return ((this.color == secondTileData.color) || (this.shape == secondTileData.shape) || (this.number == secondTileData.number))
}

private fun TileData.matchTwo(secondTileData: TileData): Boolean {
    var matchCount = 0
    if (this.color == secondTileData.color) matchCount++
    if (this.shape == secondTileData.shape) matchCount++
    if (this.number == secondTileData.number) matchCount++
    return (matchCount >= 2)
}
