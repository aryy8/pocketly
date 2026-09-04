package com.pocketly.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PocketlyColorScheme = lightColorScheme(
    primary = BrandDark,
    onPrimary = Color.White,
    secondary = BrandSubhead,
    onSecondary = Color.White,
    background = ScreenBackground,
    onBackground = TextPrimary,
    surface = GlassWhiteHigh,
    onSurface = TextPrimary,
    surfaceVariant = ScreenBackgroundSecondary,
    onSurfaceVariant = TextSecondary,
    outline = GlassBorderTop
)

@Composable
fun PocketlyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PocketlyColorScheme,
        typography = PocketlyTypography,
        content = content
    )
}
