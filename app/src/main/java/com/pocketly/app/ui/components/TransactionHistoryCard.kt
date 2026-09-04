package com.pocketly.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketly.app.ui.theme.GlassCard
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily
import com.pocketly.app.ui.theme.TextPrimary
import com.pocketly.app.ui.theme.TextSecondary

sealed class AvatarType {
    data class Text(val letter: String) : AvatarType()
    object RedBus : AvatarType()
    object Zomato : AvatarType()
}

data class TransactionItem(
    val title: String,
    val date: String,
    val amount: String,
    val isCredit: Boolean = false,
    val avatarBg: Color,
    val avatarType: AvatarType
)

@Composable
fun TransactionHistoryCard(
    modifier: Modifier = Modifier,
    onSeeAllClick: () -> Unit = {}
) {
    val transactions = listOf(
        TransactionItem(
            title = "REDBUS",
            date = "3 September",
            amount = "₹1,103.95",
            isCredit = false,
            avatarBg = Color(0xFFD65B5B),
            avatarType = AvatarType.RedBus
        ),
        TransactionItem(
            title = "Shashank Rastogi",
            date = "29 August",
            amount = "+ ₹2,100",
            isCredit = true,
            avatarBg = Color(0xFF7C929E),
            avatarType = AvatarType.Text("S")
        ),
        TransactionItem(
            title = "Zomato",
            date = "27 August",
            amount = "₹710.06",
            isCredit = false,
            avatarBg = Color(0xFFCB202D),
            avatarType = AvatarType.Zomato
        ),
        TransactionItem(
            title = "Myntra",
            date = "20 August",
            amount = "₹310",
            isCredit = false,
            avatarBg = Color(0xFF453D3B),
            avatarType = AvatarType.Text("M")
        ),
        TransactionItem(
            title = "ARYAN RASTOGI",
            date = "18 August",
            amount = "₹900",
            isCredit = false,
            avatarBg = Color(0xFF3B8E88),
            avatarType = AvatarType.Text("A")
        )
    )

    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(32.dp),
        elevation = 12.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            // Header Row: "Transaction history" and "See all >"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transaction history",
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = (-0.3).sp,
                    color = TextPrimary
                )

                Row(
                    modifier = Modifier.clickable { onSeeAllClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "See all",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2563EB)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "See all",
                        tint = Color(0xFF2563EB),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Transaction items list
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                transactions.forEach { item ->
                    TransactionRow(item = item)
                }
            }
        }
    }
}

@Composable
fun TransactionRow(item: TransactionItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circular Avatar / Logo
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(item.avatarBg),
            contentAlignment = Alignment.Center
        ) {
            when (item.avatarType) {
                is AvatarType.Text -> {
                    Text(
                        text = item.avatarType.letter,
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
                is AvatarType.RedBus -> {
                    RedBusLogo()
                }
                is AvatarType.Zomato -> {
                    Text(
                        text = "zomato",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 9.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Title and Date
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Text(
                text = item.date,
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Normal,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        // Amount
        Text(
            text = item.amount,
            fontFamily = PlusJakartaSansFontFamily,
            fontSize = 16.5.sp,
            fontWeight = FontWeight.Bold,
            color = if (item.isCredit) Color(0xFF2E7D32) else TextPrimary
        )
    }
}

/**
 * RedBus custom logo icon matching transaction_history.jpg
 */
@Composable
private fun RedBusLogo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .border(1.2.dp, Color.White, RoundedCornerShape(4.dp))
                .padding(horizontal = 3.dp, vertical = 1.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "redBus",
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 7.5.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(1.dp))
        // Wheels and Axle
        Canvas(modifier = Modifier.size(width = 22.dp, height = 4.dp)) {
            val wheelRadius = 1.6.dp.toPx()
            val leftWheelCenter = Offset(5.dp.toPx(), 2.dp.toPx())
            val rightWheelCenter = Offset(size.width - 5.dp.toPx(), 2.dp.toPx())

            // Axle
            drawLine(
                color = Color.White,
                start = leftWheelCenter,
                end = rightWheelCenter,
                strokeWidth = 1.dp.toPx()
            )
            // Left Wheel
            drawCircle(
                color = Color.White,
                radius = wheelRadius,
                center = leftWheelCenter
            )
            // Right Wheel
            drawCircle(
                color = Color.White,
                radius = wheelRadius,
                center = rightWheelCenter
            )
        }
    }
}
