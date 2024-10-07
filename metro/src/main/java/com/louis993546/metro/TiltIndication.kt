package com.louis993546.metro

// All commented because Compose API change. See TileIndication2 for WIP
// https://developer.android.com/develop/ui/compose/touch-input/user-interactions/migrate-indication-ripple
//
///**
// * Creates and [remember]s a [TiltIndication].
// *
// * [TiltIndication] is a Metro implementation of [Indication] that
// * expresses [Interaction]s by tilting the component based on the
// * pointer position.
// *
// * @param activeScale Target scaling when activated.
// * @param inactiveScale Scaling when inactive.
// * @param intensity Degree of intensity for the tilt effect.
// *
// * @author Filiph Siitam Sandström <filiph.sandstrom@filfatstudios.com>
// */
//@Deprecated("See TileIndicaion 2")
//@Composable
//fun rememberTiltIndication(
//    activeScale: Float = 0.98f,
//    inactiveScale: Float = 1f,
//    intensity: Float = 7.5f
//): Indication {
//    return remember(activeScale, inactiveScale, intensity) {
//        TiltIndication(
//            scaleMin = activeScale,
//            scaleMax = inactiveScale,
//            intensity = intensity
//        )
//    }
//}
//
//
//internal class TiltIndication(
//    private val scaleMin: Float = 0.98f,
//    private val scaleMax: Float = 1f,
//    private val intensity: Float = 7.5f
//) : Indication {
//    @Composable
//    override fun rememberUpdatedInstance(
//        interactionSource: InteractionSource
//    ): IndicationInstance {
//        val active = remember { mutableStateOf(false) }
//        val animationState = animateFloatAsState(
//            targetValue = if (active.value) 1f else 0f,
//            animationSpec = tween(
//                durationMillis = 125
//            ),
//            label = "TiltAnimation"
//        )
//
//        val instance = remember {
//            TiltIndicationInstance(
//                scaleMin = scaleMin,
//                scaleMax = scaleMax,
//                intensity = intensity,
//                animationState = animationState
//            )
//        }
//
//        LaunchedEffect(interactionSource) {
//            interactionSource.interactions.collect { interaction ->
//                when (interaction) {
//                    is PressInteraction.Press -> {
//                        active.value = true
//                        instance.start(
//                            scope = this,
//                            position = interaction.pressPosition
//                        )
//                    }
//                    is PressInteraction.Release,
//                    is PressInteraction.Cancel -> {
//                        active.value = false
//                        instance.stop()
//                    }
//                }
//            }
//        }
//
//        return instance
//    }
//}
//
//private class TiltIndicationInstance(
//    private val scaleMin: Float,
//    private val scaleMax: Float,
//    private val intensity: Float,
//    private val animationState: State<Float>
//) : IndicationInstance {
//    private var componentSize = Size.Zero
//    private var rotation = Offset.Zero
//    private var finishEvent = Channel<Unit>(Channel.CONFLATED)
//
//    fun start(scope: CoroutineScope, position: Offset) {
//        scope.launch {
//            var x = 0f
//            var y = 0f
//
//            if (position.x < componentSize.width / 3) {
//                x += -intensity
//            } else if (position.x > (componentSize.width / 3) * 2) {
//                x += intensity
//            }
//
//            if (position.y < componentSize.height / 3) {
//                y += intensity
//            } else if (position.y > (componentSize.height / 3) * 2) {
//                y += -intensity
//            }
//
//            rotation = Offset(x, y)
//        }
//        scope.launch {
//            finishEvent.receive()
//        }
//    }
//
//
//    private val matrix = Matrix()
//    private val camera = Camera()
//
//    /**
//     * We would need access to [Modifier] to properly animate,
//     * as far as I can tell there is no way to do this as
//     * of now.
//     *
//     * An alternative would be if [DrawScope] supported
//     * proper rotation.
//     *
//     * Instead we now have to use the built-in method to create
//     * a virtual [Camera] and do a bunch of [Matrix] calculations.
//     * Luckily this is actually quite performant.
//     *
//     * TODO Maybe file an issue with jetpack compose upstream?
//     */
//    override fun ContentDrawScope.drawIndication() {
//        if (rotation.y == 0f && rotation.x == 0f) {
//            // Convert 0.0-1.0 to scaleMin->scaleMax
//            val scale =
//                (((1f - animationState.value) - 0f) / (1f - 0f)) * (scaleMax - scaleMin) + scaleMin
//
//            drawContext.transform.apply {
//                scale(scale, center)
//            }
//        }
//
//        componentSize = size
//        this.drawIntoCanvas { canvas ->
//            camera.save()
//            camera.rotateX(rotation.y * animationState.value)
//            camera.rotateY(rotation.x * animationState.value)
//            camera.rotateZ(0f)
//            camera.getMatrix(matrix)
//
//            val x = center.x
//            val y = center.y
//            matrix.preTranslate(-x, -y)
//            matrix.postTranslate(x, y)
//
//            canvas.nativeCanvas.concat(matrix)
//            camera.restore()
//        }
//
//        drawContent()
//    }
//}
