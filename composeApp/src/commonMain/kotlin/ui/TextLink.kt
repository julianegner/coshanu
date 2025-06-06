package ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import util.callUrl
import util.darkmodeLinkBlue
import util.modeDependantColor
import util.onClick

@Composable
fun TextLink(
    url: String,
    text: String = url,
    fontSize: TextUnit = UiStateHolder.standardTextSize.value,
) {
    Text(
        text = text,
        style = TextStyle(
            color = if (UiStateHolder.darkModeState.value) darkmodeLinkBlue else Color.Blue,
            textDecoration = TextDecoration.Underline
        ),
        fontSize = fontSize,
        modifier = Modifier.onClick{callUrl(url)}
    )
}
