package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize
import util.clickableHoverIcon

@Composable
fun GenericSwitch(
    state: MutableState<Boolean>,
    checkedText: String,
    uncheckedText: String,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row {
        Switch(
            checked = state.value,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.DarkGray,
                checkedTrackColor = Color.LightGray,
                uncheckedThumbColor = Color.DarkGray,
                uncheckedTrackColor = Color.LightGray
            ),
            modifier = Modifier.clickableHoverIcon()
        )
        Text(
            fontSize = standardTextSize.value,
            lineHeight = standardLineHeight.value,
            text = if (state.value) checkedText else uncheckedText,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}
