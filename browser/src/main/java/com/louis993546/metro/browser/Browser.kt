package com.louis993546.metro.browser

import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.louis993546.metro.ApplicationBar
import com.louis993546.metro.CircleButton
import com.louis993546.metro.Text

@Composable
fun Browser(
    modifier: Modifier = Modifier,
) {
    var url by remember { mutableStateOf("https://www.google.com") }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        AndroidView(
            modifier = Modifier.weight(1f),
            factory = { context -> WebView(context) }
        ) { webView ->
            webView.loadUrl("https://www.google.com")
        }

        ApplicationBar(
            modifier = Modifier.fillMaxWidth(),
        ) {
            CircleButton(
                modifier = Modifier.size(48.dp)
            ) {
                Image(
//                    modifier = Modifier.padding(4.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                    contentDescription = "refresh",
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.White), // TODO get color from theme
            ) {
                Text(
                    modifier = Modifier.matchParentSize(),
                    text = url,
                    color = Color.Black, // TODO get color from theme
                )
            }
        }
    }
}