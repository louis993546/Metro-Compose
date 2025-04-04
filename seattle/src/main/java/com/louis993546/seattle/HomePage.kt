package com.louis993546.seattle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.louis993546.metro.Text
import com.louis993546.metro.demo.VerticalTilesGrid

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        VerticalTilesGrid(
            modifier = Modifier.fillMaxSize(),
            gap = 12.dp,
        ) {
            // Always inject launcher settings "app" here
            s {
                Box(
                    modifier = Modifier
                        .background(color = Color.Green)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Testing",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
