package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import game.enums.GameState
import game.GameStateHolder
import game.TileData
import game.same
import util.runOnMainAfter

@Composable
fun TileCard(
    tileData: TileData
) {
    val tileDataState: MutableState<TileData> = remember { mutableStateOf(tileData) }

    val played = remember { mutableStateOf(tileDataState.value.played) }

    if (GameStateHolder.selected.value.first != null && GameStateHolder.selected.value.second != null) {
        if (GameStateHolder.selected.value.first!!.same(tileDataState.value) || GameStateHolder.selected.value.second!!.same(tileDataState.value)) {
            played.value = true

            runOnMainAfter(200L) {
                GameStateHolder.resetSelected()
            }
        }
    }

    if (GameStateHolder.gameState.value == GameState.RESTART) {
        played.value = false
    }

    val cardModifier = Modifier
        .aspectRatio(1f)
        .padding(10.dp)

    if (!played.value) {
        val cardBorderState = remember { mutableStateOf(tileDataState.value.borderStroke) }

        Card(
            modifier = cardModifier
                .clickable(onClick = { tileSelected(tileDataState, cardBorderState) }),
            backgroundColor = if (
                GameStateHolder.tutorial.isTutorial() &&
                GameStateHolder.tutorial.isAllowedTile(tileDataState.value)
            ) {
                if (GameStateHolder.darkModeState.value) { Color.LightGray } else { Color.DarkGray }
            } else {
                if (GameStateHolder.darkModeState.value) { Color.DarkGray } else { Color.LightGray }
            },
            border = cardBorderState.value,
        ) {
            Tile(tileDataState, cardBorderState)
        }
    } else {
        Card(
            modifier = cardModifier,
            elevation = 0.dp) {}
    }
}
