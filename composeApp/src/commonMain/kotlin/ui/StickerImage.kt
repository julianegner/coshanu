package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import game.enums.ShapeEnum
import game.newTileData

/*
creating an image for a sticker
 */
@Composable
fun StickerImage() {
    UiStateHolder.darkModeState.value = false
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .background(Color.Black)

    ){
        CircleOfTiles(
            listOf(
                newTileData(Color(0xFF5090FF), ShapeEnum.TRIANGLE, 3),
                newTileData(Color.Red, ShapeEnum.CIRCLE, 1),
                newTileData(Color.Cyan, ShapeEnum.SQUARE, 2),
                newTileData(Color.Green, ShapeEnum.HEXAGON, 3),
                newTileData(Color.LightGray, ShapeEnum.OCTAGON, 4),
                newTileData(Color.Magenta, ShapeEnum.PENTAGON, 5)
            ),
            modifier = Modifier.width(200.dp).height(100.dp)
        )
        Text(
            text = "cosha.nu",
            color = Color.White,
            fontSize = TextUnit(10f, TextUnitType.Em),
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}
