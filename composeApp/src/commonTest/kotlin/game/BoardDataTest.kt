package game

import androidx.compose.ui.graphics.Color
import game.enums.GameMode
import game.enums.ShapeEnum
import kotlin.test.*

class BoardDataTest {

    private lateinit var boardData: BoardData

    @BeforeTest
    fun setUp() {
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        boardData = BoardData()
    }

    @Test
    fun testBoardInitialization() {
        assertEquals(96, boardData.tiles.size) // because 4 numbers, 4 colors, 6 shapes
        assertEquals(16, boardData.tiles.filter { it.chosenForPlay }.size)
        assertTrue(boardData.tiles.all { it.color in boardData.colors })
        assertTrue(boardData.tiles.all { it.shape in ShapeEnum.values() })
    }

    @Test
    fun testReset() {
        boardData.reset()
        assertTrue(boardData.tiles.isEmpty())
        assertNull(boardData.selected.first)
        assertNull(boardData.selected.second)
    }

    @Test
    fun testCreateBoard() {
        val newBoard = boardData.createBoard(4, 4, listOf(Color.Blue, Color.Green))
        assertEquals(48, newBoard.size) // because 4 numbers, 2 colors, 6 shapes
        assertTrue(newBoard.all { it.color in listOf(Color.Blue, Color.Green) })
    }

    @Test
    fun testSelectTilesForGameSingleElement() {
        val selectedTiles = boardData.selectTilesForGame(4, boardData.tiles)
        assertEquals(96, selectedTiles.size, "Expected 96 tiles, got ${selectedTiles.size}")
        assertEquals(32, boardData.tiles.filter { it.chosenForPlay }.size, "Expected 32 chosen tiles, got ${boardData.tiles.filter { it.chosenForPlay }.size}")
    }

    @Test
    fun testSelectTilesForGameTwoElements() {
        GameStateHolder.gameMode.value = GameMode.TWO_ELEMENTS
        boardData = BoardData()
        val selectedTiles = boardData.selectTilesForGame(4, boardData.tiles)
        assertEquals(96, selectedTiles.size, "Expected 96 tiles, got ${selectedTiles.size}")
        assertEquals(32, boardData.tiles.filter { it.chosenForPlay }.size, "Expected 32 chosen tiles, got ${boardData.tiles.filter { it.chosenForPlay }.size}")
    }
}
