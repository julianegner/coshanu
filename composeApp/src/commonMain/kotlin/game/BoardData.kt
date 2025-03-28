package game

import androidx.compose.ui.graphics.Color

data class BoardData(
    val size: Int = 4,
    val maxNumber: Int = 4,
    val colors: List<Color> = listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red),
    var tiles: List<TileData> = listOf(),
    var selected: Pair<TileData?, TileData?> = Pair(null, null)
)

fun BoardData.reset() {
    tiles = listOf()
    selected = Pair(null, null)
}
