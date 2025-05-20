package util

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon

fun Modifier.clickableHoverIcon(condition: Boolean = true): Modifier {
    return if (!condition) this else this.then(Modifier.pointerHoverIcon(PointerIcon.Hand))
}

fun Modifier.onClick(enabled: Boolean = true, onClick: () -> Unit): Modifier {
    return if (!enabled) this else this.clickable(onClick = onClick).pointerHoverIcon(PointerIcon.Hand)
}
