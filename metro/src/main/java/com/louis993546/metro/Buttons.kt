package com.louis993546.metro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String
) {
    // TODO Button indication
    Box(
        modifier = modifier
            .background(color = Color.Transparent)
            .border(
                width = 3.dp,
                color = LocalTextOnBackgroundColor.current
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .padding(horizontal = 12.dp),

            text = text,
            color = LocalTextOnBackgroundColor.current,
        )
    }
}

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    color: Color = LocalTextOnBackgroundColor.current,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color = Color.Transparent)
            .border(
                width = 3.dp,
                color = color,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
