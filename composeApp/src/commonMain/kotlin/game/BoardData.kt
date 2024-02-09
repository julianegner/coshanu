package game

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import ui.boardData

enum class ShapeEnum {
    CIRCLE, TRIANGLE, SQUARE, PENTAGON, HEXAGON, OKTAGON
}

data class TileData(
    val color: Color,
    val number: Int,
    val shape: ShapeEnum,
    var chosenForPlay: Boolean,
    var played: Boolean,
    var borderStroke: BorderStroke? = null
)

fun TileData.match(secondTileData: TileData): Boolean {
    return ((this.color == secondTileData.color) || (this.shape == secondTileData.shape) || (this.number == secondTileData.number))
}

fun TileData.same(secondTileData: TileData): Boolean {
    return ((this.color == secondTileData.color) && (this.shape == secondTileData.shape) && (this.number == secondTileData.number))
}

// todo size, amount of the dimensions as optional params
class BoardData() {
    var tiles: List<TileData> = createBoard()
    val size = 4

    var selected: Pair<TileData?, TileData?> = Pair(null, null)

    fun lostGame(): Boolean {
        val tilesInGame = boardData.tiles
            .filter { it.chosenForPlay }
            .filter { !it.played }

        tilesInGame.forEach { tile ->
            if (tilesInGame
                .filter { !it.same(tile) }
                .filter { it.match(tile) }
                .isEmpty()
                ) {
                return true
            }
        }
        return false
    }

    fun createBoard(): List<TileData> {
        val colors: List<Color> = listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)
        val shapes: List<ShapeEnum> = listOf(
            ShapeEnum.CIRCLE,
            ShapeEnum.TRIANGLE,
            ShapeEnum.SQUARE,
            ShapeEnum.PENTAGON,
            ShapeEnum.HEXAGON,
            ShapeEnum.OKTAGON
        )
        val numbers = (1..4).toList()
        val boardSize = 4 // todo this.size does not work

        val possibleTiles: ArrayList<TileData> = arrayListOf()

        for (color in colors) {
            for (shape in shapes) {
                for (number in numbers) {
                    possibleTiles.add(
                        TileData(
                        color = color,
                        shape = shape,
                        number = number,
                        chosenForPlay = false,
                        played = false
                    )
                    )
                }
            }
        }
        return selectTilesForGame(boardSize, possibleTiles)
    }

    fun selectTilesForGame(
        boardSize: Int,
        possibleTiles: List<TileData>
    ): List<TileData> {
        val neededPairs = (boardSize * boardSize) / 2
        val p = (1..neededPairs).toList()

        println("possibleTiles: ${possibleTiles.size}, neededPairs: $neededPairs")

        for (i in p) {
            findPair(possibleTiles)
        }
        return possibleTiles
    }

    private fun findPair(possibleTiles: List<TileData>) {
        val firstTile = possibleTiles
            .filter {tileData -> !tileData.chosenForPlay }
            .random()

        firstTile.chosenForPlay = true
        // need to know which dimension is largest
        val dimColor = possibleTiles
            .filter { tileData -> !tileData.chosenForPlay }
            .filter { tileData -> tileData.color == firstTile.color }

        val dimShape = possibleTiles
            .filter { tileData -> !tileData.chosenForPlay }
            .filter { tileData -> tileData.shape == firstTile.shape }

        val dimNumber = possibleTiles
            .filter { tileData -> !tileData.chosenForPlay }
            .filter { tileData -> tileData.number == firstTile.number }

        val pairDimension = when {
            dimColor.size >= dimShape.size && dimColor.size >= dimNumber.size -> dimColor
            dimShape.size >= dimColor.size && dimShape.size >= dimNumber.size -> dimShape
            dimNumber.size >= dimColor.size && dimNumber.size >= dimShape.size -> dimNumber
            else -> {
                if (dimColor.size > 0) { dimColor} else {
                    if (dimShape.size > 0) dimShape else {
                        dimNumber
                    }
                }
            }
        }

        if (pairDimension.isEmpty()) {
            throw RuntimeException("pairDimension is empty")
        }

        val secondTile = pairDimension.random()
        secondTile.chosenForPlay = true
    }
}
