package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hyperether.resources.stringResource
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.reset_settings
import coshanu.composeapp.generated.resources.setting
import coshanu.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.painterResource
import settings
import ui.UiStateHolder.largerTextSize
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize
import util.clickableHoverIcon
import util.colorFilter
import util.onClick

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
                    Text("", modifier = Modifier.fillMaxWidth(0.2f)) // just an invisible placeholder
                    Text(stringResource(Res.string.settings),
                        fontSize = largerTextSize.value,
                        lineHeight = standardLineHeight.value,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text("", modifier = Modifier.fillMaxWidth(0.2f)) // just an invisible placeholder

                    closingX { UiStateHolder.displaySettingsArea.value = false }
                }
                SettingsArea()
            }
        } else {
            Image( // source: https://iconduck.com/icons/56992/setting
                painter = painterResource(Res.drawable.setting),
                contentDescription = null,
                colorFilter = colorFilter(Color.Gray),
                modifier = Modifier
                    .padding(start = 5.dp, top = 10.dp, end = 10.dp)
                    .size(40.dp)
                    .onClick(onClick = { UiStateHolder.displaySettingsArea.value = true })
            )
        }
    }
}

@Composable
private fun SettingsArea() {
    Column ( verticalArrangement = Arrangement.spacedBy(16.dp)) {
        DarkModeSwitch()
        LanguageChooser()

        Button(onClick = { settings.clear() }) {
            Text(
                stringResource(Res.string.reset_settings),
                fontSize = standardTextSize.value,
                lineHeight = standardLineHeight.value,
            )
        }
    }
}
