package com.louis993546.metro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

val LocalAccentColor = compositionLocalOf<Color> { error("No accent color found!") }
val LocalBackgroundColor = compositionLocalOf<Color> { error("No background color found") }
val LocalTextOnAccentColor = compositionLocalOf<Color> { error("No text on accent color found") }
val LocalTextOnBackgroundColor =
    compositionLocalOf<Color> { error("No text on background color found") }
val LocalButtonColor = compositionLocalOf<Color> { error("No button color found") }
val LocalTextOnButtonColor = compositionLocalOf<Color> { error("No text on button color found") }

@ExperimentalFoundationApi
@ExperimentalOverscrollType
@Composable
fun MetroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentColor: Color,
    overscrollType: OverscrollType,
    content: @Composable () -> Unit
) {
    val backgroundColor = if (darkTheme) Color.Black else Color.White
    val textOnBackgroundColor = if (darkTheme) Color.White else Color.Black
    val textOnAccentColor = accentColor.textColor()
    val buttonColor = if (darkTheme) Color.DarkGray else Color.LightGray

    val overscrollEffect = when (overscrollType) {
        OverscrollType.None-> noOpOverscrollEffect
        OverscrollType.Android -> ScrollableDefaults.overscrollEffect()
        OverscrollType.Metro -> rememberMetroOverscrollEffect()
    }


    CompositionLocalProvider(
        LocalAccentColor provides accentColor,
        LocalBackgroundColor provides backgroundColor,
        LocalTextOnAccentColor provides textOnAccentColor,
        LocalTextOnBackgroundColor provides textOnBackgroundColor,
        LocalButtonColor provides buttonColor,
        LocalTextOnButtonColor provides textOnBackgroundColor, // this is close enough for now
        LocalOverscrollEffect provides overscrollEffect,
    ) {
        content()
    }
}

private fun Color.textColor(): Color {
    val luminance = this.luminance()
    return if (luminance >= 0.5) Color.Black else Color.White
}

@RequiresOptIn(
    """
        The Metro overscan effect is still WIP, use Android/None if you want something stable.
    """
)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ExperimentalOverscrollType

@ExperimentalOverscrollType
enum class OverscrollType {
    Android,
    None,
    Metro,
}

@ExperimentalFoundationApi
val LocalOverscrollEffect = compositionLocalOf<OverscrollEffect> {
    noOpOverscrollEffect
}

// TODO mark where do i copy this from
@OptIn(ExperimentalFoundationApi::class)
private val noOpOverscrollEffect = object : OverscrollEffect {

    override fun consumePreScroll(
        scrollDelta: Offset,
        source: NestedScrollSource
    ): Offset = Offset.Zero

    override fun consumePostScroll(
        initialDragDelta: Offset,
        overscrollDelta: Offset,
        source: NestedScrollSource
    ) {
    }

    override suspend fun consumePreFling(velocity: Velocity): Velocity = Velocity.Zero

    override suspend fun consumePostFling(velocity: Velocity) {}

    override var isEnabled: Boolean = false

    override val isInProgress: Boolean
        get() = false

    override val effectModifier: Modifier
        get() = Modifier
}
