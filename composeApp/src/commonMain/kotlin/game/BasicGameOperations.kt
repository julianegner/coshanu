package game

import game.GameStateHolder.gameMode
import game.GameStateHolder.gameState
import game.GameStateHolder.level
import game.GameStateHolder.remainingTileAmount
import game.GameStateHolder.tutorial
import game.enums.GameMode
import game.enums.GameState
import util.runOnMainAfter
import util.toClipboard

fun restartGame(
) {
    GameStateHolder.updateGameState(GameState.RESTART)

    println("restartGame: ${GameStateHolder.listState.value.size} ${GameStateHolder.listState.value}")

    GameStateHolder.listState.value.forEachIndexed { index, tileData ->
        println("TEST A: $index ${tileData.toReadableString()} ${tileData.played} ${tileData.chosenForPlay}")
    }

    val tilesForGame = GameStateHolder.listState.value
        .filter { tileData -> tileData.chosenForPlay }

    tilesForGame.forEach {
        it.played = false
        it.borderStroke = null
    }

    val board = GameStateHolder.boardDataState.value

    board.tiles = tilesForGame
    GameStateHolder.listState.value = tilesForGame
    remainingTileAmount.value = tilesForGame.size

    GameStateHolder.updateBoard(board, GameState.RESTART)

    GameStateHolder.listState.value.forEachIndexed { index, tileData ->
        println("TEST B: $index ${tileData.toReadableString()} ${tileData.played} ${tileData.chosenForPlay}")
    }

    println("restartGame:level: ${level.value}")

    tutorial.nextStep()
    // this slight delay in needed for the tiles of the restarted game to be shown
    runOnMainAfter(10L) {
        GameStateHolder.updateGameState(GameState.RUNNING)
    }
    GameStateHolder.timer.startTimer()
}

fun endGame(
) {
    // level.value = null
    gameState.value = GameState.LEVEL_CHANGE
    GameStateHolder.resetBoard()
    GameStateHolder.gameStateText.value = ""
    tutorial.endTutorial()

    GameStateHolder.timer.stopTimer()
    tutorial.endTutorial()
}

fun newGame(
) {
    println("newGame:level: ${level.value}")

    if (level.value !== null) {
        LevelGenerator().generateLevel(level.value!!)
        GameStateHolder.updateGameState(GameState.RUNNING)

        GameStateHolder.timer.startTimer()
    }
}

fun setGameMode(level: Int) {
    when (level) {
        0, 1, 2, 3 -> gameMode.value = GameMode.SINGLE_ELEMENT
        10, 11, 12, 13 -> gameMode.value = GameMode.TWO_ELEMENTS
        else -> gameMode.value = GameMode.SINGLE_ELEMENT
    }
}

fun saveGame() {
    // todo implement save game
    // todo save this to clipboard or file

    val saveGameString = """saveGame
        level: ${level.value}
        rem. tiles: ${remainingTileAmount.value}
        timer: ${GameStateHolder.timer.durationState().value}
        board size: ${GameStateHolder.boardDataState.value.size}
        board: ${GameStateHolder.boardDataState.value.tiles.map { it.toSaveString() }}
        )
        """

    println(saveGameString)
    saveGameString.toClipboard()
}

fun loadGame() {
    // todo show input field for inputting load data
    //  or load from clipboard
    //  or file

    println("loadGame")
    GameStateHolder.updateGameState(GameState.LOAD_GAME)
}
