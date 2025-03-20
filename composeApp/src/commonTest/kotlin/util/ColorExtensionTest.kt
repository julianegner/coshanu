package util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals

class ColorExtensionTest {

    companion object {
        val colorStrings = listOf(
            "blue",
            "green",
            "yellow",
            "light_gray",
            "dark_gray",
            "magenta",
            "cyan",
            "unknown"
        )
    }

    @Test
    fun test() {
        colorStrings.forEach { colorString ->
            assertEquals(colorString, stringToColor(colorString).toSaveName())
        }
    }
}
