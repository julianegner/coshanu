package game

import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource

enum class GameState(val resourceId: StringResource) {
    RESTART(Res.string.restart),
    STARTING(Res.string.starting),
    WON(Res.string.won),
    LOST(Res.string.lost),
    RUNNING(Res.string.running),
    LEVEL_CHANGE(Res.string.level_change)
}
