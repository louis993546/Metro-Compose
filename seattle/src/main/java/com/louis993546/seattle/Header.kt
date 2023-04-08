package com.louis993546.seattle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.LocalBackgroundColor
import com.louis993546.metro.Text

@Composable
fun Header(
    modifier: Modifier = Modifier,
    letter: Char,
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .background(color = LocalBackgroundColor.current)
                .size(48.dp)
                .border(width = 2.dp, color = LocalAccentColor.current)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.BottomStart),
                text = letter.toString(),
                size = 24.sp,
            )
        }
    }
}
