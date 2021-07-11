package com.louis993546.metro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.louis993546.metro.ui.theme.MetroDemoTheme


@Composable
fun HomePage(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        // TODO https://stackoverflow.com/questions/68334723/how-to-do-the-wp8-background-effect-in-jetpack-compose
//        Image(
//            modifier = Modifier.fillMaxWidth(),
//            painter = painterResource(id = R.drawable.image),
//            contentDescription = "background",
//            contentScale = ContentScale.Crop,
//        )
        VerticalTilesGrid(modifier = Modifier.fillMaxSize()) {
            s { HomeTile(iconRes = R.drawable.ic_baseline_local_phone_24) }
            s { HomeTile(iconRes = R.drawable.ic_baseline_message_24) }
            s { HomeTile(iconRes = R.drawable.ic_baseline_map_24) }
            s { HomeTile() }
            s { HomeTile() }
            m { HomeTile(title = "Calculator", iconRes = R.drawable.ic_baseline_calculate_24) }
            s { HomeTile() }
            s { HomeTile() }
            s { HomeTile() }
            l { HomeTile(title = "Tile 10") }
            s { HomeTile() }
            s { HomeTile() }
            m { HomeTile(title = "Tile 13") }
            m { HomeTile(title = "Tile 14") }
            s { HomeTile() }
            s { HomeTile() }
            s { HomeTile() }
        }
    }
}

@Preview
@Composable
fun PreviewHomePage() {
    MetroDemoTheme {
        HomePage()
    }
}
