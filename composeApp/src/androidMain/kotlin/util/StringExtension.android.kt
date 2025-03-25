package util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper


actual fun String.toClipboard() {
    // todo get this to work for android
    // val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    // val clipData = ClipData.newPlainText("text", this)
    // clipboardManager.setPrimaryClip(clipData)
}
