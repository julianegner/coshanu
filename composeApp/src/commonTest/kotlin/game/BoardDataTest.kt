package game

import game.enums.GameMode
import kotlin.test.*

class BoardDataTest {

    private lateinit var boardData: BoardData

    @BeforeTest
    fun setUp() {
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        boardData = BoardData()
    }

    @Test
    fun testReset() {
        boardData.reset()
        assertTrue(boardData.tiles.isEmpty())
        assertNull(boardData.selected.first)
        assertNull(boardData.selected.second)
    }
}
