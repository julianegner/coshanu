package game

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

class LevelGenerator {
    fun generateLevel(levelNumber: Int) {

        GameStateHolder.resetBoard()
        when(levelNumber) {
            0 -> generateTutorial()
            10 -> generateTutorial() // todo tutorial for two elements
            1,11 -> {
                    val board = BoardData(4)
                    board.tiles = board.tiles
                        .filter { tileData -> tileData.chosenForPlay }
                        .shuffled()
                    GameStateHolder.saveNewBoard(board)
            }
            2,12 -> {
                val board = BoardData(
                    size = 4,
                    maxNumber = 10,
                    colors = listOf(
                        Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan
                    )
                )
                board.tiles = board.tiles
                    .filter { tileData -> tileData.chosenForPlay }
                    .shuffled()
                GameStateHolder.saveNewBoard(board)
            }
            3,13 -> {
                val board = BoardData(
                    size = 8,
                    maxNumber = 10,
                    colors = listOf(
                    Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan
                    )
                )
                board.tiles = board.tiles
                    .filter { tileData -> tileData.chosenForPlay }
                    .shuffled()
                GameStateHolder.saveNewBoard(board)
            }
            else -> {
                val board = BoardData(size = levelNumber * 4, maxNumber = 2 * levelNumber)
                board.tiles = board.tiles
                    .filter { tileData -> tileData.chosenForPlay }
                    .shuffled()
                GameStateHolder.saveNewBoard(board)
            }
        }
    }

    fun generateTutorial() {


        val board = BoardData(if (GameStateHolder.level.value == 0) {2} else {4})

        GameStateHolder.tutorial.startTutorial()
        board.tiles = GameStateHolder.tutorial.getTileList()
        GameStateHolder.saveNewBoard(board)
    }
}
