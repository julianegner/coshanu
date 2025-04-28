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
import org.jetbrains.compose.resources.stringResource
import ui.UiStateHolder.largerTextSize


@Composable
fun GameModeSymbol(modifier: Modifier = Modifier) {

    // todo display a symbol for the game mode
    when (GameStateHolder.gameMode.value) {
        GameMode.SINGLE_ELEMENT -> {

            Row {
                Card(
                    modifier = Modifier
                        .rotate(20f)
                        .height(30.dp)
                        .aspectRatio(0.5f)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(5.dp))
                        .border(1.dp, color = androidx.compose.ui.graphics.Color.Red, shape = RoundedCornerShape(5.dp)),
                    shape = RoundedCornerShape(5.dp),

                    ) { Text("1", textAlign = TextAlign.Center) }
                Text(
                    stringResource(Res.string.single_element_must_fit),
                    modifier = modifier.padding(start = 15.dp),
                    fontSize = largerTextSize.value
                )
            }
        }
        GameMode.TWO_ELEMENTS -> {
            Row {
                Card(
                    modifier = Modifier
                        .rotate(-20f)
                        .height(30.dp)
                        .aspectRatio(0.5f)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(5.dp))
                        .border(1.dp, color = androidx.compose.ui.graphics.Color.Red, shape = RoundedCornerShape(5.dp)),
                    shape = RoundedCornerShape(5.dp),

                    ) { Text("1", textAlign = TextAlign.Center) }

                Card(
                    modifier = Modifier
                        .rotate(20f)
                        .height(30.dp)
                        .aspectRatio(0.5f)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(5.dp))
                        .border(1.dp, color = androidx.compose.ui.graphics.Color.Red, shape = RoundedCornerShape(5.dp)),
                    shape = RoundedCornerShape(5.dp),

                    ) { Text("2", textAlign = TextAlign.Center) }

                Text(stringResource(Res.string.two_elements_must_fit),
                    modifier = modifier.padding(start = 15.dp),
                    fontSize = largerTextSize.value
                )
            }
        }
        GameMode.TWO_ELEMENTS_WITH_TIMER -> {
            Row {
                Card(
                    modifier = Modifier
                        .rotate(-20f)
                        .height(30.dp)
                        .aspectRatio(0.5f)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(5.dp))
                        .border(1.dp, color = androidx.compose.ui.graphics.Color.Red, shape = RoundedCornerShape(5.dp)),
                    shape = RoundedCornerShape(5.dp),

                    ) { Text("1", textAlign = TextAlign.Center) }

                Card(
                    modifier = Modifier
                        .rotate(20f)
                        .height(30.dp)
                        .aspectRatio(0.5f)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(5.dp))
                        .border(1.dp, color = androidx.compose.ui.graphics.Color.Red, shape = RoundedCornerShape(5.dp)),
                    shape = RoundedCornerShape(5.dp),

                    ) { Text("2", textAlign = TextAlign.Center) }

                Image( // source: https://iconduck.com/icons/157837/stopwatch
                    painter = painterResource(Res.drawable.stopwatch),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp).size(20.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                        color = androidx.compose.ui.graphics.Color.Red
                    )

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
