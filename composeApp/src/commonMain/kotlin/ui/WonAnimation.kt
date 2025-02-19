package ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import coshanu.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.ExperimentalCompottieApi
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter

@OptIn(ExperimentalResourceApi::class, ExperimentalCompottieApi::class)
@Composable
fun WonAnimation() {

    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/lottie_fireworks.json").decodeToString()
        )
    }

    Image(
        painter = rememberLottiePainter(
            composition = composition,
            iterations = Compottie.IterateForever
        ),
        contentDescription = "Lottie animation"
    )
}
