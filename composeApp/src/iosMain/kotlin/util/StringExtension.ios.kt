package util

import platform.UIKit.UIPasteboard

actual fun String.toClipboard() {
    UIPasteboard.generalPasteboard().string = this
}
