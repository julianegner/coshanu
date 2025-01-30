package game

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color

fun Color.toName(): String =
    when (this) {
        Color.Blue -> "Blue"
        Color.Green -> "Green"
        Color.Red -> "Red"
        Color.Yellow -> "Yellow"
        Color.LightGray -> "LightGray"
        Color.DarkGray -> "DarkGray"
        Color.Magenta -> "Magenta"
        Color.Cyan -> "Cyan"
        else -> "Unknown"
    }

data class TileData(
    val color: Color,
    val number: Int,
    val shape: ShapeEnum,
    var chosenForPlay: Boolean,
    var played: Boolean,
    var borderStroke: BorderStroke? = null
)

fun TileData.tutorialString(): String = "${this.color.toName().lowercase()} ${this.shape.name.lowercase()} with Number ${this.number}"

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
