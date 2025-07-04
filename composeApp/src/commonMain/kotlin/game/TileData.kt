package game

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.*
import game.enums.GameMode
import game.enums.ShapeEnum
import com.hyperether.resources.stringResource
import ui.UiStateHolder
import util.modeDependantColor
import util.toName
import util.toPattern
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

    if (!UiStateHolder.colorActive.value) {
        return colorlessTutorialString()
    }

    val colorPrefix = if (this.shape == ShapeEnum.CIRCLE) stringResource(Res.string.color_prefix_circle) else stringResource(Res.string.color_prefix)
    val colorPostfix = if (this.shape == ShapeEnum.CIRCLE) stringResource(Res.string.color_postfix_circle) else stringResource(Res.string.color_postfix)

    return "${colorPrefix} ${this.color.toName().lowercase()}${colorPostfix} " +
            "${stringResource(this.shape.resourceId)} " +
            "${stringResource(Res.string.with)} ${stringResource(Res.string.the_number)} ${this.number}"
}

@Composable
private fun TileData.colorlessTutorialString(): String {
    /*

Text in README anpassen und aus neue Funktion hinweisen
remove: Text feld in Tile.kt

evtl tooltip, der anzeigt, welche Karte da ist (z.B. "Dreieck mit Pflanze und der Nummer 4") oder Dreieck, Pflanze, 4

evtl. extra klasse für colorless, wo die entsprechenden code teile zusammengefasst werden

     */

    val patternPrefix = if (this.shape == ShapeEnum.CIRCLE) stringResource(Res.string.color_prefix_circle) else stringResource(Res.string.color_prefix)
    val patternString = "${stringResource(this.color.toPattern().withStringResourceId)} "


    /*
todo


Tutorial
.. und nur noch blaue Karten bleiben

tutorial 2 Karten
..spiel das erste grüne Paar
das zweite grüne Paar

spiel eins der blauen Paare
*/

    return  "${patternPrefix} ${stringResource(this.shape.resourceId)} " +
            "${stringResource(Res.string.with)} ${patternString}" +
            "${stringResource(Res.string.and)} ${stringResource(Res.string.the_number)} ${this.number}"

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
