package util

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon

fun Modifier.clickableHoverIcon(condition: Boolean = true): Modifier {
    return if (!condition) this else this.then(Modifier.pointerHoverIcon(PointerIcon.Hand))
}
