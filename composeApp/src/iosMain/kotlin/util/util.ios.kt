package util

import platform.UIKit.UIApplication
import platform.Foundation.NSURL

actual fun callUrl(url: String) {
    val nsURL = NSURL.URLWithString(url)
    nsURL?.let {
        UIApplication.sharedApplication.openURL(it)
    }
}
