# Keep Java standard library classes
-keep class java.lang.** { *; }
-keep class java.util.** { *; }

-libraryjars /lib/rt.jar

# Keep classes related to ProGuard's warnings
-keep class org.jetbrains.skiko.** { *; }
-keep class org.slf4j.** { *; }
-keep class util.Util_desktopKt { *; }

# Keep exception classes
-keep class java.lang.Exception { *; }
-keep class java.lang.Error { *; }

# Keep logging classes
-keep class java.io.** { *; }

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }

# Keep Compose runtime and UI classes
-keep class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }

# Keep kotlinx-coroutines classes
-keep class kotlinx.coroutines.** { *; }

# Keep Compottie library classes
-keep class io.github.alexzhirkevich.compottie.** { *; }

# Keep Multiplatform Settings library classes
-keep class com.russhwolf.settings.** { *; }

# Keep FlagKit library classes
-keep class dev.carlsen.flagkit.** { *; }

# Keep Compose Icons library classes
-keep class br.com.devsrsouza.compose.icons.** { *; }

# Keep Java standard library classes
-keep class java.lang.** { *; }
-keep class java.util.** { *; }

# Keep exception and error classes
-keep class java.lang.Exception { *; }
-keep class java.lang.Error { *; }

# Keep logging classes
-keep class java.io.** { *; }

# Keep Skiko library classes
-keep class org.jetbrains.skiko.** { *; }

# Keep SLF4J logging classes
-keep class org.slf4j.** { *; }

# Keep desktop-specific utility classes
-keep class util.Util_desktopKt { *; }

-dontwarn androidx.compose.**

-ignorewarnings
-keepattributes
