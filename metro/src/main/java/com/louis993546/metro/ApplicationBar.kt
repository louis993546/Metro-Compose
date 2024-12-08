package com.louis993546.metro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ApplicationBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = LocalAccentColor.current,
    iconColor: Color = LocalTextOnAccentColor.current,
    count: Int,
    icon: @Composable (index: Int) -> Unit,
    onButtonClicked: (index: Int) -> Unit,
) {
    ApplicationBar(
        modifier = modifier.defaultMinSize(minHeight = 36.dp),
        backgroundColor = backgroundColor,
    ) {
        (0 until count).forEach { index ->
            CircleButton(
                modifier = Modifier
                    .size(42.dp)
                    .clickable { onButtonClicked(index) },
                color = iconColor,
            ) {
                icon(index)
            }
            // TODO minimize button
        }
    }
}

/**
 * TODO create a version that has minimize button
 */
@Composable
fun ApplicationBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = LocalAccentColor.current,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .background(backgroundColor)
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),
        horizontalArrangement = Arrangement.spacedBy(
            16.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}