package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyperether.resources.AppLocale
import com.hyperether.resources.*
import game.enums.ScreenType
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize

@Composable
fun LanguageChooser() {
    val expanded = remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .size(
                if (UiStateHolder.screenType.value == ScreenType.LANDSCAPE) 150.dp else 300.dp,
                if (UiStateHolder.screenType.value == ScreenType.LANDSCAPE) 32.dp else 70.dp
            )
            .clip(RoundedCornerShape(4.dp))
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(4.dp))
            .clickable { expanded.value = !expanded.value },
    ) {
        Text(
            text = languageLabel(currentLanguage.value),
            fontSize = standardTextSize.value,
            lineHeight = standardLineHeight.value,
            modifier = Modifier.padding(start = 10.dp)
        )
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
                    }
                ) {
                    Text(
                        text = languageLabel(selectionOption),
                        fontSize = standardTextSize.value
                        )
                }
            }
        }
    }
}

private fun getLanguageName(locale: AppLocale): String {
    val languageName =
        when (locale) {
            AppLocale.DEFAULT -> StringsDefault.strings["language_name"]
            AppLocale.DE -> StringsDe.strings["language_name"]
            else -> StringsDefault.strings["language_name"]
        }
    return languageName!!
}

private fun languageLabel(locale: AppLocale): String = getLanguageName(locale) + if (locale.name == "DEFAULT") " (EN)" else " (${locale.name})"

