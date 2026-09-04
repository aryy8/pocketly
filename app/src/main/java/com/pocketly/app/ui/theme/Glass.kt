package com.pocketly.app.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Authentic Liquid Glass card surface matching the deep shadow, specular highlights,
 * and translucent depth from the Figma design.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(32.dp),
    elevation: Dp = 14.dp,
    backgroundBrush: Brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xF8FFFFFF),
            Color(0xEEFFFFFF),
            Color(0xE0F8FAFC)
        )
    ),
    borderStroke: BorderStroke = BorderStroke(
        width = 1.2.dp,
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White,
                Color.White.copy(alpha = 0.55f),
                Color(0x1A000000)
            )
        )
    ),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape,
                spotColor = Color(0x38000000),     // Rich spot shadow creating realistic 3D depth
                ambientColor = Color(0x18000000)   // Soft diffuse ambient shadow
            )
            .clip(shape)
            .background(brush = backgroundBrush)
            .border(
                border = borderStroke,
                shape = shape
            )
    ) {
        // Specular highlight gleam line along the top curve
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .align(Alignment.TopCenter)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.9f),
                            Color.Transparent
                        )
                    )
                )
        )
        content()
    }
}

/**
 * Frosted Liquid Glass pill surface for interactive selectors, chips, and dropdowns.
 */
@Composable
fun GlassPill(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xF2FFFFFF),
                        Color(0xC8F1F5F9)
                    )
                )
            )
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color(0x24000000)
                        )
                    )
                ),
                shape = shape
            ),
        content = content
    )
}
