package com.pocketly.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketly.app.ui.theme.BrandDark
import com.pocketly.app.ui.theme.InriaSerifFontFamily

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(bottomStart = 34.dp, bottomEnd = 34.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = shape,
                spotColor = Color(0x3E000000),      // Deep realistic drop shadow matching Figma
                ambientColor = Color(0x1C000000)
            )
            .clip(shape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xF9FFFFFF),
                        Color(0xF0FFFFFF),
                        Color(0xE4F8FAFC)
                    )
                )
            )
            .border(
                border = BorderStroke(
                    width = 1.2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color.White.copy(alpha = 0.6f),
                            Color(0x18000000)
                        )
                    )
                ),
                shape = shape
            )
            .statusBarsPadding()
            .padding(start = 24.dp, end = 24.dp, top = 4.dp, bottom = 18.dp)
    ) {
        Text(
            text = "Pocketly",
            fontFamily = InriaSerifFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp,
            letterSpacing = (-0.3).sp,
            color = BrandDark
        )
    }
}
