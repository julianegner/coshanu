package util

import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ui.UiStateHolder

@Composable
fun TooltipWrapper(
    text: String?,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered = remember { mutableStateOf(false) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> isHovered.value = true
                is HoverInteraction.Exit -> isHovered.value = false
            }
        }
    }
    Box(modifier = Modifier
        .hoverable(interactionSource = interactionSource, enabled = true)) {
        if (isHovered.value && !text.isNullOrEmpty()) {
            Tooltip(text, offset)
        }
        content()
    }
}

// UI element to show the tooltip
@Composable
fun Tooltip(
    text: String,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
) {
    Box(
        modifier =  Modifier
            .zIndex(1f)
            .layout { measurable, constraints ->
                // Measure the tooltip but don't add it to the layout
                val placeable = measurable.measure(constraints)
                layout(0,0) { // Set the size to 0 to avoid taking up space and move other elements
                    placeable.place(0, 0)
                }
            }
            .offset(x = offset.x, y = offset.y)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .padding(10.dp)
    ) {
        Text(
            text,
            color = Color.Black,
            fontSize = UiStateHolder.standardTextSize.value,
            lineHeight = UiStateHolder.standardLineHeight.value
        )
    }
}

