package ui

import DARK_MODE
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.dark_mode
import coshanu.composeapp.generated.resources.light_mode
import com.hyperether.resources.stringResource
import coshanu.composeapp.generated.resources.color_active
import coshanu.composeapp.generated.resources.color_inactive
import settings
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize
import util.clickableHoverIcon

@Composable
fun ColorActiveSwitch() {
    Row() {
        Switch(
            checked = UiStateHolder.colorActive.value,
            onCheckedChange = {
                UiStateHolder.setColorActive(it)
            },
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
            text = if (UiStateHolder.colorActive.value) {
                stringResource(Res.string.color_active)
            } else {
                stringResource(Res.string.color_inactive)
            },
            modifier = Modifier.padding(top = 20.dp))
    }
}
