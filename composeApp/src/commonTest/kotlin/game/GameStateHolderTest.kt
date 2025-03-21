package game

import androidx.compose.ui.graphics.Color
import game.enums.GameState
import game.enums.ShapeEnum
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameStateHolderTest {

    @Test
    fun testChangeLevel() {
        GameStateHolder.openMenu()
        assertEquals(0, GameStateHolder.level.value)
        GameStateHolder.changeLevel(5)
        assertEquals(5, GameStateHolder.level.value)
    }

    @Test
    fun testGetRemainingTileAmount() {
        // Assuming listState is initialized with some values
        val remainingTiles = GameStateHolder.getRemainingTileAmount()
        assertTrue(remainingTiles >= 0)
    }

    @Test
    fun testIsGameState() {
        GameStateHolder.updateGameState(GameState.RUNNING)
        assertTrue(GameStateHolder.isGameState(GameState.RUNNING))
    }

    @Test
    fun testUpdateSelected() {
        val tile1 = TileData(color = Color.Red, shape = ShapeEnum.CIRCLE, number = 1, chosenForPlay = true, played = false)
        val tile2 = TileData(color = Color.Blue, shape = ShapeEnum.SQUARE, number = 2, chosenForPlay = true, played = false)
        GameStateHolder.updateSelected(Pair(tile1, tile2))
        assertEquals(Pair(tile1, tile2), GameStateHolder.selected.value)
    }

    @Test
    fun testResetSelected() {
        GameStateHolder.resetSelected()
        assertEquals(Pair(null, null), GameStateHolder.selected.value)
    }

    @Test
    fun testCheckGameFinished() {
        // Assuming remainingTileAmount and other states are set up
        GameStateHolder.checkGameFinished()
        // Add assertions based on the expected game state
    }

    @Test
    fun testLostGame() {
        // Assuming listState is initialized with some values
        val result = GameStateHolder.lostGame()
        // Add assertions based on the expected result
    }

    val tile1 = TileData(color = Color.Red, shape = ShapeEnum.CIRCLE, number = 1, chosenForPlay = true, played = false)
    val tile2 = TileData(color = Color.Blue, shape = ShapeEnum.SQUARE, number = 2, chosenForPlay = true, played = false)
    val tile3 = TileData(color = Color.Green, shape = ShapeEnum.HEXAGON, number = 3, chosenForPlay = true, played = false)
    val tile4 = TileData(color = Color.Yellow, shape = ShapeEnum.TRIANGLE, number = 4, chosenForPlay = true, played = false)
    val tile5 = TileData(color = Color.Red, shape = ShapeEnum.CIRCLE, number = 5, chosenForPlay = true, played = false)
    val tile6 = TileData(color = Color.Blue, shape = ShapeEnum.SQUARE, number = 6, chosenForPlay = true, played = false)
    val tile7 = TileData(color = Color.Green, shape = ShapeEnum.HEXAGON, number = 7, chosenForPlay = true, played = false)
    val tile8 = TileData(color = Color.Yellow, shape = ShapeEnum.TRIANGLE, number = 8, chosenForPlay = true, played = false)

    @Test
    fun testPlayCard() {
        // listState is initialized with some values
        GameStateHolder.listState.value = listOf(tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8)
        GameStateHolder.remainingTileAmount.value = 8
        assertEquals(8, GameStateHolder.remainingTileAmount.value)
        GameStateHolder.playCard(tile1, tile5)
        assertEquals(6, GameStateHolder.remainingTileAmount.value)

        // be aware that playCard does not test if the cards match.
        // Thats done in Tile.secondTileMatchesPlayCards()
    }

    @Test
    fun testLoadGame() {
        val gameDataInput = """
            level: 1
            rem. tiles: 16
            timer: 11m 36s
            board size: 4
            board: [red PENTAGON 4 , yellow OCTAGON 2 , yellow CIRCLE 3 , yellow SQUARE 4 , green CIRCLE 1 , yellow CIRCLE 4 , yellow SQUARE 3 , red PENTAGON 2 , yellow SQUARE 1 , yellow HEXAGON 4 , red TRIANGLE 3 , blue CIRCLE 3 , red OCTAGON 4 , green PENTAGON 3 , red SQUARE 3 , green HEXAGON 4 ]
        """.trimIndent()
        GameStateHolder.loadGame(gameDataInput)
        // Add assertions based on the expected state of the game
    }

    @Test
    fun testResetBoard() {
        GameStateHolder.resetBoard()
        assertTrue(GameStateHolder.boardDataState.value.tiles.isEmpty())
    }

    @Test
    fun testUpdateGameState() {
        GameStateHolder.updateGameState(GameState.RUNNING)
        assertEquals(GameState.RUNNING, GameStateHolder.gameState.value)
        GameStateHolder.updateGameState(GameState.WON)
        assertEquals(GameState.WON, GameStateHolder.gameState.value)
        GameStateHolder.updateGameState(GameState.RUNNING)
    }

    @Test
    fun testTimerFunctionality() {
        GameStateHolder.timer.startTimer()
        assertTrue(GameStateHolder.timer.isRunning())
        GameStateHolder.timer.stopTimer()
        assertFalse(GameStateHolder.timer.isRunning())
        GameStateHolder.timer.reset()
        assertEquals(0, GameStateHolder.timer.durationState().value.inWholeSeconds)
    }

    @Test
    fun testUpdateBoard() {
        val boardData = BoardData(4)
        val tile = TileData(color = Color.Red, shape = ShapeEnum.CIRCLE, number = 1, chosenForPlay = true, played = false)
        boardData.tiles = listOf(tile)
        GameStateHolder.updateBoard(boardData)
        assertEquals(boardData, GameStateHolder.boardDataState.value)
    }
}
