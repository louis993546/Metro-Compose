package com.louis993546.metro

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.channels.Channel

/**
 * Just a temporary file cause the original Indication API has been deprecated. Properly migrate and
 * put every in v1 to preserve the history
 */
private class TiltIndicationNode(
    private val scaleMin: Float,
    private val scaleMax: Float,
    private val intensity: Float,
) : Modifier.Node(), DrawModifierNode {
    private var componentSize = Size.Zero
    private var rotation = Offset.Zero
    private var finishEvent = Channel<Unit>(Channel.CONFLATED)

    override fun onAttach() {
        super.onAttach()
    }

    override fun onDetach() {
        finishEvent.trySend(Unit)
    }

    override fun ContentDrawScope.draw() {
        TODO("Not yet implemented")
    }
}
