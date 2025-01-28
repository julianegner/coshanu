import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import game.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.Board
import ui.GameSymbol

const val tutorialPart1 = "Welcome to the Tutorial!\nPlease click on the upper left green Triangle with Number 3."
const val tutorialPart2 = "you see that the Frame of that Tile is green now.\n" +
        "Now click on the lower right blue Circle with number 1\n"
const val tutorialPart3 =
        "you see that the frame of that tile is red for a moment.\n" +
        "That is because the Tiles do not fit together.\n" +
        "For Tiles to fit, the must have the same Color, Shape or Number, therefore the Name CoShaNu.\n" +
        "Now click on the lower left blue triangle with the Number 3.\n"
const val tutorialPart3b =
        "Both Tiles disappear, because this fits.\n" +
        "Yes, this game was lost.\n" +
        "But no problem, just click on 'restart Game'\n"
const val tutorialPart4 = "Your goal to win is to remove all Tiles. But be careful to not remove the wrong ones, as shown before.\n" +
        "Now choose the upper left green Triangle with Number 3 and then the yellow Triangle with the Number 3.\n"
const val tutorialPart5 =
        "These Tiles are played and only the Blue Tiles are there.\n" +
        "play both and win!"

const val wholeTutorialText = tutorialPart1 + tutorialPart2 + tutorialPart3 + tutorialPart4 + tutorialPart5

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {

    MaterialTheme {
        Row(modifier = Modifier.padding(horizontal = 20.dp)) {
            GameSymbol()

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
                    Text(GameStateHolder.gameStateText.value, fontSize = TextUnit(1.5f, TextUnitType.Em))
                }

                if (!(GameStateHolder.isGameState(GameState.RUNNING) || GameStateHolder.isGameState(GameState.LOST) || GameStateHolder.isGameState(GameState.WON))) {
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

@Composable
fun Menu() {

    Text("Choose a Level:")
    Text(GameMode.SINGLE_ELEMENT.message)
    Row {
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
    Text(GameMode.TWO_ELEMENTS.message)
    Row {
        Button(
            onClick = {
                GameStateHolder.changeLevel(10)
            }) { Text("Tutorial") }
        (11..13).forEach { i ->
            Button(
                onClick = {
                    GameStateHolder.changeLevel(i)
                }) { Text(i.toString()) }
        }
    }
}
