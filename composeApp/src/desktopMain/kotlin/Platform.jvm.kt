class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val mobile: Boolean = false
}

actual fun getPlatform(): Platform = JVMPlatform()
