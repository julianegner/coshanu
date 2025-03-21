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

        assertEquals(1, GameStateHolder.level.value)
        assertEquals(16, GameStateHolder.remainingTileAmount.value)
        assertEquals(4, GameStateHolder.boardDataState.value.size)
        assertEquals(16, GameStateHolder.boardDataState.value.tiles.size)

        // check if tiles are correctly loaded
        val tile = GameStateHolder.boardDataState.value.tiles[0]
        assertEquals(Color.Red, tile.color)
        assertEquals(ShapeEnum.PENTAGON, tile.shape)
        assertEquals(4, tile.number)
        val tile1 = GameStateHolder.boardDataState.value.tiles[1]
        assertEquals(Color.Yellow, tile1.color)
        assertEquals(ShapeEnum.OCTAGON, tile1.shape)
        assertEquals(2, tile1.number)
        val tile2 = GameStateHolder.boardDataState.value.tiles[2]
        assertEquals(Color.Yellow, tile2.color)
        assertEquals(ShapeEnum.CIRCLE, tile2.shape)
        assertEquals(3, tile2.number)
        val tile3 = GameStateHolder.boardDataState.value.tiles[3]
        assertEquals(Color.Yellow, tile3.color)
        assertEquals(ShapeEnum.SQUARE, tile3.shape)
        assertEquals(4, tile3.number)
        val tile4 = GameStateHolder.boardDataState.value.tiles[4]
        assertEquals(Color.Green, tile4.color)
        assertEquals(ShapeEnum.CIRCLE, tile4.shape)
        assertEquals(1, tile4.number)
        val tile5 = GameStateHolder.boardDataState.value.tiles[5]
        assertEquals(Color.Yellow, tile5.color)
        assertEquals(ShapeEnum.CIRCLE, tile5.shape)
        assertEquals(4, tile5.number)
        val tile6 = GameStateHolder.boardDataState.value.tiles[6]
        assertEquals(Color.Yellow, tile6.color)
        assertEquals(ShapeEnum.SQUARE, tile6.shape)
        assertEquals(3, tile6.number)
        val tile7 = GameStateHolder.boardDataState.value.tiles[7]
        assertEquals(Color.Red, tile7.color)
        assertEquals(ShapeEnum.PENTAGON, tile7.shape)
        assertEquals(2, tile7.number)
        val tile8 = GameStateHolder.boardDataState.value.tiles[8]
        assertEquals(Color.Yellow, tile8.color)
        assertEquals(ShapeEnum.SQUARE, tile8.shape)
        assertEquals(1, tile8.number)
        val tile9 = GameStateHolder.boardDataState.value.tiles[9]
        assertEquals(Color.Yellow, tile9.color)
        assertEquals(ShapeEnum.HEXAGON, tile9.shape)
        assertEquals(4, tile9.number)
        val tile10 = GameStateHolder.boardDataState.value.tiles[10]
        assertEquals(Color.Red, tile10.color)
        assertEquals(ShapeEnum.TRIANGLE, tile10.shape)
        assertEquals(3, tile10.number)
        val tile11 = GameStateHolder.boardDataState.value.tiles[11]
        assertEquals(Color.Blue, tile11.color)
        assertEquals(ShapeEnum.CIRCLE, tile11.shape)
        assertEquals(3, tile11.number)
        val tile12 = GameStateHolder.boardDataState.value.tiles[12]
        assertEquals(Color.Red, tile12.color)
        assertEquals(ShapeEnum.OCTAGON, tile12.shape)
        assertEquals(4, tile12.number)
        val tile13 = GameStateHolder.boardDataState.value.tiles[13]
        assertEquals(Color.Green, tile13.color)
        assertEquals(ShapeEnum.PENTAGON, tile13.shape)
        assertEquals(3, tile13.number)
        val tile14 = GameStateHolder.boardDataState.value.tiles[14]
        assertEquals(Color.Red, tile14.color)
        assertEquals(ShapeEnum.SQUARE, tile14.shape)
        assertEquals(3, tile14.number)
        val tile15 = GameStateHolder.boardDataState.value.tiles[15]
        assertEquals(Color.Green, tile15.color)
        assertEquals(ShapeEnum.HEXAGON, tile15.shape)
        assertEquals(4, tile15.number)
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

    @Test
    fun testLevelUp() {
        GameStateHolder.levelUp()
        assertEquals(2, GameStateHolder.level.value)
    }

    @Test
    fun testSaveNewBoard() {
        val boardData = BoardData(4)
        val tile = TileData(color = Color.Red, shape = ShapeEnum.CIRCLE, number = 1, chosenForPlay = true, played = false)
        boardData.tiles = listOf(tile)
        GameStateHolder.saveNewBoard(boardData)
        assertEquals(boardData, GameStateHolder.boardDataState.value)
    }

}
