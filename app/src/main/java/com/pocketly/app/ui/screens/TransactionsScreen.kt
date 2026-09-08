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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketly.app.data.TransactionRepository
import com.pocketly.app.ui.components.AddTransactionDialog
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
    val context = LocalContext.current
    val repository = remember { TransactionRepository(context) }

    var selectedFilter by remember(initialFilter) { mutableStateOf(initialFilter) }
    var selectedTransaction by remember { mutableStateOf<TransactionItem?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var reloadTrigger by remember { mutableIntStateOf(0) }

    val dbTransactions = remember(reloadTrigger) { repository.getAllTransactions() }

    val filteredRecords = remember(dbTransactions, selectedFilter) {
        when (selectedFilter) {
            "Expenses" -> dbTransactions.filter { !it.isCredit }
            "Income" -> dbTransactions.filter { it.isCredit }
            else -> dbTransactions
        }
    }

    val groupedByMonth = remember(filteredRecords) {
        filteredRecords.groupBy { it.month }
    }

    if (showAddDialog) {
        AddTransactionDialog(
            onDismiss = { showAddDialog = false },
            onTransactionAdded = {
                reloadTrigger++
            }
        )
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
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
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

                        // Add transaction action button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .shadow(6.dp, CircleShape)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xF8FFFFFF),
                                            Color(0xEEF8FAFC),
                                            Color(0xD9E2E8F0)
                                        )
                                    )
                                )
                                .border(
                                    border = BorderStroke(
                                        1.2.dp,
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.White,
                                                Color.White.copy(alpha = 0.6f),
                                                Color(0x20000000)
                                            )
                                        )
                                    ),
                                    shape = CircleShape
                                )
                                .clickable { showAddDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Transaction",
                                tint = Color(0xFF0F172A),
                                modifier = Modifier.size(20.dp)
                            )
                        }
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

            // Dynamically render each month section from SQLite
            if (groupedByMonth.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No transactions found for $selectedFilter",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                groupedByMonth.forEach { (month, records) ->
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        SectionHeader(title = month)
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
                                records.forEach { record ->
                                    val item = record.toTransactionItem()
                                    TransactionRow(
                                        item = item,
                                        onClick = { selectedTransaction = item }
                                    )
                                }
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
