import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import game.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.Board
import ui.Polygon
import kotlin.concurrent.Volatile

// todo remove DataHolder and use GameStateHolder
// class DataHolder private constructor() {
//
//     companion object {
//         val instance:DataHolder by lazy {
//             DataHolder()
//         }
//     }
//
//     val boardDataState: MutableState<BoardData> = mutableStateOf(BoardData(1))
//     val listState: MutableState<List<TileData>> = mutableStateOf(boardDataState.value.tiles)
// }

const val wholeTutorialText =
    "Welcome to the Tutorial!\nPlease click on the upper left green Triangle with Number 3." + "\n" +
            "you see that the Frame of that Tile is green now.\n" +
            "Now click on the lower right blue Circle with number 1\n" +
            "you see that the frame of that tile is red for a moment.\n" +
            "That is because the Tiles do not fit together.\n\n" +
            "For Tiles to fit, the must have the same Color, Shape or Number, therefore the Name CoShaNu.\n" +
            "Now click on the lower left blue triangle with the Number 3.\n" +
            "Both Tiles disappear, because this fits.\n" +
            "Yes, this game was lost.\n\n" +
            "But no problem, just click on 'restart Game'\n\n" +
            "Your goal to win is to remove all Tiles. But be careful to not remove the wrong ones, as shown yet.\n" +
            "Now choose the upper left green Triangle with Number 3 and then the yellow Triangle with the Number 3.\n" +
            "These Tiles are played and only the Blue Tiles are there.\n\n" +
            "play both and win!"

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {

    // val level: MutableState<Int?> = remember { mutableStateOf(null) }

    val boardDataState = GameStateHolder.boardDataState
    val listState = GameStateHolder.listState
    val tutorialTextState = GameStateHolder.tutorialTextState
    val gameState = GameStateHolder.gameState
    val level = GameStateHolder.level


    /*
    // todo show tutorial text step by step
    val tutorialTextState = mutableStateOf(
        "Welcome to the Tutorial!\nPlease click on the upper left green Triangle with Number 3." + "\n" +
                "you see that the Frame of that Tile is green now.\n" +
        "Now click on the lower right blue Circle with number 1\n" +
        "you see that the frame of that tile is red for a moment.\n" +
        "That is because the Tiles do not fit together.\n\n" +
        "For Tiles to fit, the must have the same Color, Shape or Number, therefore the Name CoShaNu.\n" +
        "Now click on the lower left blue triangle with the Number 3.\n" +
        "Both Tiles disappear, because this fits.\n" +
                "Yes, this game was lost.\n\n" +
                "But no problem, just click on 'restart Game'\n\n" +
        "Your goal to win is to remove all Tiles. But be careful to not remove the wrong ones, as shown yet.\n" +
        "Now choose the upper left green Triangle with Number 3 and then the yellow Triangle with the Number 3.\n" +
        "These Tiles are played and only the Blue Tiles are there.\n\n" +
        "play both and win!"
    )
     */

    MaterialTheme {


        // todo change board on level change
        // or put a menu page above...
        /*
        Row {
            Button(
                onClick = {
                    LevelGenerator().generateLevel(0, boardDataState, listState)
                }) { Text("0") }
            Button(
                onClick = {
                    LevelGenerator().generateLevel(1, boardDataState, listState)
                }) { Text("1") }
            Button(
                onClick = {
                    LevelGenerator().generateLevel(2, boardDataState, listState)
                }) { Text("2") }
            Button(
                onClick = {
                    LevelGenerator().generateLevel(3, boardDataState, listState)
                }) { Text("3") }
        }
         */

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(vertical = 20.dp),
                text = "CoShaNu - Color, Shape, Number",
                fontSize = TextUnit(2f, TextUnitType.Em)
            )
            // todo display main menu OR game

            Menu(level)

            T(level)
            Text(getPlatform().name, modifier = Modifier.padding(vertical = 5.dp))
        }
        /*
        var showContent by remember { mutableStateOf(false) }
        val greeting = remember { Greeting().greet() }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource("compose-multiplatform.xml"), null)
                    Text("Compose: $greeting")
                }
            }
        }
         */
    }
}

@Composable
fun T(level: MutableState<Int?>) {

    GameStateHolder.updateBoard(BoardData(1))

    // val boardDataState: MutableState<BoardData> = mutableStateOf(BoardData(1))
    // val listState = remember {
    //     mutableStateOf(boardDataState.value.tiles)
    // }

    // val level = remember { mutableStateOf(levelA.value) }

    if (level.value !== null) {
        GameStateHolder.resetBoard()
        // boardDataState.value.reset()
        // listState.value = listOf()
        // GameStateHolder.updateBoard(boardDataState.value)

        // DataHolder.instance.boardDataState.value = boardDataState.value
        // DataHolder.instance.listState.value = listState.value

        LevelGenerator().generateLevel(level.value!!)

        // LevelGenerator().generateLevel(2, boardDataState, listState)

        // DataHolder.instance.boardDataState.value = boardDataState.value
        // GameStateHolder.updateBoard(boardDataState.value)
        // DataHolder.instance.listState.value = listState.value

        // todo create a service for all the game data to change while playing

        // val tutorialTextState = mutableStateOf("")

        Board()
    }
}

@Composable
fun Menu(level: MutableState<Int?>) {

    Text("Choose a Level:")
    Row {
        Button(
            onClick = {
                //level.value = 0
                GameStateHolder.updateLevel(0)
                GameStateHolder.updateTutorialText(wholeTutorialText)
                //LevelGenerator().generateLevel(0, boardDataState, listState)
            }) { Text("0") }
        Button(
            onClick = {
                // level.value = 1
                GameStateHolder.updateLevel(1)
                //LevelGenerator().generateLevel(1, boardDataState, listState)
            }) { Text("1") }
        Button(
            onClick = {
                // level.value = 2
                GameStateHolder.updateLevel(2)
                //LevelGenerator().generateLevel(2, boardDataState, listState)
            }) { Text("2") }
        Button(
            onClick = {
                //level.value = 3
                GameStateHolder.updateLevel(3)
                //LevelGenerator().generateLevel(3, boardDataState, listState)
            }) { Text("3") }
    }
}
