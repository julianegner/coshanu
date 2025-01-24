package game

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

class LevelGenerator {
    fun generateLevel(levelNumber: Int) {
                      // boardDataState: MutableState<BoardData>,
                      // listState: MutableState<List<TileData>>) {

        // GameStateHolder.boardDataState, GameStateHolder.listState

        GameStateHolder.resetBoard()
        when(levelNumber) {
            0 -> generateTutorial()
            1 -> {
                    val board = BoardData(4)
                    board.tiles = board.tiles
                        .filter { tileData -> tileData.chosenForPlay }
                        .shuffled()
                    GameStateHolder.saveNewBoard(board)
            }
            2 -> {
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
            3 -> {
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
            // boardDataState: MutableState<BoardData>,
            // listState: MutableState<List<TileData>>) {

        // GameStateHolder.boardDataState, GameStateHolder.listState

        val board = BoardData(2)

        // boardDataState.value = BoardData(2)
        board.tiles = listOf(
            TileData(
                color = Color.Green,
                shape = ShapeEnum.TRIANGLE,
                number = 3,
                chosenForPlay = true,
                played = false
            ),
            TileData(
                color = Color.Yellow,
                shape = ShapeEnum.TRIANGLE,
                number = 3,
                chosenForPlay = true,
                played = false
            ),
            TileData(
                color = Color.Blue,
                shape = ShapeEnum.TRIANGLE,
                number = 3,
                chosenForPlay = true,
                played = false
            ),
            TileData(
                color = Color.Blue,
                shape = ShapeEnum.CIRCLE,
                number = 1,
                chosenForPlay = true,
                played = false
            )
        )
        GameStateHolder.saveNewBoard(board)
    }
}
