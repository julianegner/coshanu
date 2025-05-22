package util

import androidx.compose.ui.graphics.Color

fun colorFilter(color: Color): androidx.compose.ui.graphics.ColorFilter =
    androidx.compose.ui.graphics.ColorFilter.tint(
        color = color
    )
