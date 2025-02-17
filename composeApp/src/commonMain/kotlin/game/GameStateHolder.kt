package game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import game.enums.GameMode
import game.enums.GameState
import util.runOnMainAfter

object GameStateHolder {
    val boardDataState: MutableState<BoardData> = mutableStateOf(BoardData(1))
    val listState: MutableState<List<TileData>> = mutableStateOf(boardDataState.value.tiles)

    val gameState: MutableState<GameState> = mutableStateOf(GameState.STARTING)
    val level: MutableState<Int?> = mutableStateOf(null)
    val gameMode: MutableState<GameMode?> = mutableStateOf(null)

    val selected: MutableState<Pair<TileData?, TileData?>> = mutableStateOf(Pair(null, null))
    val gameStateText: MutableState<String> = mutableStateOf("")
    val remainingTileAmount: MutableState<Int> = mutableStateOf(0)

    val tutorial: Tutorial = Tutorial()

    val darkModeState: MutableState<Boolean> = mutableStateOf(false)

    fun resetBoard() {
        boardDataState.value.reset()
        listState.value = listOf()
        remainingTileAmount.value = 0
        resetSelected()
    }

    fun saveNewBoard(board: BoardData) {
        updateBoard(board)
        remainingTileAmount.value = getRemainingTileAmount()
    }

    fun updateBoard(newBoardData: BoardData) {
        if (boardDataState.value !== newBoardData) {
            boardDataState.value = newBoardData
            listState.value = newBoardData.tiles
        }
    }

    fun updateBoard(newBoardData: BoardData, newState: GameState) {
        updateBoard(newBoardData)
        updateGameState(newState)
    }

    fun updateGameState(newState: GameState) {
        gameState.value = newState
        if (newState == GameState.WON) {
            runOnMainAfter(4000L) {
                level.value = null
            }
        }
    }

    fun changeLevel(newLevel: Int) {
        level.value = newLevel
        updateGameState(GameState.LEVEL_CHANGE)
        resetBoard()
    }

    // this only works at startup
    fun getRemainingTileAmount(): Int {
        val remainingTileAmount =  listState.value
            .filter { it.chosenForPlay }
            .filter { !it.played }
            .size
        return remainingTileAmount
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

    fun checkGameFinished(
    ) {
        if ( remainingTileAmount.value == 0) {
            if (tutorial.isTutorial()) {
                runOnMainAfter(5000L) {
                    tutorial.endTutorial()
                    updateGameState(GameState.WON)
                }
            } else {
                updateGameState(GameState.WON)
            }
        } else if (this.lostGame()) {
            updateGameState(GameState.LOST)
        } else {
            updateGameState(GameState.RUNNING)
        }
    }

    fun lostGame(): Boolean {
        val tilesInGame = listState.value
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

    fun playCard(vararg tileDataArgs: TileData) {
        tileDataArgs.forEach { tileData ->
            val list = listState.value
            list
                .filter { it.same(tileData) }
                .first()
                .played = true

            listState.value = list
        }
        boardDataState.value.tiles = listState.value

        remainingTileAmount.value -= 2
    }
}
