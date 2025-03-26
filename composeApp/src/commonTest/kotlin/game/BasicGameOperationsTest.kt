package game

import game.enums.GameMode
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import game.enums.GameState

class BasicGameOperationsTest {

    @BeforeTest
    fun setUp() {
        GameStateHolder.resetBoard()
    }

    @Test
    fun testRestartGame() {
        restartGame()
        assertEquals(GameState.RESTART, GameStateHolder.gameState.value)
        assertTrue(GameStateHolder.listState.value.all { !it.played })
    }

    @Test
    fun testEndGame() {
        endGame()
        assertEquals(GameState.LEVEL_CHANGE, GameStateHolder.gameState.value)
        assertTrue(GameStateHolder.listState.value.isEmpty())
    }

    @Test
    fun testNewGame() {
        GameStateHolder.level.value = 1
        newGame()
        assertEquals(GameState.RUNNING, GameStateHolder.gameState.value)
        assertTrue(GameStateHolder.listState.value.isNotEmpty())
    }

    @Test
    fun testSetGameMode() {
        setGameMode(1)
        assertEquals(GameMode.SINGLE_ELEMENT, GameStateHolder.gameMode.value)

        setGameMode(10)
        assertEquals(GameMode.TWO_ELEMENTS, GameStateHolder.gameMode.value)
    }

    // @Test
    // fun testSaveGame() {
    //     // This test would need to check the clipboard content or file content
    //     // Assuming a function `getClipboardContent` exists for testing purposes
    //     saveGame()
    //     val expectedContent = """saveGame
    //         level: ${GameStateHolder.level.value}
    //         rem. tiles: ${GameStateHolder.remainingTileAmount.value}
    //         timer: ${GameStateHolder.timer.durationState().value}
    //         board size: ${GameStateHolder.boardDataState.value.size}
    //         board: ${GameStateHolder.boardDataState.value.tiles.map { it.toSaveString() }}
    //         )
    //         """
    //     assertEquals(expectedContent, getClipboardContent())
    // }

    @Test
    fun testLoadGame() {
        // This test would need to simulate loading game data
        loadGame()
        assertEquals(GameState.LOAD_GAME, GameStateHolder.gameState.value)
    }
}
