package com.louis993546.metro

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.louis993546.metro.ui.theme.MetroDemoTheme


@Composable
fun HomePage(
    modifier: Modifier = Modifier,
) {
    VerticalTilesGrid(modifier = modifier) {
        s { HomeTile(title = "", iconRes = R.drawable.ic_baseline_local_phone_24) }
        s { HomeTile(title = "", iconRes = R.drawable.ic_baseline_message_24) }
        s { HomeTile(title = "", iconRes = R.drawable.ic_baseline_map_24) }
        s { HomeTile(title = "") }
        s { HomeTile(title = "") }
        m { HomeTile(title = "Tile 6") }
        s { HomeTile(title = "") }
        s { HomeTile(title = "") }
        s { HomeTile(title = "") }
        l { HomeTile(title = "Tile 10") }
        s { HomeTile(title = "") }
        s { HomeTile(title = "") }
        m { HomeTile(title = "Tile 13") }
        m { HomeTile(title = "Tile 14") }
        s { HomeTile(title = "") }
        s { HomeTile(title = "") }
        s { HomeTile(title = "") }
    }
}

@Preview
@Composable
fun PreviewHomePage() {
    MetroDemoTheme {
        HomePage()
    }
}
