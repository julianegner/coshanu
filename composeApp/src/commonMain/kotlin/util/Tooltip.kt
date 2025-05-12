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

// function with changeable values from UI State Holder
@Composable
fun Modifier.tooltip(
    text: String,
    offset: DpOffset = DpOffset(0.dp, 0.dp)): Modifier
        = this.tooltip(
            offset = offset,
            backgroundColor = if (UiStateHolder.darkModeState.value)
                Color.Gray
            else
                Color.LightGray) {

            Text(text,
                fontSize = UiStateHolder.standardTextSize.value,
                lineHeight = UiStateHolder.standardLineHeight.value
            )
        }

// function without any UI stateholder calls, usable in a library
@Composable
fun Modifier.tooltip(
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    backgroundColor: Color = Color.LightGray,
    content: @Composable () -> Unit
): Modifier = this.tooltip(
    modifier = Modifier
        .offset(offset.x, offset.y)
        .clip(RoundedCornerShape(8.dp))
        .background(backgroundColor)
        .padding(10.dp),
    content
)

// function holding the tooltip logic
@Composable
fun Modifier.tooltip(
        modifier: Modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .padding(10.dp)
            ,
        content: @Composable () -> Unit
    ): Modifier {

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
    if (isHovered.value) {
        Tooltip(modifier, content)
    }


    return this
        .hoverable(interactionSource = interactionSource, enabled = true)
}

// UI element to show the tooltip
@Composable
fun Tooltip(
        modifier: Modifier,
        content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .zIndex(1f)
            .layout { measurable, constraints ->
                // Measure the tooltip but don't add it to the layout
                val placeable = measurable.measure(constraints)
                layout(0, 0) {
                    placeable.place(0, 0)
                }
            }
            .then(modifier)

    ) {
        content()
    }
}

/*
this wrapper is ment for usage where the modifier extension function does not work
like on the InfoSymbol, which is inside of a Row and the symbol flickers if not wrapped
for the moment, the infoSymbol is wrapped in a row where the tooltip is used
when the tooltip is moved to a library, this must be fixed
 */
// @Composable
// fun TooltipWrapper(
//     text: String,
//     offset: DpOffset = DpOffset(0.dp, 0.dp),
//     modifier: Modifier? = null,
//     content: @Composable () -> Unit
// ) {
//     // todo this does not work, it does not show the tooltip
//     val mod = Modifier.tooltip(text)
//     /*
//         if (modifier != null)
//             Modifier.tooltip(offset, modifier,
//                 { Text(text) })
//         else
//             Modifier.tooltip(text, offset)
//
//      */
//     Row {
//         Box(modifier = mod) { // } Modifier.tooltip(text)) {
//             content()
//         }
//     }
//
//     /*
//     Row {
//         Box(modifier = Modifier.tooltip(text)) {
//             content()
//         }
//     }
//      */
// }
