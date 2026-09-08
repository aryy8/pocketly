package com.pocketly.app.ui.components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pocketly.app.ui.theme.GlassSpecularGleam
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily
import com.pocketly.app.ui.theme.ReceivedGreen
import com.pocketly.app.ui.theme.TextPrimary
import com.pocketly.app.ui.theme.TextSecondary
import java.util.Locale
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * Apple Camera Zoom Slider Dial to set monthly budget in ₹500 increments.
 * Styled in luxury Liquid White Glass aesthetic with curved arc ticks.
 */
@Composable
fun AppleCameraBudgetDialDialog(
    initialBudget: Float,
    onDismiss: () -> Unit,
    onConfirm: (Float) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            AppleCameraBudgetDialCard(
                initialBudget = initialBudget,
                onDismiss = onDismiss,
                onConfirm = onConfirm
            )
        }
    }
}

@Composable
fun AppleCameraBudgetDialCard(
    initialBudget: Float,
    onDismiss: () -> Unit,
    onConfirm: (Float) -> Unit
) {
    val minBudget = 2000f
    val maxBudget = 100000f
    val stepAmount = 500f

    var rawBudgetValue by remember { mutableFloatStateOf(initialBudget.coerceIn(minBudget, maxBudget)) }

    // Stepped budget snapped to nearest 500
    val snappedBudget = ((rawBudgetValue / stepAmount).roundToInt() * stepAmount).coerceIn(minBudget, maxBudget)

    // Animated budget for ultra-smooth live readout
    val animatedBudget by animateFloatAsState(
        targetValue = snappedBudget,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow),
        label = "budget_readout"
    )

    // Presets matching Apple Camera zoom focal lengths (0.5x, 1x, 2x, 3x, 5x)
    val presets = listOf(5000f, 10000f, 15000f, 20000f, 30000f, 50000f)

    // Liquid White frosted container
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 28.dp,
                shape = RoundedCornerShape(36.dp),
                spotColor = Color(0x33000000),
                ambientColor = Color(0x1A000000)
            )
            .clip(RoundedCornerShape(36.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFAFFFFFF),
                        Color(0xF0F8FAFC)
                    )
                )
            )
            .border(
                width = 1.5.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GlassSpecularGleam,
                        Color(0x80FFFFFF),
                        Color(0x1F000000)
                    )
                ),
                shape = RoundedCornerShape(36.dp)
            )
            .padding(top = 22.dp, bottom = 26.dp, start = 20.dp, end = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header Bar: Title, Step badge, Close
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF1F5F9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Tune,
                            contentDescription = "Dial",
                            tint = Color(0xFF0F172A),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Precision Budget Dial",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Swipe wheel to set September limit",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF1F5F9))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color(0xFF475569),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Live Bold Numeric Display
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "₹" + String.format(Locale.US, "%,.0f", animatedBudget),
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0F172A),
                    letterSpacing = (-1).sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Context indicator pill (Safe Daily Spend)
                val safeDaily = (snappedBudget / 30f).roundToInt()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF1F5F9))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(ReceivedGreen)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Safe spend: ₹$safeDaily / day",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF334155)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Apple Camera Focal Presets Row (e.g. 5k, 10k, 15k, 20k, 30k, 50k)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                presets.forEach { preset ->
                    val isSelected = (snappedBudget == preset)
                    val label = when {
                        preset >= 1000f -> "${(preset / 1000).toInt()}k"
                        else -> preset.toInt().toString()
                    }

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                if (isSelected) Color(0xFF0F172A) else Color(0xFFF1F5F9)
                            )
                            .clickable {
                                rawBudgetValue = preset
                            }
                            .padding(horizontal = 12.dp, vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 12.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else Color(0xFF475569)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // THE APPLE CAMERA CURVED ZOOM WHEEL
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFF8FAFC),
                                Color(0xFFEDF2F7),
                                Color(0xFFE2E8F0)
                            )
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0x33CBD5E1),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            change.consume()
                            // Drag sensitivity: ~22px per 500 Rs step
                            val sensitivity = 22f
                            val deltaBudget = -(dragAmount / sensitivity) * stepAmount
                            val target = (rawBudgetValue + deltaBudget).coerceIn(minBudget, maxBudget)
                            rawBudgetValue = target
                        }
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                // Curved Tick Marks Canvas
                AppleCameraCurvedDialCanvas(
                    currentBudget = snappedBudget,
                    minBudget = minBudget,
                    maxBudget = maxBudget,
                    stepAmount = stepAmount
                )

                // Apex Indicator (Apple Camera Yellow / Gold Marker)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Canvas(modifier = Modifier.size(16.dp, 12.dp)) {
                        val path = Path().apply {
                            moveTo(0f, 0f)
                            lineTo(size.width, 0f)
                            lineTo(size.width / 2f, size.height)
                            close()
                        }
                        // Draw amber/yellow triangle pointer
                        drawPath(
                            path = path,
                            color = Color(0xFFF59E0B) // Bright Apple Camera Amber
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(2.5.dp)
                            .height(16.dp)
                            .clip(RoundedCornerShape(1.dp))
                            .background(Color(0xFFF59E0B))
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons (Confirm and Cancel)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF1F5F9))
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF475569)
                    )
                }

                Button(
                    onClick = { onConfirm(snappedBudget) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1.5f)
                        .height(48.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Confirm",
                            tint = Color.White,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Set ₹" + String.format(Locale.US, "%,.0f", snappedBudget),
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

/**
 * Canvas drawing the authentic curved Apple camera tick marks track.
 * Uses polar coordinate trigonometry to curve ticks smoothly along an arc.
 */
@Composable
private fun AppleCameraCurvedDialCanvas(
    currentBudget: Float,
    minBudget: Float,
    maxBudget: Float,
    stepAmount: Float
) {
    Canvas(modifier = Modifier.fillMaxWidth().height(130.dp)) {
        val w = size.width
        val h = size.height

        val centerX = w / 2f
        // Arc radius and circle center below canvas for convex upward curve
        val arcRadius = w * 1.05f
        val centerY = arcRadius + 22.dp.toPx()

        // Angular spread per ₹500 step
        val anglePerStepRad = 0.042f // ~2.4 degrees per step

        val currentStepIndex = (currentBudget / stepAmount).roundToInt()
        val totalStepsVisible = 28 // 14 steps left, 14 steps right

        val minStep = (minBudget / stepAmount).roundToInt()
        val maxStep = (maxBudget / stepAmount).roundToInt()

        val textPaint = Paint().apply {
            color = Color(0xFF475569).toArgb()
            textSize = 10.dp.toPx()
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT_BOLD
            isAntiAlias = true
        }

        for (offset in -totalStepsVisible..totalStepsVisible) {
            val stepIndex = currentStepIndex + offset
            if (stepIndex < minStep || stepIndex > maxStep) continue

            val stepBudget = stepIndex * stepAmount
            val angle = offset * anglePerStepRad

            // Angle from 12 o'clock (apex = 0 rad offset from -PI/2)
            val rad = -Math.PI / 2.0 + angle

            // Determine if major (every 5000 = 10 steps), medium (every 2500 = 5 steps), or minor
            val isMajor = (stepBudget.toInt() % 5000 == 0)
            val isMedium = (stepBudget.toInt() % 2500 == 0 && !isMajor)

            val tickLength = when {
                isMajor -> 26.dp.toPx()
                isMedium -> 18.dp.toPx()
                else -> 11.dp.toPx()
            }

            val tickWidth = when {
                isMajor -> 2.2.dp.toPx()
                isMedium -> 1.6.dp.toPx()
                else -> 1.1.dp.toPx()
            }

            val tickAlpha = (1f - (Math.abs(offset) / (totalStepsVisible.toFloat() + 1f))).coerceIn(0.15f, 1f)

            val tickColor = when {
                isMajor -> Color(0xFF0F172A).copy(alpha = tickAlpha)
                isMedium -> Color(0xFF334155).copy(alpha = tickAlpha * 0.85f)
                else -> Color(0xFF94A3B8).copy(alpha = tickAlpha * 0.65f)
            }

            // Outer point of tick (nearest arc top)
            val outerR = arcRadius
            val innerR = arcRadius - tickLength

            val startX = (centerX + outerR * cos(rad)).toFloat()
            val startY = (centerY + outerR * sin(rad)).toFloat()

            val endX = (centerX + innerR * cos(rad)).toFloat()
            val endY = (centerY + innerR * sin(rad)).toFloat()

            drawLine(
                color = tickColor,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = tickWidth
            )

            // Draw major tick text labels (e.g. 10k, 15k, 20k, 25k)
            if (isMajor && Math.abs(offset) < totalStepsVisible - 2) {
                val labelR = innerR - 12.dp.toPx()
                val labelX = (centerX + labelR * cos(rad)).toFloat()
                val labelY = (centerY + labelR * sin(rad)).toFloat()

                val labelText = when {
                    stepBudget >= 1000f -> "${(stepBudget / 1000).toInt()}k"
                    else -> stepBudget.toInt().toString()
                }

                drawIntoCanvas { canvas ->
                    textPaint.alpha = (tickAlpha * 255).toInt()
                    canvas.nativeCanvas.drawText(labelText, labelX, labelY + 4.dp.toPx(), textPaint)
                }
            }
        }
    }
}
