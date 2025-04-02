package util

import java.awt.Desktop
import java.net.URI

actual fun callUrl(url: String) {
    try {
        Desktop.getDesktop().browse(URI(url))
    } catch (e: Exception) {
        println("Failed to open URL: $url with error: ${e.message}")
    }
}
