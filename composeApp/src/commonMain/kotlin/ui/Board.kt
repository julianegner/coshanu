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
import util.runOnMainAfter

@Composable
fun Board() {
         //    boardDataState: MutableState<BoardData>,
         //  listState: MutableState<List<TileData>>,
         //  tutorialTextState: MutableState<String>
         //  ) {
    // val boardDataState = GameStateHolder.boardDataState
    // val listState = GameStateHolder.listState
    // val tutorialTextState = GameStateHolder.tutorialTextState
    // val gameState = GameStateHolder.gameState


    // val selected = remember { mutableStateOf(boardDataState.value.selected) }
    // val gameState = remember { mutableStateOf("S") }

    Row() {
        LazyVerticalGrid(
            modifier = Modifier.size(800.dp).border(width = 1.dp, color = Color.Black),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalArrangement = Arrangement.SpaceBetween,
            columns = GridCells.Fixed(GameStateHolder.boardDataState.value.size)
        ) {
            items(GameStateHolder.listState.value.size) { index ->

                val tileData =  GameStateHolder.listState.value.get(index)
                // val tileDataState: MutableState<TileData> = remember { mutableStateOf(GameStateHolder.listState.value.get(index)) }
                card(tileData)
                // card(tileDataState, selected, played, gameState, boardDataState)
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = GameStateHolder.tutorialTextState.value
        )
    }
    if (GameStateHolder.gameState.value == GameState.RUNNING) {
        Text(GameStateHolder.gameState.value.message + " " + GameStateHolder.getRemainingTileAmount())
    } else {
        Text(GameStateHolder.gameState.value.message)
    }

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
}

fun restartGame(
    // gameState: MutableState<String>,
    // list: MutableState<List<TileData>>,
    // tiles: List<TileData>
) {
    //GameStateHolder.resetBoard()
    GameStateHolder.updateGameState(GameState.RESTART)

    val tilesForGame = GameStateHolder.listState.value
        .filter { tileData -> tileData.chosenForPlay }

    tilesForGame.forEach {
        it.played = false
        it.borderStroke = null
    }

    val board = GameStateHolder.boardDataState.value

    board.tiles = tilesForGame

    GameStateHolder.updateBoard(board)

    // list.value = tilesForGame
    // gameState.value = "RESTART"
}

fun newGame(
    // boardDataState: MutableState<BoardData>, list: MutableState<List<TileData>>
) { //gameState: MutableState<String>



    // todo this does not work
/*
    boardData.tiles.forEach {
        it.chosenForPlay = false
        it.played = false
        it.borderStroke = null
    }

    boardData.tiles = boardData.selectTilesForGame(boardData.size, boardData.tiles)
    */
    val board = BoardData()
    board.tiles = board.tiles
        .filter { tileData -> tileData.chosenForPlay }
        .shuffled()

    GameStateHolder.updateBoard(board)
    GameStateHolder.updateGameState(GameState.STARTING)
}

@Composable
private fun card(
    tileData: TileData,
    // tileDataState: MutableState<TileData>,
    // selected: MutableState<Pair<TileData?,TileData?>>,
    // played: MutableState<Boolean>,
    // gameState: MutableState<String>,
    // boardDataState: MutableState<BoardData>
) {
    val tileDataState: MutableState<TileData> = remember { mutableStateOf(tileData) }

    val played = remember { mutableStateOf(tileDataState.value.played) }
    // val selected = remember { mutableStateOf(GameStateHolder.boardDataState.value.selected) }

    val cardModifier = Modifier
        .aspectRatio(1f)
        .padding(10.dp)

    if (GameStateHolder.selected.value.first != null && GameStateHolder.selected.value.second != null) {
        if (GameStateHolder.selected.value.first!!.same(tileDataState.value) || GameStateHolder.selected.value.second!!.same(tileDataState.value)) {
            played.value = true
            runOnMainAfter(200L) {
                GameStateHolder.selected.value = Pair(null, null)
            }
        }
    }

    if (GameStateHolder.gameState.value == GameState.RESTART) {
        played.value = false
    }
    // if (gameState.value == "RESTART") {
    //     played.value = false
    // }

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
