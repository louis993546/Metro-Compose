package com.louis993546.skylight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import com.louis993546.metro.ApplicationBar
import com.louis993546.metro.MetroColor
import com.louis993546.metro.MetroTheme
import com.louis993546.metro.Pages
import com.louis993546.metro.Text

class SkylightActivity : ComponentActivity() {
    private val pages = listOf(
        "Follow",
        "Discovery",
        "TODO other custom feed"
    )

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO make it look just like the text-heavy Twitter App
            //   https://www.youtube.com/watch?v=dz-Dp9L8l78
            //   then make it work for 2024: gestures, image/videos, re-tweet & quote tweet, etc.
            MetroTheme(
                accentColor = MetroColor.cyan,
            ) {
                Column {
                    Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars))
                    Pages(
                        pageTitles = pages,
                        modifier = Modifier.weight(1f),
                    ) { pageNumber ->
                        Text(text = pages[pageNumber])
                    }
                    ApplicationBar(
                        modifier = Modifier.fillMaxWidth(),
                        count = 4,
                        icon = { },
                        onButtonClicked = {},
                    )
                }
            }
        }
    }
}
