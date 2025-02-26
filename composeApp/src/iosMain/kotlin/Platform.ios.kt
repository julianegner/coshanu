import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val mobile: Boolean = true
}

actual fun getPlatform(): Platform = IOSPlatform()
