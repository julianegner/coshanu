package game

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.*
import game.enums.GameMode
import game.enums.ShapeEnum
import org.jetbrains.compose.resources.stringResource
import ui.UiStateHolder
import util.toName
import util.toSaveName

data class TileData(
    val color: Color,
    val number: Int,
    val shape: ShapeEnum,
    var chosenForPlay: Boolean,
    var played: Boolean,
    var borderStroke: BorderStroke? = null
)

fun newTileData(color: Color, shape: ShapeEnum, number: Int): TileData =
    TileData(
        color = color,
        shape = shape,
        number = number,
        chosenForPlay = true,
        played = false
    )


@Composable
fun TileData.tutorialString(): String {

    var colorPrefix = if (this.shape == ShapeEnum.CIRCLE) stringResource(Res.string.color_prefix_circle) else stringResource(Res.string.color_prefix)
    var colorPostfix = if (this.shape == ShapeEnum.CIRCLE) stringResource(Res.string.color_postfix_circle) else stringResource(Res.string.color_postfix)

    return "${colorPrefix} ${this.color.toName().lowercase()}${colorPostfix} " +
            "${stringResource(this.shape.resourceId)} " +
            "${stringResource(Res.string.with_number)} ${this.number}"
}

fun TileData.same(secondTileData: TileData): Boolean = ((this.color == secondTileData.color) && (this.shape == secondTileData.shape) && (this.number == secondTileData.number))

fun TileData.match(secondTileData: TileData): Boolean {
    when (GameStateHolder.gameMode.value) {
        GameMode.SINGLE_ELEMENT -> {
            return matchOne(secondTileData)
        }
        GameMode.TWO_ELEMENTS, GameMode.TWO_ELEMENTS_WITH_TIMER -> {
            return matchTwo(secondTileData)
        }
        else -> {
            // this should never happen
            throw RuntimeException("GameMode not valid")
        }
    }
}

val darkmodeBlue = Color(0xAA0000FF)
val darkmodeRed = Color(0xAAAA0000)
val darkmodeGreen = Color(0xAA00AA00)
val darkmodeYellow = Color(0xAAAAAA00)

fun TileData.getColor(): Color {
    return if (UiStateHolder.darkModeState.value) {

        // todo create a color object where you can give a standard color and get the appropriate color back
        when (this.color) {
            Color.Blue -> darkmodeBlue
            Color.Green -> darkmodeGreen
            Color.Red -> darkmodeRed
            Color.Yellow -> darkmodeYellow
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

fun TileData.toSaveString(): String {
    return this.color.toSaveName() +
            " ${this.shape.name}" +
            " ${this.number}" +
            // " ${if (this.chosenForPlay) "chosen" else ""}" +
            " ${if (this.played) "played" else ""}"
}

fun TileData.toReadableString(withPlayed: Boolean = false): String {
    return "${this.color.toSaveName()} ${this.shape.name} ${this.number} ${if (withPlayed) this.played else ""}"
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
