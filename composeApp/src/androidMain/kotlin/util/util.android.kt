package util

import android.content.Intent
import android.net.Uri
import android.app.Activity
import de.julianegner.coshanu.MainActivity

actual fun callUrl(url: String) {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
}
