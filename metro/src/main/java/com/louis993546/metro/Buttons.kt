package com.louis993546.metro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    pressed: Boolean = false,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color = if (pressed) LocalTextOnBackgroundColor.current else Color.Transparent)
            .border(
                width = 3.dp,
                color = if (pressed) LocalTextOnBackgroundColor.current else LocalTextOnBackgroundColor.current,
                shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}