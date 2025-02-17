package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

@Composable
fun Menu() {

    Text(stringResource(Res.string.choose_level))

    Row {
        Text(stringResource(Res.string.single_element), modifier = Modifier.padding(5.dp).width(100.dp))
        (0..3).forEach { i ->
            Button(
                onClick = {
                    GameStateHolder.changeLevel(i)
                }) { Text(if (i == 0) stringResource(Res.string.tutorial) else i.toString()) }
        }
    }

    val LightBlue = Color(0xCC3333FF)

    Row {
        Text(stringResource(Res.string.two_elements), modifier = Modifier.padding(5.dp).width(100.dp))
        (10..13).forEach { i ->
            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = if (GameStateHolder.darkModeState.value) Color.Black else Color.White,
                    backgroundColor = if (GameStateHolder.darkModeState.value) LightBlue else Color.Blue
                ),
                onClick = {
                    GameStateHolder.changeLevel(i)
                }) { Text(if (i == 10) stringResource(Res.string.tutorial) else i.toString()) }
        }
    }
}
