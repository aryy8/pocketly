package com.pocketly.app.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pocketly.app.ui.components.TopBar
import com.pocketly.app.ui.theme.ChartFood
import com.pocketly.app.ui.theme.ChartMobile
import com.pocketly.app.ui.theme.ChartShopping
import com.pocketly.app.ui.theme.ChartTransport
import com.pocketly.app.ui.theme.GlassCard
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily
import com.pocketly.app.ui.theme.ReceivedGreen
import com.pocketly.app.ui.theme.SmoothScrollContainer
import com.pocketly.app.ui.theme.TextPrimary
import com.pocketly.app.ui.theme.TextSecondary
import com.pocketly.app.ui.theme.rememberFluidFlingBehavior
import com.pocketly.app.ui.theme.trackpadSmoothScroll

data class MonthlyBar(
    val month: String,
    val amount: Float,
    val displayAmount: String
)

@Composable
fun AnalyticsScreen(bottomPadding: Dp = 0.dp) {
    val barData = remember {
        listOf(
            MonthlyBar("May", 5.2f, "₹5,200"),
            MonthlyBar("Jun", 7.2f, "₹7,200"),
            MonthlyBar("Jul", 5.8f, "₹5,800"),
            MonthlyBar("Aug", 8.45f, "₹8,450"),
            MonthlyBar("Sep", 6.0f, "₹6,000")
        )
    }
    var selectedBarIndex by remember { mutableIntStateOf(4) }
    var showReportDialog by remember { mutableStateOf(false) }

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

                        // Food Category
                        CategoryProgressRow(
                            label = "Food & Dining",
                            spent = "₹3,500",
                            budget = "₹5,000",
                            progress = 0.70f,
                            barColor = ChartFood
                        )

                        // Mobile Bills Category
                        CategoryProgressRow(
                            label = "Utilities & Mobile",
                            spent = "₹1,000",
                            budget = "₹1,500",
                            progress = 0.66f,
                            barColor = ChartMobile
                        )

                        // Shopping Category
                        CategoryProgressRow(
                            label = "Shopping & Lifestyle",
                            spent = "₹900",
                            budget = "₹2,000",
                            progress = 0.45f,
                            barColor = ChartShopping
                        )

                        // Transport Category
                        CategoryProgressRow(
                            label = "Cab & Transport",
                            spent = "₹600",
                            budget = "₹1,200",
                            progress = 0.50f,
                            barColor = ChartTransport
                        )
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
