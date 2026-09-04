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

data class SpendingCategoryItem(
    val icon: ImageVector,
    val label: String,
    val amount: String,
    val percentage: String,
    val chartColor: Color,
    val bgBoxColor: Color,
    val accentColor: Color
)

@Composable
fun MonthlySpendingCard(modifier: Modifier = Modifier) {
    val categories = listOf(
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

    val donutSegments = categories.map { category ->
        DonutSegment(
            value = category.percentage.replace("%", "").toFloat(),
            color = category.chartColor,
            label = category.label
        )
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
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Sept 2026",
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

                    // Amount + Red Trend Indicator
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "₹6,000",
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
                                    imageVector = Icons.AutoMirrored.Filled.TrendingDown,
                                    contentDescription = "Trend down",
                                    tint = TrendRed,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "12%",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 13.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TrendRed
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
                    categories.forEach { category ->
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
