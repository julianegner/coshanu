package util

import kotlinx.browser.window

actual fun String.toClipboard() {
    window.navigator.clipboard.writeText(this)
}
