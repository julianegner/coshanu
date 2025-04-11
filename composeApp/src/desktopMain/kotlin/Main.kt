// import App
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.icon
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    val state = rememberWindowState(placement = WindowPlacement.Maximized)
    Window(
        onCloseRequest = ::exitApplication,
        title = "CoShaNu",
        state = state,
        icon = painterResource(Res.drawable.icon)
    ) {
        App()
    }
}
