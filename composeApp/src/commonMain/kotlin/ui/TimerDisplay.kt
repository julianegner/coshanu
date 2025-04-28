package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.time_is_up
import game.GameStateHolder
import game.GameStateHolder.timer
import game.GameStateHolder.updateGameState
import game.enums.GameState
import ui.UiStateHolder.standardTextSize
import kotlin.time.Duration

@Composable
fun TimerDisplay(modifier: Modifier = Modifier) {
    Box( modifier = modifier
        .defaultMinSize(minWidth = 100.dp, minHeight = 40.dp)
        .border(BorderStroke(2.dp, Color.Green)),
        contentAlignment = Alignment.Center
    ) {

        Text(
            modifier = Modifier.padding(10.dp),
            text = timer.durationState().value.toString(),
            fontSize = standardTextSize.value,
            )
    }
}

@Composable
fun CountdownTimerDisplay(modifier: Modifier = Modifier) {
    if (GameStateHolder.totalAllowedTime.value - timer.durationState().value <= Duration.ZERO) {
        updateGameState(GameState.LOST)
        timer.stopTimer()
    }

    Box( modifier = modifier
        .defaultMinSize(minWidth = 100.dp, minHeight = 40.dp)
        .border(BorderStroke(2.dp, Color.Red)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = (GameStateHolder.totalAllowedTime.value - timer.durationState().value)
                .toString(),
            fontSize = standardTextSize.value,
        )
    }
}
