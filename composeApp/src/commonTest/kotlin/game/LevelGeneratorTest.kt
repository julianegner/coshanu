package game

import androidx.compose.ui.graphics.Color
import game.enums.GameMode
import game.enums.ShapeEnum
import kotlin.test.*

class LevelGeneratorTest {

    val levelGenerator = LevelGenerator()

    // todo add tests to check if the generated level is solvable

    @Test
    fun level0() {
        GameStateHolder.level.value = 0
        levelGenerator.generateLevel(0)
        assertEquals(0, GameStateHolder.level.value)
        assertEquals(GameMode.SINGLE_ELEMENT,GameStateHolder.gameMode.value)
        assertEquals(2, GameStateHolder.boardDataState.value.size)
        assertEquals(4, GameStateHolder.boardDataState.value.tiles.size)
        assertTrue { GameStateHolder.tutorial.isTutorial() }
        assertFalse { GameStateHolder.lostGame() }
    }
    @Test
    fun level1() {
        GameStateHolder.level.value = 1
        levelGenerator.generateLevel(1)
        assertEquals(1, GameStateHolder.level.value)
        assertEquals(GameMode.SINGLE_ELEMENT,GameStateHolder.gameMode.value)
        assertEquals(4, GameStateHolder.boardDataState.value.size)
        assertEquals(16, GameStateHolder.boardDataState.value.tiles.size)
        assertFalse { GameStateHolder.tutorial.isTutorial() }
        assertFalse { GameStateHolder.lostGame() }
    }
    @Test
    fun level2() {
        GameStateHolder.level.value = 2
        levelGenerator.generateLevel(2)
        assertEquals(2, GameStateHolder.level.value)
        assertEquals(GameMode.SINGLE_ELEMENT,GameStateHolder.gameMode.value)
        assertEquals(4, GameStateHolder.boardDataState.value.size)
        assertEquals(16, GameStateHolder.boardDataState.value.tiles.size)
        assertFalse { GameStateHolder.tutorial.isTutorial() }
        assertFalse { GameStateHolder.lostGame() }
    }
    @Test
    fun level3() {
        GameStateHolder.level.value = 3
        levelGenerator.generateLevel(3)
        assertEquals(3, GameStateHolder.level.value)
        assertEquals(GameMode.SINGLE_ELEMENT,GameStateHolder.gameMode.value)
        assertEquals(8, GameStateHolder.boardDataState.value.size)
        assertEquals(64, GameStateHolder.boardDataState.value.tiles.size)
        assertFalse { GameStateHolder.tutorial.isTutorial() }
        assertFalse { GameStateHolder.lostGame() }
    }

    @Test
    fun level10() {
        GameStateHolder.level.value = 10
        levelGenerator.generateLevel(10)
        assertEquals(10, GameStateHolder.level.value)
        assertEquals(GameMode.TWO_ELEMENTS,GameStateHolder.gameMode.value)
        assertEquals(4, GameStateHolder.boardDataState.value.size)
        assertEquals(16, GameStateHolder.boardDataState.value.tiles.size)
        assertTrue { GameStateHolder.tutorial.isTutorial() }
        assertFalse { GameStateHolder.lostGame() }
    }
    @Test
    fun level11() {
        GameStateHolder.level.value = 11
        levelGenerator.generateLevel(11)
        assertEquals(11, GameStateHolder.level.value)
        assertEquals(GameMode.TWO_ELEMENTS,GameStateHolder.gameMode.value)
        assertEquals(4, GameStateHolder.boardDataState.value.size)
        assertEquals(16, GameStateHolder.boardDataState.value.tiles.size)
        assertFalse { GameStateHolder.tutorial.isTutorial() }
        assertFalse { GameStateHolder.lostGame() }
    }
    @Test
    fun level12() {
        GameStateHolder.level.value = 12
        levelGenerator.generateLevel(12)
        assertEquals(12, GameStateHolder.level.value)
        assertEquals(GameMode.TWO_ELEMENTS,GameStateHolder.gameMode.value)
        assertEquals(4, GameStateHolder.boardDataState.value.size)
        assertEquals(16, GameStateHolder.boardDataState.value.tiles.size)
        assertFalse { GameStateHolder.tutorial.isTutorial() }
        assertFalse { GameStateHolder.lostGame() }
    }
    @Test
    fun level13() {
        GameStateHolder.level.value = 13
        levelGenerator.generateLevel(13)
        assertEquals(13, GameStateHolder.level.value)
        assertEquals(GameMode.TWO_ELEMENTS,GameStateHolder.gameMode.value)
        assertEquals(8, GameStateHolder.boardDataState.value.size)
        assertEquals(64, GameStateHolder.boardDataState.value.tiles.size)
        assertFalse { GameStateHolder.tutorial.isTutorial() }
        assertFalse { GameStateHolder.lostGame() }
    }

    @Test
    fun testBoardInitialization() {
        levelGenerator.generateLevel(1)
        val boardData = GameStateHolder.boardDataState.value
        assertEquals(16, boardData.tiles.size)
        assertTrue(boardData.tiles.all { it.color in boardData.colors })
        assertTrue(boardData.tiles.all { it.shape in ShapeEnum.values() })
    }

    @Test
    fun testCreateBoard() {
        val newBoard = levelGenerator.createBoard(4, 4, listOf(Color.Blue, Color.Green))
        assertEquals(16, newBoard.tiles.size) // because 4 numbers, 2 colors, 6 shapes
        assertTrue(newBoard.tiles.all { it.color in listOf(Color.Blue, Color.Green) })
    }

    @Test
    fun testSelectTilesForGameSingleElement() {
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        val selectedTiles = levelGenerator.selectTilesForGame(4, 6, listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red))
        assertEquals(16, selectedTiles.size, "Expected 32 tiles, got ${selectedTiles.size}")
    }

    @Test
    fun testSelectTilesForGameTwoElements() {
        GameStateHolder.gameMode.value = GameMode.TWO_ELEMENTS
        val selectedTiles = levelGenerator.selectTilesForGame(4, 6, listOf(Color.Blue, Color.Green, Color.Yellow, Color.Red))
        assertEquals(16, selectedTiles.size, "Expected 32 tiles, got ${selectedTiles.size}")
    }
}
