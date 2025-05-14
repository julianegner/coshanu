package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.hyperether.resources.stringResource
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.sound
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize

@Composable
fun SoundSwitch() {
    Row() {
        Switch(
            checked = UiStateHolder.soundActive.value,
            onCheckedChange = {
                UiStateHolder.setSoundActive(it)
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
            text =  stringResource(Res.string.sound),
            modifier = Modifier.padding(top = 20.dp))
    }
}
