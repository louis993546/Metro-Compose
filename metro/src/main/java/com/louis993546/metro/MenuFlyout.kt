package com.louis993546.metro

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import java.util.Locale

@Composable
fun MenuFlyoutItem(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    Text(
        modifier = modifier
            .then(
                if (enabled && onClick != null)
                    Modifier.clickable { onClick() }
                else
                    Modifier
            ),
        text = text.lowercase(Locale.ROOT),
        size = 26.sp,
        weight = FontWeight.Light,
        color =
            if (enabled)
                LocalBackgroundColor.current
            else
                LocalBackgroundColor.current.copy(alpha = 0.35f)
    )
}

@OptIn(ExperimentalComposeUiApi::class
)
@Composable
fun MenuFlyout(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Popup(
        properties = PopupProperties(
            focusable = true,
            clippingEnabled = true,
            usePlatformDefaultWidth = false,
            excludeFromSystemGesture = false,
        ),
        onDismissRequest = onDismissRequest
    ) {
        AnimatedVisibility(
            modifier = Modifier,
            visible = enabled,
            enter = expandVertically(expandFrom = Alignment.Top) { 20 } + fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(LocalTextOnBackgroundColor.current)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 26.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    content()
                }
            }
        }
    }
}
