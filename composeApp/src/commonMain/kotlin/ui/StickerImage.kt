package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import game.enums.ShapeEnum
import game.newTileData


val tileListForSticker = listOf(
    newTileData(Color(0xFF5090FF), ShapeEnum.TRIANGLE, 3),
    newTileData(Color.Red, ShapeEnum.CIRCLE, 1),
    newTileData(Color.Cyan, ShapeEnum.SQUARE, 2),
    newTileData(Color.Green, ShapeEnum.HEXAGON, 3),
    newTileData(Color.LightGray, ShapeEnum.OCTAGON, 4),
    newTileData(Color.Magenta, ShapeEnum.PENTAGON, 5)
)

val textColorForSticker = Color.White
val backgroundColorForSticker = Color.Black
/*
creating images for stickers
 */
@Composable
fun StickerImage() {
    UiStateHolder.darkModeState.value = false

    Column {
        StickerRectangle()
        Spacer(Modifier.height(10.dp))
        StickerRectangleWithExtraText2()
        Spacer(Modifier.height(10.dp))
        // StickerRectangleWithExtraText()
        // Spacer(Modifier.height(10.dp))
        StickerRound()
    }
}

@Composable
private fun StickerRectangle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(backgroundColorForSticker)

    ) {
        CircleOfTiles(
            elements = tileListForSticker,
            modifier = Modifier.width(200.dp).height(100.dp)
        )

        Text(
            text = "cosha.nu",
            color = textColorForSticker,
            fontSize = TextUnit(10f, TextUnitType.Em),
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
private fun StickerRectangleWithExtraText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(backgroundColorForSticker)

    ) {
        CircleOfTiles(
            elements = tileListForSticker,
            modifier = Modifier.width(200.dp).height(100.dp)
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "play",
                color = textColorForSticker,
                fontStyle = FontStyle.Italic,
                fontSize = TextUnit(3f, TextUnitType.Em),
                modifier = Modifier.padding(top = 40.dp)
            )
            Text(
                text = "www.",
                color = textColorForSticker,
                fontSize = TextUnit(2f, TextUnitType.Em),
                modifier = Modifier.padding(top = 50.dp)
            )
        }
        Text(
            text = "cosha.nu",
            color = textColorForSticker,
            fontSize = TextUnit(10f, TextUnitType.Em),
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
private fun StickerRectangleWithExtraText2() {
    Text(
        text = "play",
        color = textColorForSticker,
        fontStyle = FontStyle.Italic,
        fontSize = TextUnit(3f, TextUnitType.Em),
        modifier = Modifier
            .absoluteOffset(x = 140.dp, y = 120.dp)
            .zIndex(1f)
    )
    Text(
        text = "www.",
        color = textColorForSticker,
        fontSize = TextUnit(1.5f, TextUnitType.Em),
        modifier = Modifier
            .absoluteOffset(x = 150.dp, y = 180.dp)
            .zIndex(1f)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(backgroundColorForSticker)

    ) {
        CircleOfTiles(
            elements = tileListForSticker,
            modifier = Modifier.width(200.dp).height(100.dp)
        )

        Text(
            text = "cosha.nu",
            color = textColorForSticker,
            fontSize = TextUnit(10f, TextUnitType.Em),
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
fun StickerRound() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(backgroundColorForSticker)

    ) {
        Text(
            text = "cosha.nu",
            color = textColorForSticker,
            fontSize = TextUnit(2f, TextUnitType.Em),
            modifier = Modifier.absoluteOffset(x = 196.dp, y = 140.dp)
        )
        CircleOfTiles(
            elements = tileListForSticker,
            radius = 100.dp,
            centerX = 200.dp,
            centerY = 200.dp,
            elementSize = 75.dp,
            modifier = Modifier
                .width(150.dp)
                .height(100.dp)
                .offset(x = (-150).dp, y = (-80).dp)
        )
    }
}
