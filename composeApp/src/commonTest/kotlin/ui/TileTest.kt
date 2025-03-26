package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import game.GameStateHolder
import game.TileData
import game.enums.GameMode
import game.enums.GameState
import game.enums.ShapeEnum
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TileTest {

    @BeforeTest
    fun setUp() {
        GameStateHolder.resetBoard()
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        GameStateHolder.gameState.value = GameState.RUNNING
    }

    @Test
    fun testSelectFirstTile() {
        val tileData = TileData(Color.Red, 1, ShapeEnum.CIRCLE, false, false)
        val tileDataState = mutableStateOf(tileData)
        val cardBorderState = mutableStateOf<BorderStroke?>(null)

        tileSelected(tileDataState, cardBorderState)

        assertEquals(tileData, GameStateHolder.selected.value.first)
        assertNull(GameStateHolder.selected.value.second)
    }

    @Test
    fun testDeselectFirstTile() {
        val tileData = TileData(Color.Red, 1, ShapeEnum.CIRCLE, false, false)
        val tileDataState = mutableStateOf(tileData)
        val cardBorderState = mutableStateOf<BorderStroke?>(null)

        tileSelected(tileDataState, cardBorderState)
        tileSelected(tileDataState, cardBorderState)

        assertNull(GameStateHolder.selected.value.first)
        assertNull(GameStateHolder.selected.value.second)
        assertNull(tileDataState.value.borderStroke)
    }

    val greenBorderStroke = BorderStroke(5.dp, Color.Green)
    val redBorderStroke = BorderStroke(5.dp, Color.Red)

    @Test
    fun testSelectMatchingSecondTile() {

        val firstTile = TileData(Color.Red, 1, ShapeEnum.CIRCLE, false, false)
        val secondTile = TileData(Color.Red, 2, ShapeEnum.CIRCLE, false, false)
        val firstTileState = mutableStateOf(firstTile)
        val secondTileState = mutableStateOf(secondTile)
        val cardBorderState = mutableStateOf<BorderStroke?>(null)

        // here we should have all the tiles in the game
        GameStateHolder.listState.value = listOf(firstTile, secondTile)

        tileSelected(firstTileState, cardBorderState)
        tileSelected(secondTileState, cardBorderState)

        assertTrue(firstTile.played)
        assertTrue(secondTile.played)
        assertEquals(greenBorderStroke, firstTileState.value.borderStroke)
        assertNull(secondTileState.value.borderStroke)
    }

    @Test
    fun testSelectNonMatchingSecondTile() {

        val firstTile = TileData(Color.Red, 1, ShapeEnum.CIRCLE, false, false)
        val secondTile = TileData(Color.Blue, 2, ShapeEnum.SQUARE, false, false)
        val firstTileState = mutableStateOf(firstTile)
        val secondTileState = mutableStateOf(secondTile)
        val cardBorderState = mutableStateOf<BorderStroke?>(null)

        tileSelected(firstTileState, cardBorderState)
        tileSelected(secondTileState, cardBorderState)

        assertEquals(greenBorderStroke, firstTileState.value.borderStroke)
        assertEquals(redBorderStroke, secondTileState.value.borderStroke)
    }
}
