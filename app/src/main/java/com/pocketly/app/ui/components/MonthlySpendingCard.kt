package com.pocketly.app.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketly.app.ui.theme.BrandSubhead
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
import com.pocketly.app.ui.theme.ShoppingAccent
import com.pocketly.app.ui.theme.ShoppingBg
import com.pocketly.app.ui.theme.TextPrimary
import com.pocketly.app.ui.theme.TransportAccent
import com.pocketly.app.ui.theme.TransportBg
import com.pocketly.app.ui.theme.TrendRed

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import com.pocketly.app.ui.theme.ReceivedGreen

data class SpendingCategoryItem(
    val icon: ImageVector,
    val label: String,
    val amount: String,
    val percentage: String,
    val chartColor: Color,
    val bgBoxColor: Color,
    val accentColor: Color
)

data class MonthlyData(
    val monthName: String,
    val totalSpent: String,
    val trendPercent: String,
    val isTrendDown: Boolean,
    val categories: List<SpendingCategoryItem>
)

@Composable
fun MonthlySpendingCard(modifier: Modifier = Modifier) {
    val monthsData = remember {
        listOf(
            MonthlyData(
                monthName = "Sept 2026",
                totalSpent = "₹6,000",
                trendPercent = "12%",
                isTrendDown = true,
                categories = listOf(
                    SpendingCategoryItem(
                        icon = Icons.Filled.Restaurant,
                        label = "Food",
                        amount = "₹3,500",
                        percentage = "58%",
                        chartColor = ChartFood,
                        bgBoxColor = FoodBg,
                        accentColor = FoodAccent
                    ),
                    SpendingCategoryItem(
                        icon = Icons.Filled.PhoneAndroid,
                        label = "Mobile Bills",
                        amount = "₹1,000",
                        percentage = "17%",
                        chartColor = ChartMobile,
                        bgBoxColor = MobileBg,
                        accentColor = MobileAccent
                    ),
                    SpendingCategoryItem(
                        icon = Icons.Filled.ShoppingBag,
                        label = "Shopping",
                        amount = "₹900",
                        percentage = "15%",
                        chartColor = ChartShopping,
                        bgBoxColor = ShoppingBg,
                        accentColor = ShoppingAccent
                    ),
                    SpendingCategoryItem(
                        icon = Icons.Filled.DirectionsCar,
                        label = "Transport",
                        amount = "₹600",
                        percentage = "10%",
                        chartColor = ChartTransport,
                        bgBoxColor = TransportBg,
                        accentColor = TransportAccent
                    )
                )
            ),
            MonthlyData(
                monthName = "Aug 2026",
                totalSpent = "₹8,450",
                trendPercent = "18%",
                isTrendDown = false,
                categories = listOf(
                    SpendingCategoryItem(
                        icon = Icons.Filled.Restaurant,
                        label = "Food",
                        amount = "₹4,200",
                        percentage = "50%",
                        chartColor = ChartFood,
                        bgBoxColor = FoodBg,
                        accentColor = FoodAccent
                    ),
                    SpendingCategoryItem(
                        icon = Icons.Filled.ShoppingBag,
                        label = "Shopping",
                        amount = "₹2,100",
                        percentage = "25%",
                        chartColor = ChartShopping,
                        bgBoxColor = ShoppingBg,
                        accentColor = ShoppingAccent
                    ),
                    SpendingCategoryItem(
                        icon = Icons.Filled.PhoneAndroid,
                        label = "Mobile Bills",
                        amount = "₹1,200",
                        percentage = "14%",
                        chartColor = ChartMobile,
                        bgBoxColor = MobileBg,
                        accentColor = MobileAccent
                    ),
                    SpendingCategoryItem(
                        icon = Icons.Filled.DirectionsCar,
                        label = "Transport",
                        amount = "₹950",
                        percentage = "11%",
                        chartColor = ChartTransport,
                        bgBoxColor = TransportBg,
                        accentColor = TransportAccent
                    )
                )
            ),
            MonthlyData(
                monthName = "Jul 2026",
                totalSpent = "₹5,800",
                trendPercent = "5%",
                isTrendDown = true,
                categories = listOf(
                    SpendingCategoryItem(
                        icon = Icons.Filled.Restaurant,
                        label = "Food",
                        amount = "₹3,100",
                        percentage = "53%",
                        chartColor = ChartFood,
                        bgBoxColor = FoodBg,
                        accentColor = FoodAccent
                    ),
                    SpendingCategoryItem(
                        icon = Icons.Filled.DirectionsCar,
                        label = "Transport",
                        amount = "₹1,400",
                        percentage = "24%",
                        chartColor = ChartTransport,
                        bgBoxColor = TransportBg,
                        accentColor = TransportAccent
                    ),
                    SpendingCategoryItem(
                        icon = Icons.Filled.ShoppingBag,
                        label = "Shopping",
                        amount = "₹800",
                        percentage = "14%",
                        chartColor = ChartShopping,
                        bgBoxColor = ShoppingBg,
                        accentColor = ShoppingAccent
                    ),
                    SpendingCategoryItem(
                        icon = Icons.Filled.PhoneAndroid,
                        label = "Mobile Bills",
                        amount = "₹500",
                        percentage = "9%",
                        chartColor = ChartMobile,
                        bgBoxColor = MobileBg,
                        accentColor = MobileAccent
                    )
                )
            )
        )
    }

    var selectedIndex by remember { mutableStateOf(0) }
    var showMonthDialog by remember { mutableStateOf(false) }
    val currentMonthData = monthsData[selectedIndex]

    val donutSegments = currentMonthData.categories.map { category ->
        DonutSegment(
            value = category.percentage.replace("%", "").toFloat(),
            color = category.chartColor,
            label = category.label
        )
    }

    if (showMonthDialog) {
        Dialog(onDismissRequest = { showMonthDialog = false }) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(32.dp),
                elevation = 18.dp,
                backgroundBrush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xF9FAFBFC),
                        Color(0xF0F3F6FA)
                    )
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Select Statement Period",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFF1F5F9))
                                .clickable { showMonthDialog = false },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    monthsData.forEachIndexed { idx, mData ->
                        val isSelected = selectedIndex == idx
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) Color(0xFFF1F5F9) else Color.Transparent)
                                .clickable {
                                    selectedIndex = idx
                                    showMonthDialog = false
                                }
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = mData.monthName,
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 15.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Total Spent: ${mData.totalSpent}",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.5.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    selectedIndex = idx
                                    showMonthDialog = false
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
                            )
                        }
                        if (idx < monthsData.size - 1) {
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
            }
        }
    }

    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(32.dp),
        elevation = 14.dp,
        backgroundBrush = Brush.verticalGradient(
            colors = listOf(
                Color(0xEEFFFFFF),
                Color(0x94F0F4F8),
                Color(0xC0EAF0F6)
            )
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // 1. Top Section: Pure solid white card with rounded bottom corners and drop shadow
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(
                            topStart = 32.dp,
                            topEnd = 32.dp,
                            bottomStart = 24.dp,
                            bottomEnd = 24.dp
                        ),
                        spotColor = Color(0x22000000),
                        ambientColor = Color(0x0D000000)
                    ),
                shape = RoundedCornerShape(
                    topStart = 32.dp,
                    topEnd = 32.dp,
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                ),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 14.dp)
                ) {
                    // Header Row: Title + Month picker pill
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "This Month Total Spent",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 15.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BrandSubhead
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0xFFF1F3F5))
                                .clickable { showMonthDialog = true }
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = currentMonthData.monthName,
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Select month",
                                    tint = TextPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Amount + Trend Indicator
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = currentMonthData.totalSpent,
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(
                            modifier = Modifier.padding(bottom = 3.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (currentMonthData.isTrendDown) {
                                        Icons.AutoMirrored.Filled.TrendingDown
                                    } else {
                                        Icons.AutoMirrored.Filled.TrendingUp
                                    },
                                    contentDescription = if (currentMonthData.isTrendDown) "Trend down" else "Trend up",
                                    tint = if (currentMonthData.isTrendDown) TrendRed else ReceivedGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = currentMonthData.trendPercent,
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (currentMonthData.isTrendDown) TrendRed else ReceivedGreen
                                )
                            }
                            Text(
                                text = "vs last month",
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF9CA3AF)
                            )
                        }
                    }
                }
            }

            // 2. Bottom Section: Rest of component with liquid glass appearance
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DonutChart(
                    segments = donutSegments,
                    chartSize = 104.dp,
                    strokeWidth = 20.dp
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    currentMonthData.categories.forEach { category ->
                        CategoryRowItem(category)
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryRowItem(category: SpendingCategoryItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(category.bgBoxColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.label,
                tint = category.accentColor,
                modifier = Modifier.size(15.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = category.label,
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 13.5.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = category.amount,
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 13.5.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = category.percentage,
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            color = category.accentColor,
            modifier = Modifier.width(32.dp)
        )
    }
}
