package game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object GameStateHolder {
    val boardDataState: MutableState<BoardData> = mutableStateOf(BoardData(1))
    val listState: MutableState<List<TileData>> = mutableStateOf(boardDataState.value.tiles)
    val tutorialTextState: MutableState<String> = mutableStateOf("")
    val gameState: MutableState<GameState> = mutableStateOf(GameState.STARTING)
    val level: MutableState<Int?> = mutableStateOf(null)
    val gameMode: MutableState<GameMode?> = mutableStateOf(null)

    val selected: MutableState<Pair<TileData?, TileData?>> = mutableStateOf(Pair(null, null))
    val gameStateText: MutableState<String> = mutableStateOf("")
    val remainingTileAmount: MutableState<Int> = mutableStateOf(0)

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
        println("GameStateHolder.updateBoard 2: $newBoardData")
        updateBoard(newBoardData)
        updateGameState(newState)
    }

    fun updateTutorialText(newText: String) {
        tutorialTextState.value = newText
    }

    fun updateGameState(newState: GameState) {
        gameState.value = newState
        updateGameStateTextRemainingTileAmount()
        // if (newState == GameState.LOST || newState == GameState.WON) {
        if (newState == GameState.WON) {
            level.value = null
        }
    }

    fun changeLevel(newLevel: Int) {
        level.value = newLevel
        updateGameState(GameState.LEVEL_CHANGE)
        resetBoard()
        updateTutorialText("") // tutorial text is set when game is started
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

    fun isTutorial(): Boolean {
        return level.value == 0 || level.value == 10
    }

    fun updateSelected(newSelected: Pair<TileData?, TileData?>) {
        selected.value = newSelected
    }

    fun resetSelected() {
        selected.value = Pair(null, null)
    }

    fun getGameStateText(): MutableState<String> {
        val text = when (gameState.value) {
            GameState.RUNNING ->
                "${gameState.value.message} Remaining tiles: ${remainingTileAmount.value}"
            else -> gameState.value.message
        }
        gameStateText.value = text
        return gameStateText
    }

    fun checkGameFinished(
    ) {
        if ( remainingTileAmount.value == 0) {
            updateGameState(GameState.WON)
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
    }

    private fun updateGameStateTextRemainingTileAmount() {
        gameStateText.value = getGameStateText().value
    }
}
