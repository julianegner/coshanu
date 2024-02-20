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
import game.BoardData
import game.TileData
import game.same
import util.runOnMainAfter

@Composable
fun Board(boardDataState: MutableState<BoardData>,
          listState: MutableState<List<TileData>>,
          tutorialTextState: MutableState<String>
          ) {

    val selected = remember { mutableStateOf(boardDataState.value.selected) }
    val gameState = remember { mutableStateOf("S") }

    Row() {
        LazyVerticalGrid(
            modifier = Modifier.size(800.dp).border(width = 1.dp, color = Color.Black),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalArrangement = Arrangement.SpaceBetween,
            columns = GridCells.Fixed(boardDataState.value.size)
        ) {
            items(listState.value.size) { index ->
                val tileDataState: MutableState<TileData> = remember { mutableStateOf(listState.value.get(index)) }
                val played = remember { mutableStateOf(tileDataState.value.played) }
                card(tileDataState, selected, played, gameState, boardDataState)
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = tutorialTextState.value
        )
    }
    GameText(gameState)

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        if (gameState.value == "L") {
            Button(onClick = { restartGame(gameState, listState, boardDataState.value.tiles) }) {
                Text("restart Game")
            }
        }
        if (gameState.value == "L" || gameState.value == "W") {

            Button(onClick = { newGame(boardDataState, listState) }) {
                Text("New Game")
            }
        }
    }
}

fun restartGame(
    gameState: MutableState<String>,
    list: MutableState<List<TileData>>,
    tiles: List<TileData>
) {

    val tilesForGame = tiles
        .filter { tileData -> tileData.chosenForPlay }

    tilesForGame.forEach {
        it.played = false
        it.borderStroke = null
    }

    list.value = tilesForGame
    gameState.value = "RESTART"
}

fun newGame(boardDataState: MutableState<BoardData>, list: MutableState<List<TileData>> ) { //gameState: MutableState<String>
    // todo this does not work
/*
    boardData.tiles.forEach {
        it.chosenForPlay = false
        it.played = false
        it.borderStroke = null
    }

    boardData.tiles = boardData.selectTilesForGame(boardData.size, boardData.tiles)
    */
    boardDataState.value = BoardData()
    list.value = boardDataState.value.tiles
        .filter { tileData -> tileData.chosenForPlay }
        .shuffled()
}

@Composable
private fun GameText(gameState: MutableState<String>) {
    val game =  gameState.value
    var gameText = when (game) {
        "RESTART" -> "starting again"
        "S" -> "starting"
        "W" -> "You Won!!"
        "L" -> "Sorry, you lost!"
        else -> "running... remaining Tiles: ${game.removePrefix("R")}"
    }
    Text(gameText)
}

@Composable
private fun card(
    tileDataState: MutableState<TileData>,
    selected: MutableState<Pair<TileData?,TileData?>>,
    played: MutableState<Boolean>,
    gameState: MutableState<String>,
    boardDataState: MutableState<BoardData>
) {
    val cardModifier = Modifier
        .aspectRatio(1f)
        .padding(10.dp)

    if (selected.value.first != null && selected.value.second != null) {
        if (selected.value.first!!.same(tileDataState.value) || selected.value.second!!.same(tileDataState.value)) {
            played.value = true
            runOnMainAfter(200L) {
                selected.value = Pair(null, null)
            }
        }
    }

    if (gameState.value == "RESTART") {
        played.value = false
    }

    if (!played.value) {
        val cardBorderState = remember { mutableStateOf(tileDataState.value.borderStroke) }

        Card(
            modifier = cardModifier
                .clickable(onClick = { tileSelected(tileDataState, selected, cardBorderState, gameState, boardDataState) }),
            backgroundColor = Color.LightGray,
            border = cardBorderState.value

        ) {
            Tile(tileDataState, selected, cardBorderState, gameState, boardDataState)
        }
    } else {
        Card(
            modifier = cardModifier,
            elevation = 0.dp) {}
    }
}
