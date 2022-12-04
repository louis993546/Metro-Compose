package com.louis993546.metro.browser

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.louis993546.metro.ApplicationBar
import com.louis993546.metro.CircleButton
import com.louis993546.metro.Text

@Composable
fun Browser(
    modifier: Modifier = Modifier,
) {
    val webViewState = rememberWebViewState(url = "https://www.google.com")

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        WebView(
            modifier = Modifier.weight(1f),
            state = webViewState,
            onCreated = { webView ->
                webView.settings.javaScriptEnabled = true
            }
        )

        ApplicationBar(
            modifier = Modifier.fillMaxWidth(),
        ) {
            RefreshButton()
            AddressBar(url = webViewState.content.getCurrentUrl() ?: "wtf")
            ThreeDotsButton()
        }
    }
}

@Composable
fun RefreshButton(
    modifier: Modifier = Modifier,
) {
    CircleButton(
        modifier = modifier.size(48.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
            contentDescription = "refresh",
        )
    }
}

@Composable
fun RowScope.AddressBar(
    modifier: Modifier = Modifier,
    url: String,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .weight(1f)
            .background(color = Color.White), // TODO get color from theme
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = url,
            color = Color.Black, // TODO get color from theme
            modifier = Modifier.padding(4.dp),
            maxLine = 1,
        )
    }
}

@Composable
fun ThreeDotsButton(
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxHeight()) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ic_3_dots),
            contentDescription = "More Options",
        )
    }
}
