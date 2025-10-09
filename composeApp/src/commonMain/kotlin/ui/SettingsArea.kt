package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.hyperether.resources.stringResource
import de.julianegner.multiplatformTooltip.TooltipWrapper
import de.julianegner.multiplatformTooltip.tooltipCompatibleClick
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.card_tooltip_active
import coshanu.composeapp.generated.resources.card_tooltip_inactive
import coshanu.composeapp.generated.resources.color_active
import coshanu.composeapp.generated.resources.color_inactive
import coshanu.composeapp.generated.resources.color_option_description
import coshanu.composeapp.generated.resources.dark_mode
import coshanu.composeapp.generated.resources.light_mode
import coshanu.composeapp.generated.resources.reset_settings
import coshanu.composeapp.generated.resources.setting
import coshanu.composeapp.generated.resources.settings
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import settings
import ui.UiStateHolder.largerTextSize
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize
import util.colorFilter
import util.runOnMainAfter
import util.clickableHoverIcon

@Composable
fun SettingsAreaWrapper() {
    Row (
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    )
    {
        if (UiStateHolder.displaySettingsArea.value) {
            Column (modifier =
                Modifier
                    .border(1.dp, Color.Gray)
                    .padding(10.dp)) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image( // source: https://iconduck.com/icons/56992/setting
                        painter = painterResource(Res.drawable.setting),
                        contentDescription = null,
                        colorFilter = colorFilter(Color.Gray),
                        modifier = Modifier
                            .padding(start = 5.dp, top = 10.dp)
                            .size(40.dp)
                    )
                    Spacer(modifier = Modifier.fillMaxWidth(0.2f))
                    Text(stringResource(Res.string.settings),
                        fontSize = largerTextSize.value,
                        lineHeight = standardLineHeight.value,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Spacer(modifier = Modifier.fillMaxWidth(0.2f))

                    closingX { UiStateHolder.displaySettingsArea.value = false }
                }
                SettingsArea()
            }
        } else {
            TooltipWrapper(
                stringResource(Res.string.settings),
                offset = DpOffset(x = (-120).dp, y = 50.dp)) {
                Image( // source: https://iconduck.com/icons/56992/setting
                    painter = painterResource(Res.drawable.setting),
                    contentDescription = null,
                    colorFilter = colorFilter(Color.Gray),
                    modifier = Modifier
                        .padding(start = 5.dp, top = 10.dp, end = 10.dp)
                        .size(40.dp)
                        .tooltipCompatibleClick(interactionSource = null, indication = null) { UiStateHolder.displaySettingsArea.value = true }
                        .clickableHoverIcon()
                        //.onClick(onClick = { UiStateHolder.displaySettingsArea.value = true })
                )
            }
        }
    }
}

@Composable
private fun SettingsArea() {
    val coroutineScope = rememberCoroutineScope()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GenericSwitch(
            state = UiStateHolder.darkModeState,
            checkedText = stringResource(Res.string.dark_mode),
            uncheckedText = stringResource(Res.string.light_mode),
            onCheckedChange = { UiStateHolder.setDarkModeState(it) }
        )
        LanguageChooser()
        GenericSwitch(
            state = UiStateHolder.colorActive,
            checkedText = stringResource(Res.string.color_active),
            uncheckedText = stringResource(Res.string.color_inactive),
            onCheckedChange = { UiStateHolder.setColorActive(it) }
        )
        Text(
            stringResource(Res.string.color_option_description),
            fontSize = standardTextSize.value,
            lineHeight = standardLineHeight.value,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )
        GenericSwitch(
            state = UiStateHolder.cardTooltipActive,
            checkedText = stringResource(Res.string.card_tooltip_active),
            uncheckedText = stringResource(Res.string.card_tooltip_inactive),
            onCheckedChange = { UiStateHolder.setCardTooltipActive(it) }
        )

        Button(onClick = {
            settings.clear()
            coroutineScope.launch {
                // show snackbar and schedule its removal after 5 seconds
                UiStateHolder.snackbarHost.value.showSnackbar("Settings cleared successfully")
                runOnMainAfter(5000) { UiStateHolder.snackbarHost.value.currentSnackbarData?.dismiss() }
            }
        }) {
            Text(
                stringResource(Res.string.reset_settings),
                fontSize = standardTextSize.value,
                lineHeight = standardLineHeight.value,
            )
        }
    }
}
