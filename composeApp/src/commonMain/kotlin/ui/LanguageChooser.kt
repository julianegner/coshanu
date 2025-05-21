package ui

import CUSTOM_LOCALE
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.hyperether.resources.AppLocale
import com.hyperether.resources.*
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize
import dev.carlsen.flagkit.FlagKit
import isLandscape
import settings


@Composable
fun LanguageChooser() {
    val expanded = remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .size(
                if (isLandscape) 150.dp else 300.dp,
                if (isLandscape) 32.dp else 70.dp
            )
            .clip(RoundedCornerShape(4.dp))
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(4.dp))
            .clickable { expanded.value = !expanded.value },
    ) {
        Row {
            languageFlagAndName(currentLanguage.value)
        }
        Icon(
            Icons.Filled.ArrowDropDown, "contentDescription",
            Modifier.align(Alignment.CenterEnd)
        )
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            AppLocale.entries.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        currentLanguage.value = selectionOption
                        expanded.value = false
                        settings.putString(CUSTOM_LOCALE, selectionOption.name)
                    }
                ) {
                    languageFlagAndName(selectionOption)
                }
            }
        }
    }
}

@Composable
private fun languageFlagAndName(appLocale: AppLocale) {
    val imageModifier = if (isLandscape)
        Modifier
            .padding(start = 10.dp, top = 5.dp)
    else
        Modifier
            .padding(start = 10.dp, top = 20.dp)
            .width(60.dp)
            .height(30.dp)

    Image(
        imageVector = FlagKit.getFlag(countryCode = countrycode(appLocale))!!,
        contentDescription = "Flag",
        contentScale = ContentScale.FillBounds,
        modifier = imageModifier
            .border(1.dp, Color.Gray)
    )
    Text(
        text = languageLabel(appLocale),
        fontSize = standardTextSize.value,
        lineHeight = standardLineHeight.value,
        modifier = Modifier.padding(start = 10.dp)
    )
}

private fun languageLabel(locale: AppLocale): String = "${locale.nativeName} (${locale.code})"
private fun countrycode(locale: AppLocale): String = if (locale.name == "DEFAULT") "GB" else locale.name.uppercase()
