package com.louis993546.metro

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.clip(CircleShape)
            .border(width = 2.dp, color = LocalTextOnBackgroundColor.current, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}