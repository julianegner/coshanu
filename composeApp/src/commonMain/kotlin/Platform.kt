interface Platform {
    val name: String
    val mobile: Boolean
}

expect fun getPlatform(): Platform
