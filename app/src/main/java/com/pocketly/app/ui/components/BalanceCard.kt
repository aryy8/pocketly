package com.pocketly.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketly.app.ui.theme.GlassCard
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily
import com.pocketly.app.ui.theme.ReceivedGreen
import com.pocketly.app.ui.theme.SpentRed
import com.pocketly.app.ui.theme.TextPrimary
import com.pocketly.app.ui.theme.TextSecondary

import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip

@Composable
fun BalanceCard(
    balance: String,
    spent: String,
    received: String,
    modifier: Modifier = Modifier,
    onSpentClick: () -> Unit = {},
    onReceivedClick: () -> Unit = {}
) {
    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 18.dp)
        ) {
            Text(
                text = "Available Balance",
                fontFamily = PlusJakartaSansFontFamily,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Normal,
                color = TextSecondary
            )

            // Slanted geometric sans-serif balance numerals matching Figma reference
            Text(
                text = "₹$balance",
                fontFamily = PlusJakartaSansFontFamily,
                fontStyle = FontStyle.Italic,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp,
                color = TextPrimary,
                modifier = Modifier.padding(top = 2.dp, bottom = 2.dp)
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 0.8.dp,
                color = Color(0x0C000000)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Spent section - clickable
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSpentClick() }
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Spent",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextSecondary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "↗",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = SpentRed
                        )
                        Text(
                            text = " ₹$spent",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SpentRed
                        )
                    }
                }

                // Received section - clickable
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onReceivedClick() }
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Received",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextSecondary
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "↖",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = ReceivedGreen
                        )
                        Text(
                            text = " ₹$received",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ReceivedGreen
                        )
                    }
                }
            }
        }
    }
}
