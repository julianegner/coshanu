package game

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import game.enums.GameMode
import game.enums.ShapeEnum
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TileDataTest {

    companion object {
        val redTriangle4 = TileData(
            color = Color.Red, shape = ShapeEnum.TRIANGLE, number = 4,
            chosenForPlay = true,
            played = false
        )

        val redTriangle5 = TileData(
            color = Color.Red, shape = ShapeEnum.TRIANGLE, number = 5,
            chosenForPlay = true,
            played = false
        )

        val greenCircle8 = TileData(
            color = Color.Green, shape = ShapeEnum.CIRCLE, number = 8,
            chosenForPlay = true,
            played = false
        )

        val redPentagon6 = TileData(
            color = Color.Red, shape = ShapeEnum.PENTAGON, number = 6,
            chosenForPlay = true,
            played = false
        )

        val greenOktagon4 = TileData(
            color = Color.Green, shape = ShapeEnum.OCTAGON, number = 4,
            chosenForPlay = true,
            played = false
        )

        val greyTriangle5 = TileData(
            color = Color.Gray, shape = ShapeEnum.TRIANGLE, number = 5,
            chosenForPlay = true,
            played = false
        )
    }

    // todo get to know how to test composables
    // -> for JVM only: https://markonovakovic.medium.com/compose-multiplatform-ui-tests-d59b398bb984
    // todo change Locale to german to make sure this test works every time
    // @Composable
    // @Test
    // fun shouldReturnCorrectTutorialStringsForGerman() {
    //     assertEquals("de", Locale.current.language)
    //     assertEquals("das rote Dreieck mit der Nummer 4", redTriangle4.tutorialString())
    //     assertEquals("den grünen Kreis mit der Nummer 8", greenCircle8.tutorialString())
    // }

    // todo change Locale to english to make sure this test works every time
    // @Test
    // fun shouldReturnCorrectTutorialStringsForEnglish() {
    //     assertEquals("en", Locale.current.language)
    //     assertEquals("the red Triangle with Number 4", redTriangle4.tutorialString())
    //     assertEquals("the green Circle with Number 8", greenCircle8.tutorialString())
    // }

    @Test
    fun shouldReturnTrueForSame() {
        val redTriangle4Copy = TileData(
            color = Color.Red, shape = ShapeEnum.TRIANGLE, number = 4,
            chosenForPlay = false,
            played = false
        )
        assertEquals(true, redTriangle4.same(redTriangle4Copy))
    }

    @Test
    fun shouldReturnFalseForDifferent() {
        assertEquals(false, redTriangle4.same(greenCircle8))
    }

    @Test
    fun shouldMatchOneCorrectly() {
        GameStateHolder.gameMode.value = GameMode.SINGLE_ELEMENT
        assertTrue(redTriangle4.match(redPentagon6))
        assertTrue(redTriangle4.match(greenOktagon4))
        assertTrue(redTriangle4.match(greyTriangle5))

        assertFalse(redTriangle4.match(greenCircle8))
    }

    @Test
    fun shouldMatchTwoCorrectly() {
        GameStateHolder.gameMode.value = GameMode.TWO_ELEMENTS
        assertFalse(redTriangle4.match(redPentagon6))
        assertFalse(redTriangle4.match(greenOktagon4))
        assertFalse(redTriangle4.match(greyTriangle5))
        assertFalse(redTriangle4.match(greenCircle8))

        assertTrue(redTriangle4.match(redTriangle5))
        assertTrue(redTriangle5.match(greyTriangle5))
    }
}
