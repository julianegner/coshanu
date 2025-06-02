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
import kotlin.time.Duration.Companion.seconds

fun restartGame(
) {
    GameStateHolder.updateGameState(GameState.RESTART)

    println("restartGame: ${GameStateHolder.listState.value.size} ${GameStateHolder.listState.value}")

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

    // this slight delay in needed for the tiles of the restarted game to be shown
    runOnMainAfter(10L) {
        GameStateHolder.updateGameState(GameState.RUNNING)
    }
    tutorial.nextStep()

    GameStateHolder.resetTotalAllowedTime()
    GameStateHolder.timer.startTimer()
}

fun endGame(
) {
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
        setCountdownTimerValues(level.value!!)
        GameStateHolder.updateGameState(GameState.RUNNING)

        GameStateHolder.timer.startTimer()
        GameStateHolder.resetTotalAllowedTime()
    }
}

private fun setCountdownTimerValues(level: Int) {
    when (level) {
        in 20 ..23 -> {
            GameStateHolder.addTime.value = 10.seconds
            GameStateHolder.startTime.value = 60.seconds
        }
        in 24..26 -> {
            GameStateHolder.addTime.value = 5.seconds
            GameStateHolder.startTime.value = 30.seconds
        }
        else -> {
            GameStateHolder.addTime.value = 10.seconds
            GameStateHolder.startTime.value = 60.seconds
        }
    }
}

fun setGameMode(level: Int) {
    when (level) {
        in 0..3 -> gameMode.value = GameMode.SINGLE_ELEMENT
        in 10..13 -> gameMode.value = GameMode.TWO_ELEMENTS
        in 20..26 -> gameMode.value = GameMode.TWO_ELEMENTS_WITH_TIMER
        else -> gameMode.value = GameMode.SINGLE_ELEMENT
    }
}

fun saveGame() {
    // todo implement save game
    // todo save this to clipboard or file
    // todo add version of game data

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

    GameStateHolder.updateGameState(GameState.LOAD_GAME)
}
