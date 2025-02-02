package ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import game.GameStateHolder

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
        val text = if (GameStateHolder.darkModeState.value) { "Dark Mode" } else { "Light Mode" }
        Text(text, modifier = Modifier.padding(top = 20.dp))
    }
}
