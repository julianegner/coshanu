package util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
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

    @Test
    fun test2() {
        assertEquals("de", Locale.current.language)

        // assertEquals("Blau", Color.Blue.toName())
    }

    /*
    // todo test correct translation from Res
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun myTest() = runComposeUiTest {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))


        // Declares a mock UI to demonstrate API calls
        //
        // Replace with your own declarations to test the code of your project
        setContent {
            Text(text = Color.Blue.toName(),
                modifier = Modifier.testTag("blue")
                )
        }

        // Tests the declared UI with assertions and actions of the Compose Multiplatform testing API
        onNodeWithTag("blue").assertTextEquals("Blau")
    }
     */
}
