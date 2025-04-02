package util

actual fun callUrl(url: String) {
    activity.startActivity(
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
}
