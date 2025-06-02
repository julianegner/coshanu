package game

import androidx.compose.ui.graphics.Color
import game.enums.GameMode
import game.enums.ShapeEnum

class LevelGenerator {
    fun generateLevel(levelNumber: Int) {

        GameStateHolder.resetBoard()
        setGameMode(levelNumber)
        GameStateHolder.tutorial.endTutorial()
        when(levelNumber) {
            0, 10, 20 -> generateTutorial()
            1, 11, 21, 24 -> GameStateHolder.saveNewBoard(createBoard(4, 4, listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)))
            2, 12, 22, 25 -> GameStateHolder.saveNewBoard(createBoard(size = 4, maxNumber = 10, colors = listOf(Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan)))
            3, 13, 23, 26 -> GameStateHolder.saveNewBoard(createBoard(size = 8, maxNumber = 10, colors = listOf(Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan)))
            else -> GameStateHolder.saveNewBoard(createBoard(size = levelNumber * 4, maxNumber = 2 * levelNumber, colors = listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red)))
        }
    }
    // non-private to allow testing
    fun createBoard(size: Int, maxNumber: Int, colors: List<Color>): BoardData {
        val tiles = selectTilesForGame(size, maxNumber, colors)
        return BoardData(size, maxNumber, colors, tiles)
    }

    fun selectTilesForGame(size: Int, maxNumber: Int, colors: List<Color>): List<TileData> {
        if (size == 0) return listOf()
        val shapes = listOf(ShapeEnum.CIRCLE, ShapeEnum.TRIANGLE, ShapeEnum.SQUARE, ShapeEnum.PENTAGON, ShapeEnum.HEXAGON, ShapeEnum.OCTAGON)
        val numbers = (1..maxNumber).toList()
        val possibleTiles = generatePossibleTiles(colors, shapes, numbers)

        val neededPairs = (size * size) / 2
        for (i in 1..neededPairs) {
            when (GameStateHolder.gameMode.value) {
                GameMode.SINGLE_ELEMENT -> findPair(possibleTiles)
                GameMode.TWO_ELEMENTS -> findPairTwoMatchingElements(possibleTiles)
                GameMode.TWO_ELEMENTS_WITH_TIMER -> findPairTwoMatchingElements(possibleTiles)
                else -> throw RuntimeException("GameMode not valid")
            }
        }

        return possibleTiles
            .filter { tileData -> tileData.chosenForPlay }
            .shuffled()
    }

    private fun generatePossibleTiles(
        colors: List<Color>,
        shapes: List<ShapeEnum>,
        numbers: List<Int>
    ): ArrayList<TileData> {
        val possibleTiles = arrayListOf<TileData>()

        for (color in colors) {
            for (shape in shapes) {
                for (number in numbers) {
                    possibleTiles.add(TileData(color = color, shape = shape, number = number, chosenForPlay = false, played = false))
                }
            }
        }
        return possibleTiles
    }

    fun generateTutorial() {
        val board = BoardData(if (GameStateHolder.level.value == 0) {2} else {4})

        GameStateHolder.tutorial.startTutorial()
        board.tiles = GameStateHolder.tutorial.getTileList()
        GameStateHolder.saveNewBoard(board)
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
