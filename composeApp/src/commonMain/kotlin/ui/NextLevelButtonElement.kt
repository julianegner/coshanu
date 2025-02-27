package ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.next_level
import game.GameStateHolder
import org.jetbrains.compose.resources.stringResource
import ui.UiStateHolder.standardTextSize

@Composable
fun NextLevelButtonElement() {
    if (GameStateHolder.level.value!! < 13) {
        Button(onClick = {
            GameStateHolder.levelUp()
        }) {
            Text(
                fontSize = standardTextSize.value,
                text = stringResource(Res.string.next_level)
            )
        }
    }
}
