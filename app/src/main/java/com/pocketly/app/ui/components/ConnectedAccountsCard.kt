package com.pocketly.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketly.app.ui.theme.GlassCard
import com.pocketly.app.ui.theme.GlassPill
import com.pocketly.app.ui.theme.HdfcBlue
import com.pocketly.app.ui.theme.HdfcRed
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily
import com.pocketly.app.ui.theme.SbiBlue
import com.pocketly.app.ui.theme.TextPrimary
import com.pocketly.app.ui.theme.TextSecondary

@Composable
fun ConnectedAccountsCard(modifier: Modifier = Modifier) {
    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 15.dp)
        ) {
            // Header row with + circular button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Connected Accounts",
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 16.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                // Minimalist circular plus button matching Figma reference
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(20.dp)) {
                        drawCircle(
                            color = Color(0xFF334155),
                            style = Stroke(width = 1.6.dp.toPx())
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add account",
                        tint = Color(0xFF334155),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            // Bank pills row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // HDFC Bank Chip
                GlassPill(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HdfcLogo(modifier = Modifier.size(17.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "HDFC BANK- 2673",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            maxLines = 1,
                            softWrap = false,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = TextSecondary,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }

                // SBI Bank Chip (Matching Figma reference: blue circle logo + blue SBI + dark SBI - 8958)
                GlassPill(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SbiLogo(modifier = Modifier.size(17.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "SBI",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = SbiBlue
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "SBI - 8958",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            maxLines = 1,
                            softWrap = false,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = TextSecondary,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Authentic HDFC Bank geometric logo
 */
@Composable
fun HdfcLogo(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        // Outer red border
        drawRect(
            color = HdfcRed,
            topLeft = Offset.Zero,
            size = Size(w, h),
            style = Stroke(width = w * 0.18f)
        )
        // Central blue square
        val centerSize = w * 0.44f
        val centerOffset = (w - centerSize) / 2f
        drawRect(
            color = HdfcBlue,
            topLeft = Offset(centerOffset, centerOffset),
            size = Size(centerSize, centerSize)
        )
        // Red arms extending outward
        val armThickness = w * 0.18f
        drawRect(
            color = HdfcRed,
            topLeft = Offset((w - armThickness) / 2f, 0f),
            size = Size(armThickness, h)
        )
        drawRect(
            color = HdfcRed,
            topLeft = Offset(0f, (h - armThickness) / 2f),
            size = Size(w, armThickness)
        )
        // Re-draw central blue square
        drawRect(
            color = HdfcBlue,
            topLeft = Offset(centerOffset, centerOffset),
            size = Size(centerSize, centerSize)
        )
    }
}

/**
 * Authentic SBI Logo: Blue circle with white keyhole slot extending down
 */
@Composable
fun SbiLogo(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2f
        val center = Offset(size.width / 2f, size.height / 2f)

        // Blue circle background
        drawCircle(
            color = SbiBlue,
            radius = radius,
            center = center
        )

        // White inner circle
        val innerRadius = radius * 0.32f
        drawCircle(
            color = Color.White,
            radius = innerRadius,
            center = center
        )

        // White vertical keyhole slit extending down from center circle
        val slitWidth = innerRadius * 0.55f
        drawRect(
            color = Color.White,
            topLeft = Offset(center.x - slitWidth / 2f, center.y),
            size = Size(slitWidth, radius)
        )
    }
}
