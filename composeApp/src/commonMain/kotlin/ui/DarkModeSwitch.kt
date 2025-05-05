package ui

import DARK_MODE
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.dark_mode
import coshanu.composeapp.generated.resources.light_mode
import com.hyperether.resources.stringResource
import settings
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize

@Composable
fun DarkModeSwitch() {
    Row() {
        Switch(
            checked = UiStateHolder.darkModeState.value,
            onCheckedChange = {
                UiStateHolder.setDarkModeState(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.DarkGray,
                checkedTrackColor = Color.LightGray,
                uncheckedThumbColor = Color.DarkGray,
                uncheckedTrackColor = Color.LightGray
            ),
            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
        )
        Text(
            fontSize = standardTextSize.value,
            lineHeight = standardLineHeight.value,
            text = if (UiStateHolder.darkModeState.value) {
                stringResource(Res.string.dark_mode)
            } else {
                stringResource(Res.string.light_mode)
            },
            modifier = Modifier.padding(top = 20.dp))
    }
}
