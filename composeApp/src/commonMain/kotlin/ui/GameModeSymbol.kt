package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.*
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.single_element_must_fit
import coshanu.composeapp.generated.resources.time_is_running
import coshanu.composeapp.generated.resources.two_elements_must_fit
import game.enums.GameMode
import game.GameStateHolder
import org.jetbrains.compose.resources.painterResource
import com.hyperether.resources.stringResource
import ui.UiStateHolder.largerTextSize
import ui.UiStateHolder.standardTextSize
import util.colorFilter


@Composable
fun GameModeSymbol(modifier: Modifier = Modifier) {

    // todo display a symbol for the game mode
    when (GameStateHolder.gameMode.value) {
        GameMode.SINGLE_ELEMENT -> {

            Row {
                CardSymbol(20f, "1")
                Text(
                    stringResource(Res.string.single_element_must_fit),
                    modifier = modifier.padding(start = 15.dp),
                    fontSize = largerTextSize.value
                )
            }
        }
        GameMode.TWO_ELEMENTS -> {
            Row {
                CardSymbol(-20f, "1")
                CardSymbol(20f, "2")

                Text(stringResource(Res.string.two_elements_must_fit),
                    modifier = modifier.padding(start = 15.dp),
                    fontSize = largerTextSize.value
                )
            }
        }
        GameMode.TWO_ELEMENTS_WITH_TIMER -> {
            Row {
                CardSymbol(-20f, "1")
                CardSymbol(20f, "2")

                Image( // source: https://iconduck.com/icons/157837/stopwatch
                    painter = painterResource(Res.drawable.stopwatch),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp).size(20.dp),
                    colorFilter = colorFilter(Color.Red)
                )
                Text("${GameStateHolder.startTime.value}/${GameStateHolder.addTime.value}",
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp),
                    fontSize = standardTextSize.value
                )

                Text(stringResource(Res.string.two_elements_must_fit),
                    modifier = modifier.padding(start = 15.dp),
                    fontSize = largerTextSize.value
                )
            }
            Text(stringResource(Res.string.time_is_running),
                fontSize = largerTextSize.value
            )
        }
        else -> {
            // not yet chosen, do nothing
        }
    }
}

@Composable
private fun CardSymbol(rotate: Float = 0f, text: String) {
    Card(
        modifier = Modifier
            .rotate(rotate)
            .height(30.dp)
            .aspectRatio(0.5f)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(5.dp))
            .border(1.dp, color = Color.Red, shape = RoundedCornerShape(5.dp)),
        shape = RoundedCornerShape(5.dp),

        ) { Text(text, textAlign = TextAlign.Center) }
}
