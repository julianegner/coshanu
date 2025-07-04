package game.enums

import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.cat
import coshanu.composeapp.generated.resources.dot_grid
import coshanu.composeapp.generated.resources.fire_pattern
import coshanu.composeapp.generated.resources.fish
import coshanu.composeapp.generated.resources.pattern_cat
import coshanu.composeapp.generated.resources.pattern_dot_grid
import coshanu.composeapp.generated.resources.pattern_fire
import coshanu.composeapp.generated.resources.pattern_fish
import coshanu.composeapp.generated.resources.pattern_lines_crossed
import coshanu.composeapp.generated.resources.pattern_lines_up
import coshanu.composeapp.generated.resources.pattern_plant
import coshanu.composeapp.generated.resources.pattern_waves
import coshanu.composeapp.generated.resources.plant_pattern
import coshanu.composeapp.generated.resources.waves
import coshanu.composeapp.generated.resources.with_pattern_dot_grid
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.DrawableResource

enum class Pattern(
    val drawableResource: DrawableResource,
    val stringResourceId: StringResource,
    val withStringResourceId: StringResource
) {
    Waves(Res.drawable.waves , Res.string.pattern_waves, Res.string.pattern_waves),
    Plant(Res.drawable.plant_pattern, Res.string.pattern_plant, Res.string.pattern_plant),
    Fire(Res.drawable.fire_pattern, Res.string.pattern_fire, Res.string.pattern_fire),
    DotGrid(Res.drawable.dot_grid, Res.string.pattern_dot_grid, Res.string.with_pattern_dot_grid),
    LinesUp(Res.drawable.pattern_lines_up, Res.string.pattern_lines_up, Res.string.pattern_lines_up),
    LinesCrossed(Res.drawable.pattern_lines_crossed, Res.string.pattern_lines_crossed, Res.string.pattern_lines_crossed),
    Fish(Res.drawable.fish, Res.string.pattern_fish, Res.string.pattern_fish),
    Cat(Res.drawable.cat, Res.string.pattern_cat, Res.string.pattern_cat);
}
