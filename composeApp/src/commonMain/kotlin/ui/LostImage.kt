package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coshanu.composeapp.generated.resources.Res
import coshanu.composeapp.generated.resources.lost
import org.jetbrains.compose.resources.painterResource

@Composable
fun LostImage() {
    //  https://medium.com/@abdulbasit5361234/accessing-resources-in-kmp-font-images-strings-f2927c668a88

    Image(
        painter = painterResource(Res.drawable.lost),
        contentDescription = null,
        modifier = Modifier.size(350.dp)
    )
}
