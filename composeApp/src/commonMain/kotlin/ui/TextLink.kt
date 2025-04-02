package ui

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import util.callUrl

@Composable
fun TextLink(
    url: String,
    text: String = url,
    fontSize: TextUnit = UiStateHolder.standardTextSize.value,
) {
    Text(
        text = text,
        style = TextStyle(
            color = if (UiStateHolder.darkModeState.value) Color(0xAA00FFFF) else Color.Blue,
            textDecoration = TextDecoration.Underline
        ),
        fontSize = fontSize,
        modifier = Modifier.clickable{callUrl(url)}
    )
}
