import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.Properties

val localProperties = file("../local.properties")
val properties = if (localProperties.exists()) {
    Properties().apply { load(localProperties.inputStream()) }
} else {
    null
}

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.hyperether.localization") version "1.1.1"
}

val withImpressum: Boolean = project.findProperty("withImpressum")?.toString()?.toBoolean() ?: false

kotlin {
    //macosX64("native") { // on macOS
        // linuxX64("native") // on Linux
    mingwX64("native") {// on Windows
        binaries {
            executable()
        }
    }

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoShaNu"
            isStatic = true
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "CoShaNu"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js" // must be named like this, otherwise it's not working
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
            testTask {
                useKarma {
                    // useFirefox()
                    useChromium()
                    // useChrome()
                    // useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting
        val desktopTest by getting
        sourceSets["commonMain"].kotlin.srcDirs(
            "build/generated/kotlin",
            File(
                layout.buildDirectory.get().asFile.path,
                "generated/compose/resourceGenerator/kotlin/commonCustomResClass"
            )
        )

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation("io.github.alexzhirkevich:compottie:2.0.0-rc04")
            implementation("io.github.alexzhirkevich:compottie-dot:2.0.0-rc04")
            implementation("io.github.alexzhirkevich:compottie-network:2.0.0-rc04")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

            // This module can cause binary incompatibilities. Please read its description first
            implementation("io.github.alexzhirkevich:compottie-resources:2.0.0-rc04")

            implementation("dev.carlsen.flagkit:flagkit:1.1.0")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")

            implementation("br.com.devsrsouza.compose.icons:octicons:1.1.1")
            implementation(libs.lexilabs.basic.sound)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(kotlin("stdlib"))

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
            // implementation("com.guardsquare:proguard-gradle:7.7.0")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        // Adds the desktop test dependency
        desktopTest.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlin.test)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
        }
    }
}

android {
    namespace = "de.julianegner.coshanu"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "de.julianegner.coshanu"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/MANIFEST.MF"
        }
    }
    signingConfigs {
        create("release") {
            storeFile = file("coshanu-android-release.jks")
            storePassword = properties?.getProperty("storePassword") ?: ""
            keyAlias = "coshanu-android"
            keyPassword = properties?.getProperty("keyPassword") ?: ""
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    applicationVariants.all {
        outputs.all {
            val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            output.outputFileName = "coshanu-${buildType.name}.apk"
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "coshanu"
            packageVersion = "1.3.0"
        }
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
}

tasks.register("generateWithImpressumConstant") {
    val outputDir = File(layout.buildDirectory.get().asFile.path, "generated/kotlin")
    outputs.dir(outputDir)
    doLast {
        val file = File(outputDir, "WithImpressum.kt")
        println("withImpressum: $withImpressum")
        println("writing file: $file")
        file.parentFile.mkdirs()
        file.writeText(
            """
            object WithImpressum {
                const val withImpressum: Boolean = $withImpressum
            }
            """.trimIndent()
        )
    }
}

tasks.named("generateTranslateFile").configure {
    dependsOn("generateWithImpressumConstant")
}
