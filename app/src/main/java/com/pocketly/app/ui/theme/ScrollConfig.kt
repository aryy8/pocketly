package com.pocketly.app.ui.theme

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlin.math.abs

/**
 * Provides smooth, silky trackpad and touch scrolling across macOS trackpads and touchscreens.
 * Removes harsh overscroll stretch and enhances glide momentum.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SmoothScrollContainer(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
        content = content
    )
}

/**
 * Natural fling behavior with balanced momentum so trackpad flicking glides effortlessly.
 */
@Composable
fun rememberFluidFlingBehavior(): FlingBehavior {
    val decay = remember { exponentialDecay<Float>(frictionMultiplier = 0.85f) }
    return remember(decay) {
        object : FlingBehavior {
            override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                if (abs(initialVelocity) < 1f) return 0f
                var velocityLeft = initialVelocity
                var lastValue = 0f
                val animatable = androidx.compose.animation.core.Animatable(0f)
                kotlinx.coroutines.coroutineScope {
                    try {
                        animatable.animateDecay(
                            initialVelocity = initialVelocity,
                            animationSpec = decay
                        ) {
                            val delta = value - lastValue
                            val consumed = scrollBy(delta)
                            lastValue = value
                            velocityLeft = velocity
                            if (abs(delta - consumed) > 0.5f) {
                                this@coroutineScope.cancel()
                            }
                        }
                    } catch (_: kotlinx.coroutines.CancellationException) {
                        // Flung to boundary
                    }
                }
                return velocityLeft
            }
        }
    }
}

/**
 * Intercepts continuous two-finger scroll events from MacBook trackpads in the Android emulator
 * and applies responsive, proportional glide deltas to eliminate jerky stepping.
 */
fun Modifier.trackpadSmoothScroll(
    state: LazyListState,
    stepDp: Float = 48f
): Modifier = this.pointerInput(state) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            if (event.type == PointerEventType.Scroll) {
                val deltaY = event.changes.firstOrNull()?.scrollDelta?.y ?: 0f
                if (deltaY != 0f) {
                    // Smoothly dispatch proportional delta to LazyListState
                    state.dispatchRawDelta(-deltaY * stepDp * density)
                    event.changes.forEach { it.consume() }
                }
            }
        }
    }
}
