import game.enums.ScreenType
import ui.UiStateHolder

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

val isPlatformWasm = ("Web with Kotlin/Wasm" == getPlatform().name)
val isPlatformAndroid = getPlatform().name.startsWith("Android")
val isPlatformIos = getPlatform().name.startsWith("iOS")
val isPlatformMobile = isPlatformAndroid || isPlatformIos

val isLandscape: Boolean = UiStateHolder.screenType.value == ScreenType.LANDSCAPE
val isPortrait: Boolean = UiStateHolder.screenType.value == ScreenType.PORTRAIT

val isClickPlatform = !isPlatformAndroid && !isPlatformIos && !(isPlatformWasm && isPortrait)
val landscapeOrAndroid: Boolean = isLandscape || isPlatformAndroid
