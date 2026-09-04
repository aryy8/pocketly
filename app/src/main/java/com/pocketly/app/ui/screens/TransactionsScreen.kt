package com.pocketly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.foundation.lazy.rememberLazyListState
import com.pocketly.app.ui.components.AvatarType
import com.pocketly.app.ui.components.TransactionDetailDialog
import com.pocketly.app.ui.components.TransactionItem
import com.pocketly.app.ui.components.TransactionRow
import com.pocketly.app.ui.theme.GlassCard
import com.pocketly.app.ui.theme.InriaSerifFontFamily
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily
import com.pocketly.app.ui.theme.SmoothScrollContainer
import com.pocketly.app.ui.theme.TextSecondary
import com.pocketly.app.ui.theme.rememberFluidFlingBehavior
import com.pocketly.app.ui.theme.trackpadSmoothScroll

@Composable
fun TransactionsScreen(
    onBackClick: () -> Unit = {},
    bottomPadding: Dp = 0.dp,
    initialFilter: String = "All"
) {
    var selectedFilter by remember(initialFilter) { mutableStateOf(initialFilter) }
    var selectedTransaction by remember { mutableStateOf<TransactionItem?>(null) }

    selectedTransaction?.let { item ->
        TransactionDetailDialog(
            item = item,
            onDismiss = { selectedTransaction = null }
        )
    }

    val allTransactionsSept = listOf(
        TransactionItem(
            title = "REDBUS",
            date = "3 September",
            amount = "₹1,103.95",
            isCredit = false,
            avatarBg = Color(0xFFD65B5B),
            avatarType = AvatarType.RedBus
        ),
        TransactionItem(
            title = "Swiggy",
            date = "2 September",
            amount = "₹480.00",
            isCredit = false,
            avatarBg = Color(0xFFFC8019),
            avatarType = AvatarType.Text("S")
        ),
        TransactionItem(
            title = "Uber",
            date = "1 September",
            amount = "₹320.00",
            isCredit = false,
            avatarBg = Color(0xFF1F2937),
            avatarType = AvatarType.Text("U")
        )
    )

    val allTransactionsAug = listOf(
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
            title = "Netflix",
            date = "24 August",
            amount = "₹649.00",
            isCredit = false,
            avatarBg = Color(0xFFE50914),
            avatarType = AvatarType.Text("N")
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
        ),
        TransactionItem(
            title = "Starbucks",
            date = "15 August",
            amount = "₹380.00",
            isCredit = false,
            avatarBg = Color(0xFF006241),
            avatarType = AvatarType.Text("S")
        ),
        TransactionItem(
            title = "Salary Credited",
            date = "1 August",
            amount = "+ ₹75,000",
            isCredit = true,
            avatarBg = Color(0xFF16A34A),
            avatarType = AvatarType.Text("₹")
        )
    )

    val allTransactionsJuly = listOf(
        TransactionItem(
            title = "Amazon India",
            date = "28 July",
            amount = "₹1,450.00",
            isCredit = false,
            avatarBg = Color(0xFFFF9900),
            avatarType = AvatarType.Text("A")
        ),
        TransactionItem(
            title = "Airtel Postpaid",
            date = "22 July",
            amount = "₹999.00",
            isCredit = false,
            avatarBg = Color(0xFFE40000),
            avatarType = AvatarType.Text("A")
        ),
        TransactionItem(
            title = "BookMyShow",
            date = "14 July",
            amount = "₹560.00",
            isCredit = false,
            avatarBg = Color(0xFF7C3AED),
            avatarType = AvatarType.Text("B")
        )
    )

    fun filterList(list: List<TransactionItem>): List<TransactionItem> {
        return when (selectedFilter) {
            "Expenses" -> list.filter { !it.isCredit }
            "Income" -> list.filter { it.isCredit }
            else -> list
        }
    }

    val septFiltered = filterList(allTransactionsSept)
    val augFiltered = filterList(allTransactionsAug)
    val julyFiltered = filterList(allTransactionsJuly)

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
            // Top Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.8f))
                                .clickable { onBackClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Transactions",
                            fontFamily = InriaSerifFontFamily,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Filter pills: All, Expenses, Income
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("All", "Expenses", "Income").forEach { filter ->
                            val isSelected = selectedFilter == filter
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        if (isSelected) Color.Black else Color.White.copy(alpha = 0.8f)
                                    )
                                    .clickable { selectedFilter = filter }
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = filter,
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else TextSecondary
                                )
                            }
                        }
                    }
                }
            }

            // September Section
            if (septFiltered.isNotEmpty()) {
                item {
                    SectionHeader(title = "September 2026")
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(28.dp),
                        elevation = 10.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            septFiltered.forEach { item ->
                                TransactionRow(
                                    item = item,
                                    onClick = { selectedTransaction = item }
                                )
                            }
                        }
                    }
                }
            }

            // August Section
            if (augFiltered.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    SectionHeader(title = "August 2026")
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(28.dp),
                        elevation = 10.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            augFiltered.forEach { item ->
                                TransactionRow(
                                    item = item,
                                    onClick = { selectedTransaction = item }
                                )
                            }
                        }
                    }
                }
            }

            // July Section
            if (julyFiltered.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    SectionHeader(title = "July 2026")
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(28.dp),
                        elevation = 10.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            julyFiltered.forEach { item ->
                                TransactionRow(
                                    item = item,
                                    onClick = { selectedTransaction = item }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontFamily = PlusJakartaSansFontFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextSecondary,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
    )
}
