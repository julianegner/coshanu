package game

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

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

fun TileData.same(secondTileData: TileData): Boolean {
    return ((this.color == secondTileData.color) && (this.shape == secondTileData.shape) && (this.number == secondTileData.number))
}

class BoardData(
        val size: Int = 4,
        maxNumber: Int = 4,
        val colors: List<Color> = listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)
    ) {
    var tiles: List<TileData> = createBoard(size, maxNumber, colors)

    var selected: Pair<TileData?, TileData?> = Pair(null, null)

    fun reset() {
        tiles = listOf()
        selected = Pair(null, null)
    }

    fun createBoard(size: Int, maxNumber: Int, colors: List<Color>): List<TileData> {
        if (size == 0) { return listOf() }
        val colors: List<Color> = colors
        val shapes: List<ShapeEnum> = listOf(
            ShapeEnum.CIRCLE,
            ShapeEnum.TRIANGLE,
            ShapeEnum.SQUARE,
            ShapeEnum.PENTAGON,
            ShapeEnum.HEXAGON,
            ShapeEnum.OKTAGON
        )
        val numbers = (1..maxNumber).toList()
        val boardSize = size

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

        for (i in p) {
            when (GameStateHolder.gameMode.value) {
                GameMode.SINGLE_ELEMENT -> {
                    findPair(possibleTiles)
                }
                GameMode.TWO_ELEMENTS -> {
                    findPairTwoMatchingElements(possibleTiles)
                }
                else -> {
                    // this should never happen
                    throw RuntimeException("GameMode not valid")
                }
            }
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

    private fun findPairTwoMatchingElements(possibleTiles: List<TileData>) {
        val firstTile = possibleTiles
            .filter {tileData -> !tileData.chosenForPlay }
            .random()

        firstTile.chosenForPlay = true

        // make mixed dimensions
        val dimColorShape = possibleTiles
            .filter { tileData -> !tileData.chosenForPlay }
            .filter { tileData -> tileData.color == firstTile.color }
            .filter { tileData -> tileData.shape == firstTile.shape }

        val dimColorNumber = possibleTiles
            .filter { tileData -> !tileData.chosenForPlay }
            .filter { tileData -> tileData.color == firstTile.color }
            .filter { tileData -> tileData.number == firstTile.number }

        val dimShapeNumber = possibleTiles
            .filter { tileData -> !tileData.chosenForPlay }
            .filter { tileData -> tileData.shape == firstTile.shape }
            .filter { tileData -> tileData.number == firstTile.number }


        // need to know which dimension is largest
        val pairDimension = when {
            dimColorShape.size >= dimColorNumber.size && dimColorShape.size >= dimShapeNumber.size -> dimColorShape
            dimColorNumber.size >= dimColorShape.size && dimColorNumber.size >= dimShapeNumber.size -> dimColorNumber
            dimShapeNumber.size >= dimColorShape.size && dimShapeNumber.size >= dimColorNumber.size -> dimShapeNumber
            else -> {
                if (dimColorShape.size > 0) { dimColorShape} else {
                    if (dimColorNumber.size > 0) dimColorNumber else {
                        dimShapeNumber
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
