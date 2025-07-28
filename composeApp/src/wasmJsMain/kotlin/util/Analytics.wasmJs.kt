package util

external fun sa_event(
    event: String,
    parameters: String
)

actual fun logAnalyticsEvent(eventName: String, parameters: Map<String, Any>) {
    val parameterString = parameters.entries.joinToString(",") { "${it.key}=${it.value}" }
    javaScriptLog(eventName, parameterString)
}

fun javaScriptLog(eventName: String, parameterString: String) {
    sa_event(
        eventName,
        parameterString
    )
}
