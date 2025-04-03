This is the Repository for the game CoShaNu, a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop.
It is deployed at https://cosha.nu

# CoShaNu
The Game CoShaNu lets you select pairs of cards where the elements Color, Shape or Number are the same. The game is over when all pairs are found.
There are two game modes, the simpler where one of color shape and number must be equal and the more complex where two must be equal.
Also, a timer runs, you can try to be faster to make the game more challenging.

# Kotlin Multiplatform
The project is a Kotlin Multiplatform project and has some solutions ypu might find interesting.
As the code is licensed unter MIT license, you can use it for your own projects, even commercial ones.

Interesting solutions are:
- scrolling (see composeApp/src/commonMain/kotlin/App.kt and look for verticalScrollModifier)
- timer (see composeApp/src/commonMain/kotlin/util/Timer.kt)
- Polygon (see composeApp/src/commonMain/kotlin/ui/Polygon.kt and composeApp/src/commonMain/kotlin/ui/Tile.kt)
- Dark Mode (see ui/DarkModeSwitch.kt and usage of UiStateHolder.darkModeState.value)
- State Holders for changing game states and ui states at runtime (see composeApp/src/commonMain/kotlin/game/GameStateHolder.kt and composeApp/src/commonMain/kotlin/ui/UiState.kt)
- Localisation (see composeApp/src/commonMain/resources/strings) and usage by stringResource(Res.string.myString)
- usage of lottie animation (see composeApp/src/commonMain/kotlin/ui/WonAnimation.kt)
- usage of image resources (search for "painterResource(Res.drawable.lost)" in LostImage.kt and Board.kt)
- usage of expect/actual for platform specific code (see composeApp/src/commonMain/kotlin/util/Platform.kt and i.e. composeApp/src/androidMain/kotlin/util/Platform.kt)
- usable Weblink in Text (see composeApp/src/commonMain/kotlin/ui/TextLink.kt and util/util.kt for callUrl (expect/actual))
- toClipboard (see composeApp/src/commonMain/kotlin/util/Clipboard.kt and Clipboard.kt in the platform specific folders)
- enums with localised strings (see composeApp/src/commonMain/kotlin/game/enums/GameState.kt)
- run after delay (see runOnMainAfter() in composeApp/src/commonMain/kotlin/util/Util.kt)

# Development

- Run for web:
```shell
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

open webapp on browser:
http://localhost:8080/

- Test for web:
```shell
./gradlew wasmJsBrowserTest
```

- generate artifact for web:
```shell
./gradlew :composeApp:wasmJsBrowserProductionExecutable

./gradlew :composeApp:wasmJsBrowserDistribution
```
Find the generated artifact in `composeApp/build/dist/wasmJsBrowser/productionExecutable/`

- after adding new (string) resources, run:
```shell
 ./gradlew build
```


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

# deployment

There are not many web hosters that support wasm at the moment, but github pages is one of them.

### Github pages
repository must be public or paid

### deploy kotlin/wasm to github pages
https://medium.com/@schott12521/deploy-kotlin-mutliplatform-wasmjs-to-github-pages-fe295d8b420f

See: https://github.com/julianegner/coshanu/blob/main/.github/workflows/main.yml

### Use your own Domain  for github pages
https://hossainkhan.medium.com/using-custom-domain-for-github-pages-86b303d3918a
