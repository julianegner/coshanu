package game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object GameStateHolder {
    val boardDataState: MutableState<BoardData> = mutableStateOf(BoardData(1))
    val listState: MutableState<List<TileData>> = mutableStateOf(boardDataState.value.tiles)
    val tutorialTextState: MutableState<String> = mutableStateOf("")
    val gameState: MutableState<GameState> = mutableStateOf(GameState.STARTING)
    val level: MutableState<Int?> = mutableStateOf(null)

    val selected: MutableState<Pair<TileData?, TileData?>> = mutableStateOf(Pair(null, null))

    fun resetBoard() {
        boardDataState.value.reset()
        listState.value = listOf()
    }

    fun updateBoard(newBoardData: BoardData) {
        boardDataState.value = newBoardData
        listState.value = newBoardData.tiles
    }

    fun updateTutorialText(newText: String) {
        tutorialTextState.value = newText
    }

    fun updateGameState(newState: GameState) {
        gameState.value = newState
    }

    fun updateLevel(newLevel: Int) {
        level.value = newLevel
    }

    fun getRemainingTileAmount(): Int {
        return listState.value
            .filter { it.chosenForPlay }
            .filter { !it.played }
            .size
    }

    fun isGameState(state: GameState): Boolean {
        return gameState.value == state
    }

    fun updateSelected(newSelected: Pair<TileData?, TileData?>) {
        selected.value = newSelected
    }

    fun resetSelected() {
        selected.value = Pair(null, null)
    }
}
