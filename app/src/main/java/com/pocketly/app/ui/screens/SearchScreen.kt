package com.pocketly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketly.app.ui.components.AvatarType
import com.pocketly.app.ui.components.TopBar
import com.pocketly.app.ui.components.TransactionDetailDialog
import com.pocketly.app.ui.components.TransactionItem
import com.pocketly.app.ui.components.TransactionRow
import com.pocketly.app.ui.theme.GlassCard
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily
import com.pocketly.app.ui.theme.SmoothScrollContainer
import com.pocketly.app.ui.theme.TextPrimary
import com.pocketly.app.ui.theme.TextSecondary
import com.pocketly.app.ui.theme.rememberFluidFlingBehavior
import com.pocketly.app.ui.theme.trackpadSmoothScroll

@Composable
fun SearchScreen(bottomPadding: Dp = 0.dp) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedTransaction by remember { mutableStateOf<TransactionItem?>(null) }

    val allTransactions = remember {
        listOf(
            TransactionItem("REDBUS", "3 September", "₹1,103.95", false, Color(0xFFD65B5B), AvatarType.RedBus),
            TransactionItem("Swiggy", "2 September", "₹480.00", false, Color(0xFFFC8019), AvatarType.Text("S")),
            TransactionItem("Uber", "1 September", "₹320.00", false, Color(0xFF1F2937), AvatarType.Text("U")),
            TransactionItem("Shashank Rastogi", "29 August", "+ ₹2,100", true, Color(0xFF7C929E), AvatarType.Text("S")),
            TransactionItem("Zomato", "27 August", "₹710.06", false, Color(0xFFCB202D), AvatarType.Zomato),
            TransactionItem("Netflix", "24 August", "₹649.00", false, Color(0xFFE50914), AvatarType.Text("N")),
            TransactionItem("Myntra", "20 August", "₹310", false, Color(0xFF453D3B), AvatarType.Text("M")),
            TransactionItem("ARYAN RASTOGI", "18 August", "₹900", false, Color(0xFF3B8E88), AvatarType.Text("A")),
            TransactionItem("Starbucks", "15 August", "₹380.00", false, Color(0xFF006241), AvatarType.Text("S")),
            TransactionItem("Salary Credited", "1 August", "+ ₹75,000", true, Color(0xFF16A34A), AvatarType.Text("₹")),
            TransactionItem("Amazon India", "28 July", "₹1,450.00", false, Color(0xFFFF9900), AvatarType.Text("A")),
            TransactionItem("Airtel Postpaid", "22 July", "₹999.00", false, Color(0xFFE40000), AvatarType.Text("A")),
            TransactionItem("BookMyShow", "14 July", "₹560.00", false, Color(0xFF7C3AED), AvatarType.Text("B"))
        )
    }

    val filteredList = allTransactions.filter { item ->
        val matchesQuery = searchQuery.isBlank() ||
                item.title.contains(searchQuery, ignoreCase = true) ||
                item.amount.contains(searchQuery, ignoreCase = true) ||
                item.date.contains(searchQuery, ignoreCase = true)

        val matchesFilter = when (selectedFilter) {
            "Debits" -> !item.isCredit
            "Credits" -> item.isCredit
            else -> true
        }

        matchesQuery && matchesFilter
    }

    selectedTransaction?.let { item ->
        TransactionDetailDialog(
            item = item,
            onDismiss = { selectedTransaction = null }
        )
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
                // Search Input Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        elevation = 6.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 13.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                textStyle = TextStyle(
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary
                                ),
                                cursorBrush = SolidColor(Color.Black),
                                decorationBox = { innerTextField ->
                                    if (searchQuery.isEmpty()) {
                                        Text(
                                            text = "Search merchants, amount, date...",
                                            fontFamily = PlusJakartaSansFontFamily,
                                            fontSize = 14.5.sp,
                                            color = Color(0xFF94A3B8)
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                            if (searchQuery.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE2E8F0))
                                        .clickable { searchQuery = "" },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = "Clear",
                                        tint = Color(0xFF475569),
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick suggestion pills
            item {
                Spacer(modifier = Modifier.height(12.dp))
                val suggestions = listOf("Zomato", "Salary", "Swiggy", "RedBus", "Netflix", "Uber")
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
                ) {
                    items(suggestions) { keyword ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (searchQuery.equals(keyword, ignoreCase = true)) Color.Black else Color.White.copy(alpha = 0.8f))
                                .clickable { searchQuery = keyword }
                                .padding(horizontal = 13.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = keyword,
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (searchQuery.equals(keyword, ignoreCase = true)) Color.White else TextPrimary
                            )
                        }
                    }
                }
            }

            // Filter row: All, Debits, Credits
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Debits", "Credits").forEach { filter ->
                        val isSelected = selectedFilter == filter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) Color(0xFF1E293B) else Color.White.copy(alpha = 0.7f))
                                .clickable { selectedFilter = filter }
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = filter,
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                color = if (isSelected) Color.White else TextSecondary
                            )
                        }
                    }
                }
            }

            // Results count
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${filteredList.size} transactions found",
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }

            // Transaction Cards List
            item {
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(28.dp),
                    elevation = 10.dp
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        if (filteredList.isEmpty()) {
                            Text(
                                text = "No transactions found matching \"$searchQuery\"",
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 14.sp,
                                color = TextSecondary,
                                modifier = Modifier.padding(vertical = 20.dp)
                            )
                        } else {
                            filteredList.forEach { item ->
                                TransactionRow(
                                    item = item,
                                    onClick = { selectedTransaction = item }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
