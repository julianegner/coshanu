package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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

val boardData = BoardData()

@Composable
fun Board() {

    val list = remember {
        mutableStateOf(
        boardData.tiles
            .filter { tileData -> tileData.chosenForPlay }
            .shuffled()
        )
    }

    val selected = remember { mutableStateOf(boardData.selected) }
    val gameText = remember { mutableStateOf("started") }


    LazyVerticalGrid(
        modifier = Modifier.size(800.dp).border(width = 1.dp, color = Color.Black),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalArrangement = Arrangement.SpaceBetween,
        columns = GridCells.Fixed(boardData.size)
    ) {
        items(list.value.size) { index ->
            val tileDataState: MutableState<TileData> = remember { mutableStateOf(list.value.get(index)) }
            val played = remember { mutableStateOf(tileDataState.value.played) }
            card(tileDataState, selected, played, gameText)
        }
    }

    Text(gameText.value)
}

@Composable
private fun card(
    tileDataState: MutableState<TileData>,
    selected: MutableState<Pair<TileData?,TileData?>>,
    played: MutableState<Boolean>,
    gameText: MutableState<String>
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


    if (!played.value) {
    // todo react on click on card and symbol
        val cardBorderState = remember { mutableStateOf(tileDataState.value.borderStroke) }

        Card(
            modifier = cardModifier
                .clickable(onClick = { tileSelected(tileDataState, selected, cardBorderState, gameText) }),
            backgroundColor = Color.LightGray,
            border = cardBorderState.value

        ) {
            Tile(tileDataState, selected, cardBorderState, gameText)
        }
    } else {
        Card(
            modifier = cardModifier,
            elevation = 0.dp) {}
    }
}
