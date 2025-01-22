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

    // todo show tutorial text step by step


    MaterialTheme {


        // todo change board on level change
        // or put a menu page above...

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(vertical = 20.dp),
                text = "CoShaNu - Color, Shape, Number",
                fontSize = TextUnit(2f, TextUnitType.Em)
            )
            // todo display main menu OR game

            Menu()

            T()
            Text(getPlatform().name, modifier = Modifier.padding(vertical = 5.dp))
        }
    }
}

@Composable
fun T() {

    GameStateHolder.updateBoard(BoardData(1))

    if (GameStateHolder.level.value !== null) {
        GameStateHolder.resetBoard()
        LevelGenerator().generateLevel(GameStateHolder.level.value!!)
        Board()
    }
}

@Composable
fun Menu() {

    Text("Choose a Level:")
    Row {
        Button(
            onClick = {
                GameStateHolder.updateLevel(0)
                GameStateHolder.updateTutorialText(tutorialPart1)
            }) { Text("Tutorial") }
        (1..3).forEach { i ->
            Button(
                onClick = {
                    GameStateHolder.updateLevel(i)
                }) { Text(i.toString()) }
        }
    }
}
