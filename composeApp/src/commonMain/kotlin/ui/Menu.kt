package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.choose_level
import coshanu.composeapp.generated.resources.single_element
import coshanu.composeapp.generated.resources.tutorial
import coshanu.composeapp.generated.resources.two_elements
import game.GameStateHolder
import org.jetbrains.compose.resources.stringResource
import ui.UiStateHolder.menuRowTextWidth
import ui.UiStateHolder.standardTextSize

@Composable
fun Menu() {

    Column() {
        Text(
            fontSize = standardTextSize.value,
            text = stringResource(Res.string.choose_level),
        )

        Row {
            Text(
                fontSize = standardTextSize.value,
                text = stringResource(Res.string.single_element),
                modifier = Modifier.padding(5.dp).width(menuRowTextWidth.value)
            )
            (0..3).forEach { i ->
                Button(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        GameStateHolder.changeLevel(i)
                    }) {
                    Text(
                        fontSize = standardTextSize.value,
                        text = if (i == 0) stringResource(Res.string.tutorial) else i.toString()
                    )
                }
            }
        }

        val LightBlue = Color(0xCC3333FF)

        Row {
            Text(
                fontSize = standardTextSize.value,
                text = stringResource(Res.string.two_elements),
                modifier = Modifier.padding(5.dp).width(menuRowTextWidth.value)
            )
            (10..13).forEach { i ->
                Button(
                    modifier = Modifier.padding(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = if (UiStateHolder.darkModeState.value) Color.Black else Color.White,
                        backgroundColor = if (UiStateHolder.darkModeState.value) LightBlue else Color.Blue
                    ),
                    onClick = {
                        GameStateHolder.changeLevel(i)
                    }) {
                    Text(
                        fontSize = standardTextSize.value,
                        text = if (i == 10) stringResource(Res.string.tutorial) else i.toString()
                    )
                }
            }
        }
    }
}
