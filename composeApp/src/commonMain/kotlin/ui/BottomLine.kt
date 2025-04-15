package ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.bottomLine(
    color: Color = Color.Gray,
    strokeWidth: Dp = 1.dp
): Modifier = this.drawBehind {
    val strokeWidthPx = strokeWidth.toPx()
    val y = size.height - strokeWidthPx / 2
    drawLine(
        color = color,
        start = androidx.compose.ui.geometry.Offset(0f, y),
        end = androidx.compose.ui.geometry.Offset(size.width, y),
        strokeWidth = strokeWidthPx
    )
}
