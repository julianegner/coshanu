import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import game.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.Board
import ui.DarkModeSwitch
import ui.GameSymbol

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    // if (isSystemInDarkTheme()) {
    //     GameStateHolder.darkModeState.value = true
    // }
    MaterialTheme(colors = if (GameStateHolder.darkModeState.value) darkColors() else lightColors()) {
        Scaffold { // Scaffold is needed for the dark mode switch to work
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {

                GameSymbol()
                DarkModeSwitch()

                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.padding(vertical = 20.dp),
                        text = "CoShaNu - Color, Shape, Number",
                        fontSize = TextUnit(2f, TextUnitType.Em)
                    )

                    if (GameStateHolder.isGameState(GameState.WON) || GameStateHolder.isGameState(GameState.LOST)) {
                        Text(
                            modifier = Modifier.padding(vertical = 20.dp),
                            text = GameStateHolder.gameStateText.value,
                            fontSize = TextUnit(2f, TextUnitType.Em)
                        )
                    } else {
                        Text(
                            GameStateHolder.gameStateText.value,
                            fontSize = TextUnit(1.5f, TextUnitType.Em)
                        )
                    }

                    if (!(GameStateHolder.isGameState(GameState.RUNNING) || GameStateHolder.isGameState(GameState.LOST))) {
                        Menu()
                    }

                    // Text(getPlatform().name, modifier = Modifier.padding(vertical = 5.dp))


                    // if level is chosen, display the board
                    if (GameStateHolder.level.value != null) {
                        Board()
                    }
                }
            }
        }
    }
}

@Composable
fun Menu() {

    Text("Choose a Level:")

    Row {
        Text(GameMode.SINGLE_ELEMENT.message, modifier = Modifier.padding(5.dp).width(100.dp))
        Button(
            onClick = {
                GameStateHolder.changeLevel(0)
            }) { Text("Tutorial") }
        (1..3).forEach { i ->
            Button(
                onClick = {
                    GameStateHolder.changeLevel(i)
                }) { Text(i.toString()) }
        }
    }

    val LightBlue = Color(0xCC3333FF)

    Row {
        Text(GameMode.TWO_ELEMENTS.message, modifier = Modifier.padding(5.dp).width(100.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = if (GameStateHolder.darkModeState.value) LightBlue else Color.Blue
            ),
            onClick = {
                GameStateHolder.changeLevel(10)
            }) { Text("Tutorial") }
        (11..13).forEach { i ->
            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    backgroundColor = if (GameStateHolder.darkModeState.value) LightBlue else Color.Blue
                ),
                onClick = {
                    GameStateHolder.changeLevel(i)
                }) { Text(i.toString()) }
        }
    }
}
