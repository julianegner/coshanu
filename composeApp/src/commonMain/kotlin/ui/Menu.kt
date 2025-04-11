package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.choose_level
import coshanu.composeapp.generated.resources.single_element
import coshanu.composeapp.generated.resources.tutorial
import coshanu.composeapp.generated.resources.two_elements
import game.GameStateHolder
import game.darkmodeYellow
import isPlatformAndroid
import org.jetbrains.compose.resources.stringResource
import ui.UiStateHolder.menuButtonWidth
import ui.UiStateHolder.menuRowTextWidth
import ui.UiStateHolder.standardTextSize

fun buttonModifier(level: Int) = Modifier.padding(
    start = 5.dp,
    end = if (level % 10 == 0) 20.dp else 5.dp,
    top = 10.dp)
    .width(if (level % 10 == 0) menuButtonWidth.value * 2 else menuButtonWidth.value)

@Composable
fun Menu() {
    val currentLevelButtonColors = ButtonDefaults.buttonColors(
        contentColor = Color.Black,
        backgroundColor = darkmodeYellow
    )
    val buttonDefaultColors = ButtonDefaults.buttonColors()

    Column() {
        Text(
            fontSize = standardTextSize.value,
            text = stringResource(Res.string.choose_level),
            modifier =
                if (isPlatformAndroid)
                    Modifier
                else
                    Modifier.padding(bottom = 10.dp)
        )

        val elementTextModifier =
            if (isPlatformAndroid)
                Modifier.padding(top = 25.dp).width(menuRowTextWidth.value)
            else
                Modifier.padding(5.dp).width(menuRowTextWidth.value)

        Row {
            Text(
                fontSize = standardTextSize.value,
                lineHeight = TextUnit(1.2f, TextUnitType.Em),
                text = stringResource(Res.string.single_element),
                modifier = elementTextModifier
            )

            (0..3).forEach { i ->
                Button(
                    colors = if (GameStateHolder.level.value == i) currentLevelButtonColors else buttonDefaultColors,
                    modifier = buttonModifier(i),
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
                lineHeight = TextUnit(1.2f, TextUnitType.Em),
                text = stringResource(Res.string.two_elements),
                modifier = elementTextModifier
            )
            (10..13).forEach { i ->
                Button(
                    modifier = buttonModifier(i),
                    colors = if (GameStateHolder.level.value == i) currentLevelButtonColors else
                        ButtonDefaults.buttonColors(
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
