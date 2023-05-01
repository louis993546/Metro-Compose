package com.louis993546.metro

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ApplicationBar(
    modifier: Modifier = Modifier,
    count: Int,
    icon: @Composable (index: Int) -> Unit,
    onButtonClicked: (index: Int) -> Unit,
) {
    ApplicationBar(modifier = modifier.defaultMinSize(minHeight = 36.dp)) {
        (0 until count).forEach { index ->
            CircleButton(
                modifier = Modifier
                    .size(42.dp)
                    .clickable { onButtonClicked(index) }
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
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .background(LocalButtonColor.current)
            .padding(vertical = 10.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(
            16.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}