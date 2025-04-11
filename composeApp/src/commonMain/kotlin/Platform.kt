import game.enums.ScreenType
import ui.UiStateHolder

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

val isPlatformWasm = ("Web with Kotlin/Wasm" == getPlatform().name)
val isPlatformAndroid = getPlatform().name.startsWith("Android")

val landscapeOrAndroid: Boolean = UiStateHolder.screenType.value == ScreenType.LANDSCAPE || isPlatformAndroid

// set to false for creating a deployable zip file for itch.io with
// ./gradlew :composeApp:wasmJsBrowserDistribution
// find created files in composeApp/build/dist/wasmJsBrowser/productionExecutable/
// zip it and upload to itch.io
val withImpressum = true
