This is the Repository for the game CoShaNu, a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop.
It is deployed at https://cosha.nu

# CoShaNu
The Game CoShaNu lets you select pairs of cards where the elements Color, Shape or Number are the same. The game is over when all pairs are found.
There are two game modes, the simpler where one of color shape and number must be equal and the more complex where two must be equal.
Also, a timer runs, you can try to be faster to make the game more challenging.

# Kotlin Multiplatform
The project is a Kotlin Multiplatform project and has some solutions you might find interesting.
As the code is licensed unter MIT license, you can use it for your own projects, even commercial ones.

### Interesting solutions are:

- ##### scrolling
Add scrolling to the App

<details>
  <summary>Click to see details</summary>

see [verticalScrollModifier in App.kt](composeApp/src/commonMain/kotlin/App.kt#L108)
> val verticalScrollModifier = mutableStateOf(Modifier.verticalScroll( rememberScrollState())

```
        Row (
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = verticalScrollModifier.value
                .fillMaxSize()
        )
        {
        [App content]
        }
```
</details>


- ##### Timer
This counts the seconds since starting the timer when running,
while not changing it when not running to show the time it took to finish the game.

see [Timer.kt](composeApp/src/commonMain/kotlin/util/Timer.kt#L10)

- ##### Polygon
Creating an Outline of a polygon with a given number of sides.
This is used in polygonBox to create the polygon shapes for the game.

see [Polygon.kt](composeApp/src/commonMain/kotlin/ui/Polygon.kt#L15) 
and [Tile.kt](composeApp/src/commonMain/kotlin/ui/Tile.kt#L52-L70)

- ##### Dark Mode
see [DarkModeSwitch.kt](ui/DarkModeSwitch.kt) and usage of UiStateHolder.darkModeState.value
Also see [Color.modeDependantColor](composeApp/src/commonMain/kotlin/util/ColorExtension.kt#L18) for the usage of dark mode colors.

- ##### State Holders
for changing game states and ui states at runtime

see [GameStateHolder.kt](composeApp/src/commonMain/kotlin/game/GameStateHolder.kt) 
and [UiState.kt](composeApp/src/commonMain/kotlin/ui/UiState.kt)

- #### Localisation

You need localization (L10n) to support multiple languages in your app.

<details>
  <summary>Click to see details</summary>
For Translation, see the [resource files](composeApp/src/commonMain/composeResources/values/strings.xml) 
and usage by stringResource(Res.string.myString) like

```kotlin
        Text(
            text = stringResource(Res.string.title),
            fontSize = titleTextSize.value
        )
```
</details>

- ##### enums with localised strings
You can use enums with localised strings for display
<details>
  <summary>Click to see details</summary>
see [GameState.kt](composeApp/src/commonMain/kotlin/game/enums/GameState.kt)

```kotlin
enum class GameState(val resourceId: StringResource) {
    RESTART(Res.string.restart),
    STARTING(Res.string.starting),
    WON(Res.string.won),
    LOST(Res.string.lost),
    RUNNING(Res.string.running),
    LEVEL_CHANGE(Res.string.level_change),
    LOAD_GAME(Res.string.load_game)
}
```
</details>

- ##### change language at runtime
see 
[LanguageChooser.kt](composeApp/src/commonMain/kotlin/ui/LanguageChooser.kt)

needs plugin com.hyperether.localization  
and dependency dev.carlsen.flagkit:flagkit 
(see [gradle file](composeApp/build.gradle.kts))

for hyperether.localization, see https://medium.com/@nikola.hadzic.n/compose-multiplatform-localization-plugin-simplified-multilingual-support-57682d0d10c8

- ##### usage of lottie animation
Use lottie animations

see [WonAnimation.kt](composeApp/src/commonMain/kotlin/ui/WonAnimation.kt)

- ##### usage of image resources 
Use of Image resources in the app.
<details>
  <summary>Click to see details</summary>
Image Files (e.g. png) are stored in composeApp/src/commonMain/composeResources/drawable

and then used like this:
```
    Image(
        painter = painterResource(Res.drawable.lost),
        contentDescription = null,
        modifier = Modifier.size(350.dp)
    )
```
</details>

- ##### usage of expect/actual for platform specific code

It is not always possible to write code that works on all platforms,
therefore kotlin multiplatform supports expect/actual, so that you can write the expect function in commonMain, so that the App knwos that there should be a function,
and then implement the actual function in the platform specific folders, like androidMain, iosMain, desktopMain, wasmJsMain.
see [commonMain Platform.kt](composeApp/src/commonMain/kotlin/util/Platform.kt) 
and i.e. [androidMain Platform.kt](composeApp/src/androidMain/kotlin/util/Platform.kt)

- ##### usable Weblink in Text
Unlike in HTML, where a link is easy, in Compose you need to make a Text clickable and style it so that it looks like a link
see [TextLink.kt](composeApp/src/commonMain/kotlin/ui/TextLink.kt) 

Also, to really click a Link and call an URL, you need to use the expect/actual mechanism,
because it is different in different platforms.
see [callUrl function](util/util.kt) (expect/actual)

- ##### toClipboard 
This is a function that copies a string to the clipboard,
which is different in different platforms.

see [Clipboard.kt](composeApp/src/commonMain/kotlin/util/Clipboard.kt) 
and Clipboard.kt in the platform specific folders, e.g.
[androidMain Clipboard.kt](composeApp/src/androidMain/kotlin/util/Clipboard.kt)

- ##### run after delay 
see [runOnMainAfter function](composeApp/src/commonMain/kotlin/util/util.kt#L8)

- ##### show app loading info in wasm web page
When the app is starting up in web/wasm, it takes some seconds until the app is ready.
To show something else than a white page, the app shows a loading message.

<details>
  <summary>Click to see details</summary>

Add
> document.getElementById("loader-container")?.remove()

to composeApp/src/wasmJsMain/kotlin/main.kt

```
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
document.getElementById("loader-container")?.remove()
CanvasBasedWindow(canvasElementId = "ComposeTarget") { App() }
}
```

and add 

> <div id="loader-container">Loading coshanu ...</div>

to composeApp/src/wasmJsMain/resources/index.html

````
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CoShaNu</title>
    <link id="page_favicon"
          href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAh0lEQVQ4T7WS2w3AIAhFYZ/GKdylM3UXpzDdh0qU+MBEjdZPhJPDVYTNg5vz0AVYa8k5V90RECGg6lcFHhYrgfCw1FrIHOAJgDsihoDVTA6GGBYVx7EFZxIDzQbTgBwoQw6uMPbudiiDi7LimwzJGALvU0T159MrFABZMSRWfKTfAYtZbL/CBxlxMBGC/SHMAAAAAElFTkSuQmCC"
          rel="icon" type="image/x-icon">

    <script type="application/javascript" src="composeApp.js"></script>
</head>
<body>
<div id="loader-container">Loading coshanu ...</div>
<canvas id="ComposeTarget"></canvas>
</body>
</html>
````
</details>

- ##### pass withImpressum parameter to the app 
To be able to show the impressum in the web app, a parameter can be passed to the app.
The Impressum is shown in the deployed web app at https://cosha.nu but not the one hosted at https://jegner.itch.io/coshanu.
see [gradle file](composeApp/build.gradle.kts) and [InfoArea.kt](composeApp/src/commonMain/kotlin/ui/InfoArea.kt)

- ##### usage of Snackbar, including closing after defined time
Snackbar usually shows a message for short or long time - here we can set an exact time
see [App.kt](App.kt#L89-L95) and [SettingsArea.kt](ui/SettingsArea.kt#L132)

- ##### usage of extension functions and values for colors
extension functions are a standard feature of kotlin itself, here used to add functionality to the Color class
(see [ColorExtension.kt](composeApp/src/commonMain/kotlin/util/ColorExtension.kt))

- ##### tooltip for all compile targets 
This is self written, as no library exists that supports all targets
instead of just for Android like TooltipBox or just for Desktop like TooltipArea
(see [Tooltip.kt](composeApp/src/commonMain/kotlin/ui/Tooltip.kt))

- ##### replace colors with patterns for the colorless version of the tiles
To make the game accessible for colorblind people, the game can be played in a colorless mode.

<details>
  <summary>Click to see details</summary>
See [Color.toPattern function](util/ColorExtension.kt#L77-L87) 
and [Pattern.kt](game/enums/Pattern.kt),
[getAdditionalModifier function](ui/Tile.kt#L208-L225)
and [TileData.colorlessTutorialString function](game/TileData.kt#L54-L62) 
and [TileData.tooltipText function](game/TileData.kt#L65-L671)
</details>

- ##### special string resources
If you need special string resources only in some cases and the standardstring resources otherwise,
you can use (extension) functions for this

see [Pattern.getWithStringResourceId](game/enums/Pattern.kt#L37) 
and [Pattern.getShortStringResourceId](game/enums/Pattern.kt#L43)

- ##### GenericSwitch UI component
simple switch component with only few needed parameters
see [GenericSwitch.kt](composeApp/src/commonMain/kotlin/ui/GenericSwitch.kt)


# Kotlin Multiplatform Development

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.

## additional inforamtion about Kotlin Multiplatform

create a new KMP project
https://kmp.jetbrains.com/

Overview Kotlin Multiplatform Libraries
https://klibs.io

material3 component description
https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/

## deployment

There are not many web hosters that support wasm at the moment, but github pages is one of them.

### Github pages
repository must be public or paid

### deploy kotlin/wasm to github pages
https://medium.com/@schott12521/deploy-kotlin-mutliplatform-wasmjs-to-github-pages-fe295d8b420f

See: https://github.com/julianegner/coshanu/blob/main/.github/workflows/main.yml

### Use your own Domain  for github pages
https://hossainkhan.medium.com/using-custom-domain-for-github-pages-86b303d3918a

to see if changes you made to your domain at your webhoster are propagated worldwide:
https://www.whatsmydns.net

## used images
composeApp/src/commonMain/composeResources/drawable
- icon.png self-made, so MIT license
- lost.png generated (https://iconscout.com/licenses#iconscout) https://iconscout.com/ai/illustration-generator
- Noto_Emoji_Fingerpointing.png (Apache License, Version 2.0) from https://commons.wikimedia.org/wiki/File:Noto_Emoji_Oreo_1f449.svg
- stopwatch.png (MIT license) from https://iconduck.com/icons/157837/stopwatch
- setting.png (Apache License, Version 2.0) from https://iconduck.com/icons/56992/setting

patterns for the colorless version of the tiles:
- waves.png <a href="https://www.flaticon.com/free-icons/water" title="water icons">Water icons created by Elias Bikbulatov - Flaticon</a>
- dot_grid.png <a href="https://www.flaticon.com/free-icons/dots" title="dots icons">Dots icons created by Andrejs Kirma - Flaticon</a>
- plant.png <a href="https://www.flaticon.com/free-icons/leaf" title="leaf icons">Leaf icons created by Pixel perfect - Flaticon</a>
- fire.png <a href="https://www.flaticon.com/free-icons/fire" title="fire icons">Fire icons created by Those Icons - Flaticon</a>
- pattern_lines_up.png <a href="https://www.flaticon.com/free-icons/abstract" title="abstract icons">Abstract icons created by Creatype - Flaticon</a>
- pattern_lines_crossed.png <a href="https://www.flaticon.com/free-icons/pattern" title="pattern icons">Pattern icons created by Irfansusanto20 - Flaticon</a>
- fish.png <a href="https://www.flaticon.com/free-icons/ocean" title="ocean icons">Ocean icons created by Freepik - Flaticon</a>
- cat.png <a href="https://www.flaticon.com/free-icons/black-cat" title="black cat icons">Black cat icons created by PLANBSTUDIO - Flaticon</a>

some images where not good to recognize, so a grid of them is better:
plant.png -> plant_pattern.png
fire.png -> fire_pattern.png
first, create a 4x4 collage with foror,
then remove the background with remove.bg (also shrinks the file size)
https://www.fotor.com/design/project/d5ed6e5f-b847-4a8c-88db-7b2578eb31c3/collage
https://www.remove.bg/de/upload

composeApp/src/commonMain/composeResources/files
- lottie_fireworks.json (Lottie simple license https://lottiefiles.com/page/license) https://lottiefiles.com/free-animation/fireworks-qE3wiVxSIg


# Development

### Web
- Run for web:
```shell
./gradlew clean :composeApp:wasmJsBrowserDevelopmentRun
```
with Impressum:
```shell
./gradlew clean :composeApp:wasmJsBrowserDevelopmentRun -PwithImpressum=true
```

open webapp on browser:
http://localhost:8080/

- Tests:
```shell
./gradlew clean
./gradlew wasmJsBrowserTest
./gradlew desktopTest
./gradlew deviceAndroidTest
./gradlew iosX64Test
```

- generate artifact for web:
```shell
./gradlew :composeApp:wasmJsBrowserProductionExecutable

./gradlew clean :composeApp:wasmJsBrowserDistribution

./gradlew clean :composeApp:wasmJsBrowserDistribution -PwithImpressum=true
```
Find the generated artifact in `composeApp/build/dist/wasmJsBrowser/productionExecutable/`

### Desktop
run desktop application:
```shell
./gradlew :composeApp:runDistributable
```
### JVM
generate uber/fat jar:
```shell
./gradlew :composeApp:packageUberJarForCurrentOS
```
Find the generated artifact in `composeApp/build/compose/jars/`

run it with 
```shell
java -jar composeApp/build/compose/jars/de.julianegner.coshanu-linux-x64-1.0.0.jar
``` 

### generate jar for JVM: (NOT Working!!)
```shell
./gradlew :composeApp:desktopJar
```
Find the generated artifact `composeApp/build/libs/composeApp-desktop.jar`
run it with 
```shell
java -jar composeApp-desktop.jar
```

### Debian and compatible Linux
- generate debian package:
```shell
./gradlew :composeApp:packageDeb
``` 
location of .deb file:
```shell
composeApp/build/compose/binaries/main/deb/coshanu_1.0.0_amd64.deb
```
install:
```shell
sudo apt install --reinstall ./coshanu_1.0.0_amd64.deb
```

### Android
- generate apk for Android:
```shell
./gradlew assembleRelease
```
Find the generated artifact `composeApp/build/outputs/apk/release/coshanu-release.apk`

### rebuild
- after adding new (string) resources, run:
```shell
 ./gradlew build
```
## adding a language/localisation
- add a new folder inside of `composeApp/src/commonMain/composeResources with the name values-<language code> (e.g. values-de for german)
- add a new file `strings.xml` inside of the new folder
- add the new strings to the new file (you can copy the existing string data from an existing file and change the values)
- run `./gradlew clean` to remove the old resources
- run `./gradlew build` or `/gradlew :composeApp:wasmJsBrowserDevelopmentRun` or other run to generate the new resources

The new language will be available in the app, including in the language chooser.
## Release

### 1. set version in App.kt
```
    val programVersion = "1.0.0"
```

### 2. set Tag in build.gradle.kts
```
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "coshanu"
            packageVersion = "1.0.0"
        }
```

### 3. set git version tag
```
    git tag -a v1.0.0 -m "Release version 1.0.0"
```
### 4. push code and tag to remote
```
    git push origin --tags
```
This pushes the tag to the remote repository and the release will be build by github actions.

### 5. create release on github
run github action 
  Build (and Release)

