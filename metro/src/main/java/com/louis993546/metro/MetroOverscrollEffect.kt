package com.louis993546.metro

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Velocity

/**
 * TODO
 *   https://developer.android.com/reference/kotlin/androidx/compose/foundation/OverscrollEffect
 */
@ExperimentalFoundationApi
class MetroOverscrollEffect(
    context: Context,
) : OverscrollEffect {
    override fun consumePreScroll(scrollDelta: Offset, source: NestedScrollSource): Offset {
        TODO("Not yet implemented")
    }

    override fun consumePostScroll(initialDragDelta: Offset, overscrollDelta: Offset, source: NestedScrollSource) {
        TODO("Not yet implemented")
    }

    override suspend fun consumePreFling(velocity: Velocity): Velocity {
        TODO("Not yet implemented")
    }

    override suspend fun consumePostFling(velocity: Velocity) {
        TODO("Not yet implemented")
    }

    override var isEnabled: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}

    override val isInProgress: Boolean
        get() = TODO("Not yet implemented")

    override val effectModifier: Modifier
        get() = TODO("Not yet implemented")
}

@ExperimentalFoundationApi
@Composable
internal fun rememberMetroOverscrollEffect(): OverscrollEffect {
    val context = LocalContext.current
    return remember(context) { MetroOverscrollEffect(context) }
}
