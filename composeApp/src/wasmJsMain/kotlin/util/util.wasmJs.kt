package util

import kotlinx.browser.window

actual fun callUrl(url: String) {
    window.location.href = url
}
