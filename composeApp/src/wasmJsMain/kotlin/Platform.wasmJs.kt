class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
    override val mobile: Boolean = false // todo use UAParser to determine if mobile or not
}

actual fun getPlatform(): Platform = WasmPlatform()
