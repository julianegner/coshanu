package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.*
import isPlatformWasm
import game.enums.ScreenType
import org.jetbrains.compose.resources.stringResource
import ui.UiStateHolder.standardLineHeight
import ui.UiStateHolder.standardTextSize
import ui.UiStateHolder.subtitleTextSize
import ui.UiStateHolder.titleTextSize
import withImpressum

val displayLicenseDetails: MutableState<Boolean> = mutableStateOf(false)

@Composable
fun InfoAreaWrapper(modifier: Modifier = Modifier) {
    // Info area for web only
    if (!isPlatformWasm) {
        return
    }
    Row (
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = if (UiStateHolder.displayInfoArea.value)
                        modifier.fillMaxSize().border(1.dp, Color.Gray)
                    else
                        modifier.fillMaxWidth()
    )
    {
        if (UiStateHolder.displayInfoArea.value) {
            InfoArea()
        } else {
            InfoSymbol(
                Modifier
                    .clickable(onClick = { UiStateHolder.displayInfoArea.value = true })
                    .padding(10.dp)
            )
        }
    }
}

@Composable
fun InfoArea() {
    InfoSymbol(Modifier.padding(20.dp))
    Column (modifier = Modifier
        .padding(top = 10.dp)
        .fillMaxWidth(0.9f)
        .padding(top = 20.dp, bottom = 20.dp)
    ) {
        Text("${stringResource(Res.string.info_area_title)}\n", fontSize = subtitleTextSize.value)
        // information
        BulletPoint(stringResource(Res.string.general_information_title))
        Text(stringResource(Res.string.general_information_text), fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
        // license
        BulletPoint(stringResource(Res.string.license_title))
        LicenseDetailArea()
        // code
        BulletPoint(stringResource(Res.string.code_title))
        Text(stringResource(Res.string.code_info), fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
        TextLink("https://github.com/julianegner/coshanu")
        // platform availability
        BulletPoint(stringResource(Res.string.platform_availability_title))
        Text(stringResource(Res.string.platform_availability_info), fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
        PlatformOverviewTable()
        // localization
        BulletPoint(stringResource(Res.string.localization_title))
        Text(stringResource(Res.string.localization_info), fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
        Text("${stringResource(Res.string.translators_prefix)} ${stringResource(Res.string.language_name)}: ${stringResource(Res.string.translator)}", fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
        // contribute
        BulletPoint(stringResource(Res.string.contribute_title))
        Text(stringResource(Res.string.contribute_info), fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
        TextLink("https://github.com/julianegner/coshanu/blob/main/composeApp/src/commonMain/composeResources/values/strings.xml")
        Text(stringResource(Res.string.contribute_translation_mail), fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
        TextLink(url = "mailto:admin@cosha.nu", text = "admin@cosha.nu")

        if (withImpressum) {
            Impressum()
        }
    }
    closingX { UiStateHolder.displayInfoArea.value = false }
}

// this is for legal reasons. Should not be needed, as its a non-commercial website, but better safe than sorry
@Composable
private fun Impressum() {
    BulletPoint("Impressum")
    Text("Julian Egner\nWeissstrasse 18\n53123 Bonn\nGermany", fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
    Row {
        Text("mail: ", fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
        TextLink(url = "mailto:admin@cosha.nu", text = "admin@cosha.nu")
    }
}

@Composable
private fun PlatformOverviewTable() {
    val modifier = Modifier.width( if (UiStateHolder.screenType.value == ScreenType.LANDSCAPE) 300.dp else 600.dp)
    val horizontalArrangement = Arrangement.SpaceBetween
    Column (modifier = Modifier.padding(top = 10.dp)) {
        Row (modifier = modifier.padding(bottom = 5.dp), horizontalArrangement = horizontalArrangement) {
            Text(stringResource(Res.string.platform), fontWeight = FontWeight.Bold, fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
            Text(stringResource(Res.string.link), fontWeight = FontWeight.Bold, fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
        }
        Row (modifier = modifier, horizontalArrangement = horizontalArrangement) {
            Text("Web", fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
            TextLink(url = "https://cosha.nu", text = "cosha.nu")
        }
        Row (modifier = modifier, horizontalArrangement = horizontalArrangement) {
            Text("itch.io", fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
            TextLink(url = "https://jegner.itch.io/coshanu", text = "itch.io")
        }
        Row (modifier = modifier, horizontalArrangement = horizontalArrangement) {
            Text("Android", fontSize = standardTextSize.value, lineHeight = standardLineHeight.value)
            Text("Work in progress", fontSize = standardTextSize.value)
        }
        // Add more rows as needed
    }
}

@Composable
fun ImpressumWrapper(rowModifier: Modifier = Modifier) {
    // Impressum for web only
    if (!isPlatformWasm or !withImpressum) {
        return
    }
    Row (
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    )
    {
        if (UiStateHolder.displayImpressum.value) {
            Column (modifier = Modifier.border(1.dp, Color.Gray).padding(10.dp)) {
                Row (
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.End
                ) {
                    closingX { UiStateHolder.displayImpressum.value = false }
                }
                Impressum()
            }
        } else {
            Text(text = "Impressum",
                fontSize = standardTextSize.value,
                modifier = Modifier
                    .clickable(onClick = { UiStateHolder.displayImpressum.value = true })
            )
        }
    }
}

@Composable
fun BulletPoint(text: String) {
    Text("\n* $text", fontSize = standardTextSize.value, fontStyle = FontStyle.Italic, lineHeight = standardLineHeight.value)
}

@Composable
private fun closingX(onClick: () -> Unit) {
    Text(
        text = "X",
        fontSize = titleTextSize.value,
        color = Color.Gray,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(20.dp)
    )
}

@Composable
fun InfoSymbol(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(50.dp)
            .drawBehind {
                drawCircle(
                    color = Color.Gray,
                    radius = this.size.minDimension / 2
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "i",
            color = Color.White,
            fontSize = TextUnit(2.5f, TextUnitType.Em),
        )
    }
}

@Composable
private fun LicenseDetailArea() {

    Text(stringResource(Res.string.general_license_info), fontSize = standardTextSize.value)
    if (!displayLicenseDetails.value) {
        Text(
            text = stringResource(Res.string.license_details_link_text),
            style = TextStyle(
                color =
                    if (UiStateHolder.darkModeState.value) Color(0xAA00FFFF) else Color.Blue,
                textDecoration = TextDecoration.Underline
            ),
            fontSize = standardTextSize.value,
            modifier = Modifier.clickable {
                displayLicenseDetails.value = true
            })
    } else {
        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray)
                .padding(10.dp)
        ) {

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        )
        {
            closingX { displayLicenseDetails.value = false }
        }

        Text("MIT License", fontSize = subtitleTextSize.value)
        Text(
            """


Copyright (c) 2025 Julian Egner

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.        
    """.trimIndent(),
            fontSize = standardTextSize.value,
            lineHeight = standardLineHeight.value,
            )
        }
    }
}
