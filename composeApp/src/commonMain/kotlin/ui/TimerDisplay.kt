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
import game.GameStateHolder
import ui.UiStateHolder.standardTextSize

@Composable
fun TimerDisplay(modifier: Modifier = Modifier) {
    Box( modifier = modifier
        .defaultMinSize(minWidth = 100.dp, minHeight = 40.dp)
        .border(BorderStroke(2.dp, Color.Green)),
        contentAlignment = Alignment.Center
    ) {

        Text(
            modifier = Modifier.padding(10.dp),
            text = GameStateHolder.timer.durationState().value.toString(),
            fontSize = standardTextSize.value,
            )
    }
}
