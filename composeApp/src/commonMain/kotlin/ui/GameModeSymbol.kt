package ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.single_element_must_fit
import coshanu.composeapp.generated.resources.two_elements_must_fit
import game.GameMode
import game.GameStateHolder
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameModeSymbol(modifier: Modifier = Modifier) {

    when (GameStateHolder.gameMode.value) {
        GameMode.SINGLE_ELEMENT -> {
            Text(stringResource(Res.string.single_element_must_fit),
                modifier = modifier,
                fontSize = TextUnit(1.5f, TextUnitType.Em)
            )
        }
        GameMode.TWO_ELEMENTS -> {
            Text(stringResource(Res.string.two_elements_must_fit),
                modifier = modifier,
                fontSize = TextUnit(1.2f, TextUnitType.Em)
            )
        }
        else -> {
            // not yet chosen, do nothing
        }
    }
}
