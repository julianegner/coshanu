package util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.window.Popup
import androidx.compose.material.Text
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize
import kotlin.math.min


@Composable
fun Modifier.tooltips(
    title: String? = null,
    text: String,
    arrowAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    textAlign: TextAlign = TextAlign.Center,
    maxWidth: Dp = Dp.Unspecified,
    enabled: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(),
    showOverlay: Boolean = true,
    highlightComponent: Boolean = true,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    onDismiss: () -> Unit = {}
): Modifier {

    //val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    // val screenWidthPx = remember { with(density) { configuration.screenWidthDp.dp.roundToPx() } }
    // val screenHeightPx = remember { with(density) { configuration.screenHeightDp.dp.roundToPx() } }

    val positionInRoot = remember { mutableStateOf(IntOffset.Zero) }
    val tooltipSize = remember { mutableStateOf(IntSize(0, 0)) }
    val componentSize = remember { mutableStateOf(IntSize(0, 0)) }

    // val tooltipOffset by remember(positionInRoot, componentSize, tooltipSize) {
    //     derivedStateOf {
    //         calculateOffset(
    //             positionInRoot, componentSize, tooltipSize, screenWidthPx, screenHeightPx, horizontalAlignment, verticalAlignment
    //         )
    //     }
    // }

    if (enabled) {
        Popup(
            alignment = Alignment.TopEnd
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawOverlayBackground(
                        showOverlay = showOverlay,
                        highlightComponent = highlightComponent,
                        positionInRoot = positionInRoot.value,
                        componentSize = componentSize.value,
                        backgroundColor = Color.LightGray, // MaterialTheme.colorScheme.surfaceContainerLowest,
                        backgroundAlpha = 0.8f
                    )
                    .clickable(
                        onClick = {
                            onDismiss()
                        }
                    )
            ) {
                ArrowTooltip(
                    modifier = Modifier
                        .widthIn(max = maxWidth)
                        .onSizeChanged { tooltipSize.value = it }
                        // .offset { tooltipOffset }
                        .padding(paddingValues),
                    title = title,
                    text = text,
                    arrowAlignment = arrowAlignment,
                    textAlign = textAlign
                )
            }
        }
    }

    return this then Modifier.onPlaced {
        componentSize.value = it.size
        positionInRoot.value = it.positionInRoot().round()
    }
}

@Composable
fun ArrowTooltip(
    modifier: Modifier = Modifier,
    title: String? = null,
    text: String,
    arrowAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    textAlign: TextAlign = TextAlign.Center,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = arrowAlignment,
    ) {
        /*
        Icon(
            modifier = Modifier
                .padding(
                    start = if (arrowAlignment != Alignment.CenterHorizontally) 6.dp else 0.dp,
                    end = if (arrowAlignment != Alignment.CenterHorizontally) 6.dp else 0.dp
                ),
            painter = painterResource(id = R.drawable.ic_arrow_up),
            contentDescription = "Tool tip Arrow",
            tint = MaterialTheme.colorScheme.surfaceContainer,
        )
         */

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(16.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = title ?: "",
                // color = MaterialTheme.colorScheme.primary,
                textAlign = textAlign,
                // style = MaterialTheme.typography.bodyMedium.copy(
                //     fontWeight = FontWeight.Medium
                // )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = text,
                textAlign = textAlign,
                // style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun calculateOffset(
    positionInRoot: IntOffset,
    componentSize: IntSize,
    tooltipSize: IntSize,
    screenWidthPx: Int,
    screenHeightPx: Int,
    horizontalAlignment: Alignment.Horizontal,
    verticalAlignment: Alignment.Vertical
): IntOffset {
    val horizontalAlignmentPosition = when (horizontalAlignment) {
        Alignment.Start -> positionInRoot.x
        Alignment.End -> positionInRoot.x + componentSize.width - tooltipSize.width
        else -> positionInRoot.x + (componentSize.width / 2) - (tooltipSize.width / 2)
    }
    val verticalAlignmentPosition = when (verticalAlignment) {
        Alignment.Top -> positionInRoot.y - tooltipSize.height
        Alignment.Bottom -> positionInRoot.y + componentSize.height
        else -> positionInRoot.y + (componentSize.height / 2)
    }

    val reult = IntOffset(
        x = min(screenWidthPx - tooltipSize.width, horizontalAlignmentPosition),
        y = min(screenHeightPx - tooltipSize.height, verticalAlignmentPosition)
    )

    return reult
}

private fun Modifier.drawOverlayBackground(
    showOverlay: Boolean,
    highlightComponent: Boolean,
    positionInRoot: IntOffset,
    componentSize: IntSize,
    backgroundColor: Color,
    backgroundAlpha: Float
) : Modifier {
    return if (showOverlay) {
        if (highlightComponent) {
            drawBehind {
                val highlightPath = Path().apply {
                    addRect(Rect(positionInRoot.toOffset(), componentSize.toSize()))
                }
                clipPath(highlightPath, clipOp = ClipOp.Difference) {
                    drawRect(SolidColor(backgroundColor.copy(alpha = backgroundAlpha)))
                }
            }
        } else {
            background(backgroundColor.copy(alpha = backgroundAlpha))
        }
    } else {
        this
    }
}

