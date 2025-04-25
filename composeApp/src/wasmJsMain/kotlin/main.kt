import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    document.getElementById("loader-container")?.remove()
    CanvasBasedWindow(canvasElementId = "ComposeTarget") { App() }
}
