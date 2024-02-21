package game

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

class LevelGenerator {
    fun generateLevel(levelNumber: Int,
                      boardDataState: MutableState<BoardData>,
                      listState: MutableState<List<TileData>>) {

        when(levelNumber) {
            0 -> generateTutorial(boardDataState, listState)
            1 -> {
                    boardDataState.value = BoardData(size = 4)
                    listState.value =
                        boardDataState.value.tiles
                            .filter { tileData -> tileData.chosenForPlay }
                            .shuffled()
            }
            2 -> {
                boardDataState.value = BoardData(
                    size = 4,
                    maxNumber = 10,
                    colors = listOf(
                        Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan
                    )
                )
                listState.value =
                    boardDataState.value.tiles
                        .filter { tileData -> tileData.chosenForPlay }
                        .shuffled()
            }
            3 -> {
                boardDataState.value = BoardData(
                    size = 8,
                    maxNumber = 10,
                    colors = listOf(
                    Color.Green, Color.Red, Color.Blue, Color.Yellow, Color.DarkGray, Color.Magenta, Color.Cyan
                    )
                )
                listState.value =
                    boardDataState.value.tiles
                        .filter { tileData -> tileData.chosenForPlay }
                        .shuffled()
            }
            else -> {
                boardDataState.value = BoardData(size = levelNumber * 4, maxNumber = 2 * levelNumber)
                listState.value =
                    boardDataState.value.tiles
                        .filter { tileData -> tileData.chosenForPlay }
                        .shuffled()
            }
        }
    }

    fun generateTutorial(
            boardDataState: MutableState<BoardData>,
            listState: MutableState<List<TileData>>) {
        boardDataState.value = BoardData(2)

        boardDataState.value.tiles = listOf(
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

        listState.value = boardDataState.value.tiles
    }
}
