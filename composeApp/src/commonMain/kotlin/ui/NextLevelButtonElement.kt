package ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.next_level
import game.GameStateHolder
import org.jetbrains.compose.resources.stringResource
import ui.UiStateHolder.standardTextSize

@Composable
fun NextLevelButtonElement() {
    if (GameStateHolder.level.value!! < 23) {
        Button(
            onClick = { GameStateHolder.levelUp() },
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
        ) {
            Text(
                fontSize = standardTextSize.value,
                text = stringResource(Res.string.next_level)
            )
        }
    }
}
