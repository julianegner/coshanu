package util

expect fun logAnalyticsEvent(
    eventName: String,
    parameters: Map<String, Any> = emptyMap()
)
