package util

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

actual fun String.toClipboard() {
    val stringSelection = StringSelection(this)
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(stringSelection, null)
}
