package com.louis993546.metro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * TODO decide what naming scheme I should use: WP8 name, or Andorid-esque name
 * TODO should I use composition local to override default color of text?
 */
@Composable
fun MessageBox(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(), // TODO cannot expose this since click outside does not work
    ) {
        Column(modifier = modifier) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = LocalButtonColor.current), // TODO need another surface color
                content = content,
            )

            // Push the visible part of the dialog to the top
            Spacer(modifier = Modifier
                .weight(1f)
                .defaultMinSize(minHeight = 32.dp))
        }
    }
}