package com.louis993546.metro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import timber.log.Timber
import kotlin.math.roundToInt


enum class SwitchState {
    ON, OFF;

    fun flip(): SwitchState = when (this) {
        ON -> OFF
        OFF -> ON
    }
}

@Composable
fun Switch(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    switchState: SwitchState,
    onSwitchStateChange: (newSwitchState: SwitchState) -> Unit,
) {
    // pre-define the whole size, probably should not be overrideable
    // draw a black outline rectangle
    // draw a black filled rectangle with white border
    // add gesture support
    // and then somehow add the current theme color on the left of the draggable rectangle

    Box(
        modifier = modifier
            .size(80.dp, 32.dp)
            .clickable {
                onSwitchStateChange(switchState.flip())
            },
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize()
                .border(2.dp, Color.Black),
        )
        var offsetX by remember { mutableStateOf(0f) }
        val maxOffset = with(LocalDensity.current) { (80 - 16).dp.toPx() }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState {
                        Timber.tag("metro").d("offset: $offsetX, delta: $it")
                        val proposeNewValue = offsetX + it
                        if (proposeNewValue < 0) { // TODO I assume this starts with false, but that's not always true
                            Timber.tag("metro").d("User dragged beyond OFF, ignored.")
                        } else if (proposeNewValue > maxOffset) {
                            Timber.tag("metro").d("User dragged beyond ON, ignored.")
                        } else {
                            offsetX = proposeNewValue
                        }
                    },
                    onDragStopped = {
                        // TODO do a position check first, to force it to flip to either side

                        val diffFromMax = maxOffset - offsetX
                        when {
                            (offsetX < 0.1) && switchState == SwitchState.ON -> {
                                onSwitchStateChange(SwitchState.OFF)
                            }
                            (diffFromMax < 0.1) && switchState == SwitchState.OFF -> {
                                onSwitchStateChange(SwitchState.ON)
                            }
                            // TODO real WP don't care the how far middle it is, it only cares about the overall direction
                            (diffFromMax > offsetX) && switchState == SwitchState.ON -> {
                                onSwitchStateChange(SwitchState.OFF)
                            }
                            else -> {
                                onSwitchStateChange(SwitchState.ON)
                            }
                        }
                    }
                )
                .background(Color.White)
                // TODO real WP switch don't work like this. The x-axis white never covers up the LHS black border
                .padding(horizontal = 4.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 32.dp)
                    .background(Color.Black),
            )
        }

        val colorWidth = with(LocalDensity.current) { offsetX.toDp() } - 8.dp
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(colorWidth, 20.dp)
                .background(LocalAccentColor.current),
        )
    }
}