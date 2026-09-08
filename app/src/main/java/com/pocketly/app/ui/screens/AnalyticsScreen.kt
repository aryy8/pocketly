package com.pocketly.app.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pocketly.app.ui.components.AppleCameraBudgetDialDialog
import com.pocketly.app.ui.components.DonutChart
import com.pocketly.app.ui.components.DonutSegment
import com.pocketly.app.ui.components.TopBar
import com.pocketly.app.ui.theme.ChartFood
import com.pocketly.app.ui.theme.ChartMobile
import com.pocketly.app.ui.theme.ChartShopping
import com.pocketly.app.ui.theme.ChartTransport
import com.pocketly.app.ui.theme.FoodAccent
import com.pocketly.app.ui.theme.FoodBg
import com.pocketly.app.ui.theme.GlassCard
import com.pocketly.app.ui.theme.MobileAccent
import com.pocketly.app.ui.theme.MobileBg
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily
import com.pocketly.app.ui.theme.ReceivedGreen
import com.pocketly.app.ui.theme.ShoppingAccent
import com.pocketly.app.ui.theme.ShoppingBg
import com.pocketly.app.ui.theme.SmoothScrollContainer
import com.pocketly.app.ui.theme.SpentRed
import com.pocketly.app.ui.theme.TextPrimary
import com.pocketly.app.ui.theme.TextSecondary
import com.pocketly.app.ui.theme.TransportAccent
import com.pocketly.app.ui.theme.TransportBg
import com.pocketly.app.ui.theme.rememberFluidFlingBehavior
import com.pocketly.app.ui.theme.trackpadSmoothScroll

data class MonthlyBar(
    val month: String,
    val amount: Float,
    val displayAmount: String
)

@Composable
fun AnalyticsScreen(
    bottomPadding: Dp = 0.dp,
    reloadTrigger: Int = 0
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val repository = remember { com.pocketly.app.data.TransactionRepository(context) }
    val userPreferences = remember { com.pocketly.app.data.UserPreferences(context) }

    var monthlyBudget by remember(reloadTrigger) { mutableStateOf(userPreferences.monthlyBudget) }
    var showAppleDial by remember { mutableStateOf(false) }

    val rawBars = remember(reloadTrigger) { repository.getMonthlyBarData() }
    val barData = remember(rawBars) {
        rawBars.map { MonthlyBar(it.month, it.amount, it.displayAmount) }
    }
    var selectedBarIndex by remember { mutableIntStateOf(4) }
    var showReportDialog by remember { mutableStateOf(false) }

    if (showAppleDial) {
        AppleCameraBudgetDialDialog(
            initialBudget = monthlyBudget,
            onDismiss = { showAppleDial = false },
            onConfirm = { newBudget ->
                userPreferences.monthlyBudget = newBudget
                monthlyBudget = newBudget
                showAppleDial = false
            }
        )
    }

    if (showReportDialog) {
        Dialog(onDismissRequest = { showReportDialog = false }) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(30.dp),
                elevation = 16.dp,
                backgroundBrush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFF8FAFC))
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(ReceivedGreen.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Success",
                            tint = ReceivedGreen,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Report Generated",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Your Q3 2026 Spending Summary statement has been compiled and saved to your device Downloads.",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 13.5.sp,
                        color = TextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Button(
                        onClick = { showReportDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Done",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    val listState = rememberLazyListState()
    val flingBehavior = rememberFluidFlingBehavior()

    SmoothScrollContainer {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            modifier = Modifier
                .fillMaxSize()
                .trackpadSmoothScroll(listState)
                .padding(bottom = bottomPadding)
        ) {
            item {
                TopBar()
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
                // Bold Monthly Budget Display & Apple Camera Zoom Slider Launcher
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable { showAppleDial = true },
                    shape = RoundedCornerShape(30.dp),
                    elevation = 10.dp,
                    backgroundBrush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFFFF),
                            Color(0xFFF8FAFC)
                        )
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showAppleDial = true }
                            .padding(22.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(ReceivedGreen)
                                )
                                Spacer(modifier = Modifier.width(7.dp))
                                Text(
                                    text = "SEPTEMBER 2026 TARGET",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF475569),
                                    letterSpacing = 1.sp
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFF1F5F9))
                                    .clickable { showAppleDial = true }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Tune,
                                        contentDescription = "Zoom Slider",
                                        tint = Color(0xFF0F172A),
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Zoom Dial",
                                        fontFamily = PlusJakartaSansFontFamily,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "₹" + String.format(java.util.Locale.US, "%,.0f", monthlyBudget),
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F172A),
                            letterSpacing = (-1).sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        val safeDailySpend = (monthlyBudget / 30f).toInt()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Safe daily spend: ",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 13.sp,
                                    color = TextSecondary
                                )
                                Text(
                                    text = "₹$safeDailySpend / day",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ReceivedGreen
                                )
                            }

                            Text(
                                text = "Tap to set ▾",
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }
            }

            // Aligned Monthly Budget Overview Component Directly Below It
            item {
                Spacer(modifier = Modifier.height(14.dp))
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(30.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        val balanceSummary = remember(reloadTrigger) { repository.getBalanceSummary() }
                        val totalBudget = monthlyBudget.toDouble()
                        val budgetUsed = balanceSummary.totalSpent
                        val budgetRemaining = (totalBudget - budgetUsed).coerceAtLeast(0.0)
                        val usagePercent = if (totalBudget > 0) ((budgetUsed / totalBudget) * 100).coerceIn(0.0, 100.0) else 0.0
                        val usageFraction = if (totalBudget > 0) (budgetUsed / totalBudget).toFloat().coerceIn(0f, 1f) else 0f
                        val isOnTrack = usagePercent <= 85.0

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Monthly Budget Overview",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "September 2026 · Spending vs Target",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.5.sp,
                                    color = TextSecondary
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isOnTrack) ReceivedGreen.copy(alpha = 0.12f) else SpentRed.copy(alpha = 0.12f))
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = if (isOnTrack) "🟢 On Track" else "⚠️ High Spend",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isOnTrack) ReceivedGreen else SpentRed
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Symmetrical circular budget gauge
                            Box(
                                modifier = Modifier.size(106.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(
                                    modifier = Modifier.size(106.dp)
                                ) {
                                    val stroke = 12.dp.toPx()
                                    val diameter = size.minDimension - stroke
                                    val arcTopLeft = Offset(
                                        (size.width - diameter) / 2f,
                                        (size.height - diameter) / 2f
                                    )
                                    val arcSize = Size(diameter, diameter)

                                    // Track
                                    drawArc(
                                        color = Color(0xFFE2E8F0),
                                        startAngle = -90f,
                                        sweepAngle = 360f,
                                        useCenter = false,
                                        topLeft = arcTopLeft,
                                        size = arcSize,
                                        style = Stroke(width = stroke, cap = StrokeCap.Round)
                                    )
                                    // Dynamic Progress
                                    val progressColor = if (usageFraction > 0.85f) Color(0xFFEF4444) else if (usageFraction > 0.6f) Color(0xFFF59E0B) else Color(0xFF10B981)
                                    drawArc(
                                        color = progressColor,
                                        startAngle = -90f,
                                        sweepAngle = usageFraction * 360f,
                                        useCenter = false,
                                        topLeft = arcTopLeft,
                                        size = arcSize,
                                        style = Stroke(width = stroke, cap = StrokeCap.Round)
                                    )
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "${usagePercent.toInt()}%",
                                        fontFamily = PlusJakartaSansFontFamily,
                                        fontSize = 21.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "used",
                                        fontFamily = PlusJakartaSansFontFamily,
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            // 2x2 Structured Metric Grid
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    BudgetGridTile(
                                        label = "Total Budget",
                                        value = "₹" + String.format(java.util.Locale.US, "%,.0f", totalBudget),
                                        dotColor = Color(0xFF64748B),
                                        modifier = Modifier.weight(1f)
                                    )
                                    BudgetGridTile(
                                        label = "Total Spent",
                                        value = "₹" + String.format(java.util.Locale.US, "%,.0f", budgetUsed),
                                        dotColor = SpentRed,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    BudgetGridTile(
                                        label = "Remaining",
                                        value = "₹" + String.format(java.util.Locale.US, "%,.0f", budgetRemaining),
                                        dotColor = ReceivedGreen,
                                        modifier = Modifier.weight(1f)
                                    )
                                    BudgetGridTile(
                                        label = "Total Income",
                                        value = "₹" + String.format(java.util.Locale.US, "%,.0f", balanceSummary.totalReceived),
                                        dotColor = Color(0xFF6366F1),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Bottom Summary Status Pill
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFFF8FAFC))
                                .padding(horizontal = 14.dp, vertical = 9.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = if (budgetRemaining > 0) "Remaining for Sept: ₹" + String.format(java.util.Locale.US, "%,.0f", budgetRemaining) else "Budget limit reached",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (budgetRemaining > 0) TextPrimary else SpentRed
                                )
                                Text(
                                    text = "22 days left",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
                // Spending Trend Bar Chart Card
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(30.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Monthly Spending Trends",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Tap a bar to inspect spending",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.5.sp,
                                    color = TextSecondary
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFF1F5F9))
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = barData[selectedBarIndex].displayAmount,
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(26.dp))

                        // Interactive Bar Chart
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            barData.forEachIndexed { idx, bar ->
                                val isSelected = selectedBarIndex == idx
                                val barHeightFraction = (bar.amount / 10f).coerceIn(0.15f, 1f)

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedBarIndex = idx }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight(barHeightFraction)
                                            .width(28.dp)
                                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                            .background(
                                                if (isSelected) Color(0xFF1E293B) else Color(0xFFCBD5E1)
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = bar.month,
                                        fontFamily = PlusJakartaSansFontFamily,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) TextPrimary else TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick Stats Row
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GlassCard(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(22.dp),
                        elevation = 6.dp
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("Avg Daily Spend", fontFamily = PlusJakartaSansFontFamily, fontSize = 12.sp, color = TextSecondary)
                            Text("₹200", fontFamily = PlusJakartaSansFontFamily, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("30 Days Active", fontFamily = PlusJakartaSansFontFamily, fontSize = 11.sp, color = ReceivedGreen)
                        }
                    }
                    GlassCard(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(22.dp),
                        elevation = 6.dp
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text("Savings Rate", fontFamily = PlusJakartaSansFontFamily, fontSize = 12.sp, color = TextSecondary)
                            Text("28.4%", fontFamily = PlusJakartaSansFontFamily, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("+4% vs target", fontFamily = PlusJakartaSansFontFamily, fontSize = 11.sp, color = ReceivedGreen)
                        }
                    }
                }
            }

            // Spending Breakdown Donut Chart (same style as Home screen)
            item {
                Spacer(modifier = Modifier.height(14.dp))
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(30.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Spending Breakdown",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Where your money went this month",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 12.5.sp,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        val spendData = remember(reloadTrigger) {
                            repository.getMonthlySpendingData("Sept 2026")
                        }
                        val donutSegments = spendData.categories.map {
                            DonutSegment(
                                value = if (it.percentage > 0) it.percentage.toFloat() else 1f,
                                color = it.chartColor,
                                label = it.label
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DonutChart(
                                segments = donutSegments,
                                chartSize = 130.dp,
                                strokeWidth = 24.dp
                            )

                            Spacer(modifier = Modifier.width(20.dp))

                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                spendData.categories.forEach { cat ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(28.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(cat.bgBoxColor),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = cat.icon,
                                                contentDescription = cat.label,
                                                tint = cat.accentColor,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = cat.label,
                                                fontFamily = PlusJakartaSansFontFamily,
                                                fontSize = 13.5.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = TextPrimary
                                            )
                                            Text(
                                                text = "₹" + String.format(java.util.Locale.US, "%,.0f", cat.amount),
                                                fontFamily = PlusJakartaSansFontFamily,
                                                fontSize = 12.sp,
                                                color = TextSecondary
                                            )
                                        }
                                        Text(
                                            text = "${cat.percentage}%",
                                            fontFamily = PlusJakartaSansFontFamily,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = cat.accentColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Category breakdown with progress bars
            item {
                Spacer(modifier = Modifier.height(14.dp))
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(30.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Category Budget Analysis",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )

                        val budgetRows = remember(reloadTrigger) { repository.getCategoryBudgetAnalysis() }
                        budgetRows.forEach { item ->
                            CategoryProgressRow(
                                label = item.label,
                                spent = "₹" + String.format(java.util.Locale.US, "%,.0f", item.spent),
                                budget = "₹" + String.format(java.util.Locale.US, "%,.0f", item.budget),
                                progress = item.progress,
                                barColor = item.barColor
                            )
                        }
                    }
                }
            }

            // Export Button
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Button(
                        onClick = { showReportDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Download, contentDescription = "Download", tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Export Statement & Analytics",
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 14.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun CategoryProgressRow(
    label: String,
    spent: String,
    budget: String,
    progress: Float,
    barColor: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, fontFamily = PlusJakartaSansFontFamily, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
            Text("$spent / $budget", fontFamily = PlusJakartaSansFontFamily, fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold, color = TextSecondary)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = barColor,
            trackColor = Color(0xFFE2E8F0)
        )
    }
}

@Composable
private fun BudgetMetricRow(
    label: String,
    value: String,
    dotColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = label,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 12.sp,
                color = TextSecondary
            )
            Text(
                text = value,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun BudgetGridTile(
    label: String,
    value: String,
    dotColor: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 11.5.sp,
                color = TextSecondary,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = value,
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            maxLines = 1
        )
    }
}

