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
import de.julianegner.multiplatformTooltip.TooltipWrapper
import game.enums.GameState
import game.GameStateHolder
import game.TileData
import game.same
import game.tooltipText
import isPlatformAndroid
import util.modeDependantColor
import util.onClick
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

    val boardSize = GameStateHolder.boardDataState.value.size

    val cardModifier = Modifier
        .aspectRatio(1f)
        .padding(
            if (isPlatformAndroid)
                // set padding between the cards by the board size
                when (boardSize) {
                    2 -> 10.dp
                    4 -> 8.dp
                    8 -> 5.dp
                    12 -> 2.dp
                    else -> 0.dp
                }
            else
                10.dp)

    if (!played.value) {
        val cardBorderState = remember { mutableStateOf(tileDataState.value.borderStroke) }
        val isNextTutorialTile = GameStateHolder.tutorial.isTutorial() &&
                GameStateHolder.tutorial.isAllowedTile(tileDataState.value)

        TooltipWrapper(
            if (UiStateHolder.cardTooltipActive.value)
                tileDataState.value.tooltipText()
            else
                null // no tooltip at all
        ) {
            Card(
                // only clickable if not tutorial or tutorial and allowed tile
                modifier = if (GameStateHolder.isGameState(GameState.RUNNING) &&
                    (!GameStateHolder.tutorial.isTutorial() || GameStateHolder.tutorial.isAllowedTile(tileDataState.value))
                ) {
                    cardModifier
                        .onClick(onClick = { tileSelected(tileDataState, cardBorderState) })
                } else {
                    cardModifier
                },
                backgroundColor = if (isNextTutorialTile) {
                    Color.DarkGray.modeDependantColor
                } else {
                    Color.LightGray.modeDependantColor
                },
                border = cardBorderState.value,
            ) {
                Tile(tileDataState, cardBorderState, isNextTutorialTile = isNextTutorialTile)
            }
        }
    } else {
        Card(
            backgroundColor = Color.Transparent,
            modifier = cardModifier,
            elevation = 0.dp) {}
    }
}
