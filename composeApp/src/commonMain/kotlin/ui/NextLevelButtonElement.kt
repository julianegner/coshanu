package ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.next_level
import game.GameStateHolder
import com.hyperether.resources.stringResource
import ui.UiStateHolder.standardTextSize
import util.clickableHoverIcon

@Composable
fun NextLevelButtonElement() {
    if (GameStateHolder.level.value!! < 26) {
        Button(
            onClick = { GameStateHolder.levelUp() },
            modifier = Modifier.clickableHoverIcon()
        ) {
            Text(
                fontSize = standardTextSize.value,
                text = stringResource(Res.string.next_level)
            )
        }
    }
}
