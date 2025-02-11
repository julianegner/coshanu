package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.dark_mode
import coshanu.composeapp.generated.resources.light_mode
import game.GameStateHolder
import org.jetbrains.compose.resources.stringResource

@Composable
fun DarkModeSwitch() {
    Row() {
        Switch(
            checked = GameStateHolder.darkModeState.value,
            onCheckedChange = {
                GameStateHolder.darkModeState.value = it
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.DarkGray,
                checkedTrackColor = Color.LightGray,
                uncheckedThumbColor = Color.DarkGray,
                uncheckedTrackColor = Color.LightGray
            )
        )
        Text(
            text = if (GameStateHolder.darkModeState.value) {
                stringResource(Res.string.dark_mode)
            } else {
                stringResource(Res.string.light_mode)
            },
            modifier = Modifier.padding(top = 20.dp))
    }
}
