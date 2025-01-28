package ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import game.GameMode
import game.GameStateHolder

@Composable
fun GameModeSymbol(modifier: Modifier = Modifier) {

    // todo add game mode symbol
    when (GameStateHolder.gameMode.value) {
        GameMode.SINGLE_ELEMENT -> {
            Text("${GameMode.SINGLE_ELEMENT.message} must fit for tiles to match",
                modifier = modifier,
                fontSize = TextUnit(1.5f, TextUnitType.Em)
            )
        }
        GameMode.TWO_ELEMENTS -> {
            Text("${GameMode.TWO_ELEMENTS.message} must fit for tiles to match",
                modifier = modifier,
                fontSize = TextUnit(1.2f, TextUnitType.Em)
            )
        }
        else -> {
            // not yet chosen, do nothing
        }
    }
}
