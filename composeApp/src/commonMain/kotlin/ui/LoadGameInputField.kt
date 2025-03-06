package ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import game.GameStateHolder

@Composable
fun LoadGameInputField() {
    val textState = remember { mutableStateOf("") }

    OutlinedTextField(
        value = textState.value,
        onValueChange = { textState.value = it },
        label = { Text("Enter text") },
        modifier = Modifier.fillMaxWidth()
    )

    Button(
        enabled = textState.value.isNotEmpty(),
        onClick = { GameStateHolder.loadGame(textState.value) }) {
        Text("Load Game")
    }
}
