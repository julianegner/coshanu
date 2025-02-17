package game.enums

import coshanu.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource

enum class ShapeEnum(val resourceId: StringResource) {
    CIRCLE(Res.string.circle),
    TRIANGLE(Res.string.triangle),
    SQUARE(Res.string.square),
    PENTAGON(Res.string.pentagon),
    HEXAGON(Res.string.hexagon),
    OCTAGON(Res.string.octagon)
}
