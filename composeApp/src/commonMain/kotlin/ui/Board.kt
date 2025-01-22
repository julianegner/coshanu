package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import game.*
import tutorialPart3
import tutorialPart3b
import tutorialPart4
import util.runOnMainAfter

@Composable
fun Board() {

    /*
    todo fix this:
     getRemainingTileAmount() gives 4 (incorrect), but remainingTileAmount.value gives 2 (correct)
     so something changes GameStateHolder.listState
     */
    Button(onClick = {
        println("liststate ${GameStateHolder.listState.value}")
        println("TEST: ${GameStateHolder.getRemainingTileAmount()} ${GameStateHolder.remainingTileAmount.value} ${GameStateHolder.listState.value} ") }) {
        Text("TEST")
    }

    Text(GameStateHolder.getGameStateText().value)

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        if (GameStateHolder.isGameState(GameState.LOST)) {
            Button(onClick = { restartGame() }) {
                Text("restart Game")
            }
        }
        if (GameStateHolder.isGameState(GameState.WON) || GameStateHolder.isGameState(GameState.LOST)) {
            Button(onClick = { newGame() }) {
                Text("New Game")
            }
        }
    }

    Row() {
        LazyVerticalGrid(
            modifier = Modifier.size(800.dp).border(width = 1.dp, color = Color.Black),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalArrangement = Arrangement.SpaceBetween,
            columns = GridCells.Fixed(GameStateHolder.boardDataState.value.size)
        ) {
            items(GameStateHolder.listState.value.size) { index ->
                card(GameStateHolder.listState.value.get(index))
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = GameStateHolder.tutorialTextState.value
        )
    }
}

fun restartGame(
) {
    GameStateHolder.updateGameState(GameState.RESTART)

    val tilesForGame = GameStateHolder.listState.value
        .filter { tileData -> tileData.chosenForPlay }

    tilesForGame.forEach {
        it.played = false
        it.borderStroke = null
    }

    val board = GameStateHolder.boardDataState.value

    board.tiles = tilesForGame

    GameStateHolder.updateBoard(board, GameState.RESTART)

    if (GameStateHolder.level.value == 0) {
        if (GameStateHolder.tutorialTextState.value == tutorialPart3b) {
            GameStateHolder.updateTutorialText(tutorialPart4)
        }
    }
}

fun newGame(
) {
    val board = BoardData()
    board.tiles = board.tiles
        .filter { tileData -> tileData.chosenForPlay }
        .shuffled()

    GameStateHolder.updateBoard(board, GameState.STARTING)
}

@Composable
private fun card(
    tileData: TileData) {
    val tileDataState: MutableState<TileData> = remember { mutableStateOf(tileData) }

    val played = remember { mutableStateOf(tileDataState.value.played) }

    val cardModifier = Modifier
        .aspectRatio(1f)
        .padding(10.dp)

    if (GameStateHolder.selected.value.first != null && GameStateHolder.selected.value.second != null) {
        if (GameStateHolder.selected.value.first!!.same(tileDataState.value) || GameStateHolder.selected.value.second!!.same(tileDataState.value)) {
            played.value = true
            // set played to true for current tile


            // GameStateHolder.listState.value
            //     .filter { it.same(tileDataState.value) }
            //     .first()
            //     .played = true
            runOnMainAfter(200L) {
                // GameStateHolder.playCard(tileDataState.value)
                GameStateHolder.selected.value = Pair(null, null)
            }
        }
    }

    if (GameStateHolder.gameState.value == GameState.RESTART) {
        played.value = false
    }

    if (!played.value) {
        val cardBorderState = remember { mutableStateOf(tileDataState.value.borderStroke) }

        Card(
            modifier = cardModifier
                .clickable(onClick = { tileSelected(tileDataState, cardBorderState) }),
            backgroundColor = Color.LightGray,
            border = cardBorderState.value

        ) {
            Tile(tileDataState, cardBorderState)
        }
    } else {
        Card(
            modifier = cardModifier,
            elevation = 0.dp) {}
    }
}
