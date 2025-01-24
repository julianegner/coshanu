package game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ui.Board

object GameStateHolder {
    val boardDataState: MutableState<BoardData> = mutableStateOf(BoardData(1))
    val listState: MutableState<List<TileData>> = mutableStateOf(boardDataState.value.tiles)
    val tutorialTextState: MutableState<String> = mutableStateOf("")
    val gameState: MutableState<GameState> = mutableStateOf(GameState.STARTING)
    val level: MutableState<Int?> = mutableStateOf(null)

    val selected: MutableState<Pair<TileData?, TileData?>> = mutableStateOf(Pair(null, null))
    val gameStateText: MutableState<String> = mutableStateOf("")
    val remainingTileAmount: MutableState<Int> = mutableStateOf(0)

    fun resetBoard() {
        boardDataState.value.reset()
        listState.value = listOf()
        remainingTileAmount.value = 0
    }

    fun saveNewBoard(board: BoardData) {
        updateBoard(board)
        println("------ saveNewBoard: ${remainingTileAmount.value}")
        remainingTileAmount.value = getRemainingTileAmount()
    }

    fun updateBoard(newBoardData: BoardData) {
        if (boardDataState.value !== newBoardData) {
            boardDataState.value = newBoardData
            listState.value = newBoardData.tiles
        }
        println("------ updateBoard: ${remainingTileAmount.value}")
        // remainingTileAmount.value = getRemainingTileAmount()
    }

    fun updateBoard(newBoardData: BoardData, newState: GameState) {
        println("GameStateHolder.updateBoard 2: $newBoardData")
        updateBoard(newBoardData)
        updateGameState(newState)
    }

    fun updateTutorialText(newText: String) {
        tutorialTextState.value = newText
    }

    fun updateGameState(newState: GameState) {
        println("GameStateHolder.updateGameState: $newState")

        println("GameStateHolder.updateGameState tiles: ${listState.value}")

        gameState.value = newState
        updateGameStateTextRemainingTileAmount()

        println("GameStateHolder.updateGameState: " +
                "remainingTileAmount ${remainingTileAmount.value} " +
                "gameStateText ${gameStateText.value} " +
                "getRemainingTileAmount ${getRemainingTileAmount()}"
        )
    }

    fun changeLevel(newLevel: Int) {
        level.value = newLevel
        // GameStateHolder.updateBoard(BoardData(1))

        if (GameStateHolder.level.value !== null) {
            resetBoard()
            LevelGenerator().generateLevel(level.value!!)
        }
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

    fun getGameStateText(): MutableState<String> {
        println("GameStateHolder.getGameStateText: ${gameState.value} ${remainingTileAmount.value}")
        val text = when (gameState.value) {
            GameState.RUNNING -> gameState.value.message + " " + remainingTileAmount.value
            else -> gameState.value.message
        }
        gameStateText.value = text
        return gameStateText
    }

    fun checkGameFinished(
    ) {
        println("GameStateHolder.checkGameFinished: ${listState.value}")

        val r = remainingTileAmount.value // getRemainingTileAmount()
        println("GameStateHolder.checkGameFinished getRemainingTileAmount: $r")
        if ( r == 0) {
            updateGameState(GameState.WON)
        } else if (this.lostGame()) {
            updateGameState(GameState.LOST)
        } else {
            println("GameStateHolder.checkGameFinished A: ${listState.value}")
            updateGameState(GameState.RUNNING)
            // remainingTileAmount.value -= 2 // getRemainingTileAmount()
            println("GameStateHolder.checkGameFinished: remainingTileAmount ${remainingTileAmount.value}")
            println("GameStateHolder.checkGameFinished B: ${listState.value} --- ${boardDataState.value.tiles}")
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
            println("GameStateHolder.playCard: $tileData")

            val list = listState.value
            list
                .filter { it.same(tileData) }
                .first()
                .played = true

            listState.value = list
        }
        boardDataState.value.tiles = listState.value

        println("GameStateHolder.playCard A: ${remainingTileAmount.value}")
        remainingTileAmount.value -= 2
        updateGameStateTextRemainingTileAmount()

        println("GameStateHolder.playCard B: ${remainingTileAmount.value}")
        println("playCard tiles: ${listState.value} ${boardDataState.value.tiles}")

        // val board = boardDataState.value
        // listState.value.get(listState.value.indexOf(tileData)).played = true
        // board.tiles = listState.value

        // updateBoard(board)
    }

    private fun updateGameStateTextRemainingTileAmount() {
        // remainingTileAmount.value = getRemainingTileAmount()
        gameStateText.value = getGameStateText().value

        println("GameStateHolder.updateGameStateTextRemainingTileAmount: " +
                "remainingTileAmount ${remainingTileAmount.value} " +
                "gameStateText ${gameStateText.value} " +
                "getRemainingTileAmount ${remainingTileAmount.value}"
        )
    }
}
