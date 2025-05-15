package game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import game.enums.GameMode
import game.enums.GameState
import game.enums.ShapeEnum
import ui.SoundBytes
import ui.UiStateHolder
import util.Timer
import util.stringToColor
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object GameStateHolder {
    val boardDataState: MutableState<BoardData> = mutableStateOf(BoardData(1))
    val listState: MutableState<List<TileData>> = mutableStateOf(boardDataState.value.tiles)

    val gameState: MutableState<GameState> = mutableStateOf(GameState.STARTING)
    val level: MutableState<Int?> = mutableStateOf(null)
    val currentLevel: MutableState<Level?> = mutableStateOf(null)
    val gameMode: MutableState<GameMode?> = mutableStateOf(null)

    val selected: MutableState<Pair<TileData?, TileData?>> = mutableStateOf(Pair(null, null))
    val gameStateText: MutableState<String> = mutableStateOf("")
    val remainingTileAmount: MutableState<Int> = mutableStateOf(0)

    val tutorial: Tutorial = Tutorial()

    val timer = Timer()
    val addTime: MutableState<Duration> = mutableStateOf(10.seconds)
    val startTime: MutableState<Duration> = mutableStateOf(60.seconds)
    val totalAllowedTime: MutableState<Duration> = mutableStateOf(startTime.value)

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
    }

    fun levelUp() {
        endGame()
        if (currentLevel.value == null) {
            changeLevel(0)
        } else {
            changeLevel(currentLevel.value!!.getNextLevel())
        }
    }

    fun changeLevel(newLevel: Int) {
        level.value = newLevel
        updateGameState(GameState.LEVEL_CHANGE)
        resetBoard()
    }

    fun openMenu() {
        changeLevel(level.value ?: 0)
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
            timer.stopTimer()
            updateGameState(GameState.WON)
            UiStateHolder.sound.play(SoundBytes.WON)
        } else if (this.lostGame()) {
            updateGameState(GameState.LOST)
            timer.stopTimer()
            UiStateHolder.sound.play(SoundBytes.LOST)
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

    fun playCard(first: TileData, second: TileData) {
        val tileDataArgs: List<TileData> = listOf(first, second)

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

    fun addTimeToTimer() {
        totalAllowedTime.value += addTime.value
    }

    fun resetTotalAllowedTime() {
        totalAllowedTime.value = startTime.value
    }

    fun loadGame(gameDataInput: String) {
        println("loadGame:gameDataInput: $gameDataInput")

        // todo add version of game data
        /*
        saveGame
        level: 1
        rem. tiles: 16
        timer: 11m 36s
        board size: 4
        board: [red PENTAGON 4 , yellow OCTAGON 2 , yellow CIRCLE 3 , yellow SQUARE 4 , green CIRCLE 1 , yellow CIRCLE 4 , yellow SQUARE 3 , red PENTAGON 2 , yellow SQUARE 1 , yellow HEXAGON 4 , red TRIANGLE 3 , blue CIRCLE 3 , red OCTAGON 4 , green PENTAGON 3 , red SQUARE 3 , green HEXAGON 4 ]
        )
         */

        val gameData = gameDataInput.split("\n")

        var level: Int? = null
        var remainingTileAmount: Int? = null
        // var minutes: Int? = null
        // var seconds: Int? = null
        var durationString: String? = null
        var boardSize: Int? = null
        val tileDataList = mutableListOf<TileData>()

        // todo write a for each loop of gamedata element
        gameData.forEach { data ->
            if (data.contains("level:")) {
                level = data.split(":")[1].trim().toInt()
            }
            if (data.contains("rem. tiles:")) {
                remainingTileAmount = data.split(":")[1].trim().toInt()
            }
            if (data.contains("timer:")) {
                durationString = data.split(":")[1].trim()
                // minutes = time.split("m")[0].trim().toInt()
                // seconds = time.split("m")[1].split("s")[0].trim().toInt()
            }
            if (data.contains("board size:")) {
                boardSize = data.split(":")[1].trim().toInt()
            }
            if (data.contains("board:")) {
                val board = data.split(":")[1].trim().trim('[', ']').trim()
                val tiles = board.split(",")


                println("TEST: import save: tiles: ${tiles.size}")

                tiles.forEach { tile ->
                    val tileData = tile.trim().split(" ")
                    tileDataList.add(
                        TileData(
                            color = stringToColor(tileData[0]),
                            shape = ShapeEnum.valueOf(tileData[1]),
                            number = tileData[2].toInt(),
                            chosenForPlay = true,
                            played = tile.contains("played")
                        )
                    )
                }

                println("TEST: import save: tileDataList: ${tileDataList.size}")
            }
        }

        // changeLevel(level)
        // remainingTileAmount.value
        // timer.startTimer(Duration.minutes(minutes.toDouble()) + Duration.seconds(seconds.toDouble()))


        // val boardData = BoardData(boardSize!!)
        // boardData.tiles = tileDataList
        // updateBoard(boardData)

        println(    "TEST: loadGame: \n" +
                    "level: $level\n" +
                    "remainingTileAmount: $remainingTileAmount\n" +
                    "durationString: $durationString\n" +
                    // "minutes: $minutes\n" +
                    // "seconds: $seconds\n" +
                    "boardSize: $boardSize\n" +
                    "tileDataList: $tileDataList\n"
        )

        // todo set the correct game states and then set the game state to running

        if (level != null && remainingTileAmount != null
            // && minutes != null && seconds != null
            && durationString != null
            && boardSize != null) {
            // loadGame(level, remainingTileAmount, minutes, seconds, boardSize, tileDataList)
            changeLevel(level!!)
            // setGameMode(GameStateHolder.level.value!!)
            // todo set game mode based on level
            GameStateHolder.resetBoard()


            // GameStateHolder.remainingTileAmount.value = remainingTileAmount!!
            // timer.startTimer(Duration.minutes(minutes.toDouble()) + Duration.seconds(seconds.toDouble()))

            println("TEST gamemode: ${GameStateHolder.gameMode.value}")

            val boardData = BoardData(boardSize!!)
            boardData.tiles = tileDataList
            updateBoard(boardData)
            GameStateHolder.boardDataState.value.tiles.forEachIndexed { index, tile -> println("TEST A: tile $index: ${tile.toReadableString(true)}") }
            GameStateHolder.remainingTileAmount.value = remainingTileAmount!!
            GameStateHolder.updateGameState(GameState.RUNNING)
            val duration = Duration.parse(durationString!!)
            GameStateHolder.timer.startTimerFromSave(duration)


            // GameStateHolder.boardDataState.value.tiles.forEach { println("TEST: tile: $it") }
            GameStateHolder.boardDataState.value.tiles.forEachIndexed { index, tile -> println("TEST B: tile $index: ${tile.toReadableString(true)}") }
        }
    }
}
