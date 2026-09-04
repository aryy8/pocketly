package com.pocketly.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketly.app.ui.theme.GlassBorderBottom
import com.pocketly.app.ui.theme.GlassBorderTop
import com.pocketly.app.ui.theme.NavActive
import com.pocketly.app.ui.theme.NavInactive
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily

@Composable
fun BottomNavBar(
    selectedTab: Int = 0,
    onTabSelected: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val barShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = barShape,
                spotColor = Color(0x2E000000),
                ambientColor = Color(0x12000000)
            )
            .clip(barShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xF6FFFFFF),
                        Color(0xEEFFFFFF),
                        Color(0xE4F8FAFC)
                    )
                )
            )
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color(0x28000000)
                        )
                    )
                ),
                shape = barShape
            )
            .navigationBarsPadding()
            .padding(horizontal = 6.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tab 0: Home (Custom outlined house with door arch)
            HomeNavItem(
                isSelected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                modifier = Modifier.weight(1f)
            )

            // Tab 1: Transactions (Custom rounded credit card with top stripe)
            TransactionsNavItem(
                isSelected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                modifier = Modifier.weight(1f)
            )

            // Tab 2: Central Search Button (Light soft grey circle + bold black magnifying glass)
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEFF1F4))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onTabSelected(2) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    SearchIcon(sizeDp = 22)
                }
            }

            // Tab 3: Analytics (Custom pie chart with separate wedge)
            AnalyticsNavItem(
                isSelected = selectedTab == 3,
                onClick = { onTabSelected(3) },
                modifier = Modifier.weight(1f)
            )

            // Tab 4: Profile (Custom user outline)
            ProfileNavItem(
                isSelected = selectedTab == 4,
                onClick = { onTabSelected(4) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Home Nav Item with exact house outline + inner doorway arch
 */
@Composable
private fun HomeNavItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tint = if (isSelected) NavActive else NavInactive

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(modifier = Modifier.size(23.dp)) {
            val stroke = 2.dp.toPx()
            val w = size.width
            val h = size.height

            val path = Path().apply {
                // Roof tip
                moveTo(w * 0.5f, h * 0.12f)
                // Right roof slope
                lineTo(w * 0.88f, h * 0.44f)
                // Right wall
                lineTo(w * 0.88f, h * 0.88f)
                // Bottom-right to door
                lineTo(w * 0.62f, h * 0.88f)
                // Door right
                lineTo(w * 0.62f, h * 0.56f)
                // Door top rounded arch
                quadraticBezierTo(w * 0.5f, h * 0.48f, w * 0.38f, h * 0.56f)
                // Door left
                lineTo(w * 0.38f, h * 0.88f)
                // Bottom-left
                lineTo(w * 0.12f, h * 0.88f)
                // Left wall
                lineTo(w * 0.12f, h * 0.44f)
                close()
            }

            drawPath(
                path = path,
                color = tint,
                style = Stroke(
                    width = stroke,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Home",
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = tint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Transactions Nav Item with rounded credit card outline and magnetic stripe
 */
@Composable
private fun TransactionsNavItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tint = if (isSelected) NavActive else NavInactive

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(modifier = Modifier.size(23.dp)) {
            val stroke = 1.9.dp.toPx()
            val w = size.width
            val h = size.height

            val cardTop = h * 0.22f
            val cardHeight = h * 0.56f
            val cardLeft = w * 0.08f
            val cardWidth = w * 0.84f

            // Rounded rectangle card outline
            drawRoundRect(
                color = tint,
                topLeft = Offset(cardLeft, cardTop),
                size = Size(cardWidth, cardHeight),
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
                style = Stroke(width = stroke)
            )

            // Horizontal magnetic stripe line
            val stripeY = cardTop + cardHeight * 0.34f
            drawLine(
                color = tint,
                start = Offset(cardLeft, stripeY),
                end = Offset(cardLeft + cardWidth, stripeY),
                strokeWidth = stroke
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Transactions",
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = tint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Bold magnifying glass icon matching reference image
 */
@Composable
private fun SearchIcon(sizeDp: Int = 22) {
    Canvas(modifier = Modifier.size(sizeDp.dp)) {
        val stroke = 2.8.dp.toPx()
        val radius = 5.8.dp.toPx()
        val circleCenter = Offset(9.2.dp.toPx(), 9.2.dp.toPx())

        // Magnifying glass lens
        drawCircle(
            color = Color.Black,
            radius = radius,
            center = circleCenter,
            style = Stroke(width = stroke)
        )

        // Handle at 45-degree angle
        val handleStart = Offset(13.4.dp.toPx(), 13.4.dp.toPx())
        val handleEnd = Offset(18.5.dp.toPx(), 18.5.dp.toPx())
        drawLine(
            color = Color.Black,
            start = handleStart,
            end = handleEnd,
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}

/**
 * Analytics Nav Item rendering circular pie-chart gauge icon with separate wedge
 */
@Composable
private fun AnalyticsNavItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tint = if (isSelected) NavActive else NavInactive

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(modifier = Modifier.size(23.dp)) {
            val stroke = 1.9.dp.toPx()
            val w = size.width
            val h = size.height

            // 3/4 Main arc from 0 deg (3 o'clock) clockwise to 270 deg (12 o'clock)
            val padding = 2.dp.toPx()
            val arcSize = Size(w - 2 * padding, h - 2 * padding)
            val arcOffset = Offset(padding, padding)

            drawArc(
                color = tint,
                startAngle = 5f,
                sweepAngle = 260f,
                useCenter = false,
                topLeft = arcOffset,
                size = arcSize,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )

            // Center point
            val center = Offset(w / 2f, h / 2f)

            // Top-Right Wedge
            val wedgePath = Path().apply {
                val gap = 1.5.dp.toPx()
                moveTo(center.x + gap, center.y - gap)
                lineTo(center.x + gap, padding)
                arcTo(
                    rect = Rect(arcOffset, arcSize),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 85f,
                    forceMoveTo = false
                )
                close()
            }
            drawPath(
                path = wedgePath,
                color = tint,
                style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Analytics",
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = tint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Profile Nav Item rendering user outline (circle head + curved shoulder arch)
 */
@Composable
private fun ProfileNavItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tint = if (isSelected) NavActive else NavInactive

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(modifier = Modifier.size(23.dp)) {
            val stroke = 1.9.dp.toPx()
            val w = size.width
            val h = size.height

            // Head circle
            drawCircle(
                color = tint,
                radius = 4.5.dp.toPx(),
                center = Offset(w / 2f, h * 0.32f),
                style = Stroke(width = stroke)
            )

            // Shoulder curved arch
            val shoulderWidth = w * 0.72f
            val shoulderHeight = h * 0.36f
            val shoulderLeft = (w - shoulderWidth) / 2f
            val shoulderTop = h * 0.58f

            drawArc(
                color = tint,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(shoulderLeft, shoulderTop),
                size = Size(shoulderWidth, shoulderHeight * 2),
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Profile",
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = tint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
