package com.pocketly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pocketly.app.data.TransactionRepository
import com.pocketly.app.ui.components.AddTransactionDialog
import com.pocketly.app.ui.components.BalanceCard
import com.pocketly.app.ui.components.BottomNavBar
import com.pocketly.app.ui.components.ConnectedAccountsCard
import com.pocketly.app.ui.components.GreetingSection
import com.pocketly.app.ui.components.MonthlySpendingCard
import com.pocketly.app.ui.components.TopBar
import com.pocketly.app.ui.components.TransactionDetailDialog
import com.pocketly.app.ui.components.TransactionHistoryCard
import com.pocketly.app.ui.components.TransactionItem
import com.pocketly.app.ui.theme.SmoothScrollContainer
import com.pocketly.app.ui.theme.rememberFluidFlingBehavior
import com.pocketly.app.ui.theme.trackpadSmoothScroll
import java.util.Locale

@Composable
fun HomeScreen(
    onSignOut: () -> Unit = {}
) {
    val context = LocalContext.current
    val repository = remember { TransactionRepository(context) }
    val userPreferences = remember { com.pocketly.app.data.UserPreferences(context) }

    var selectedTab by remember { mutableIntStateOf(0) }
    var transactionsInitialFilter by remember { mutableStateOf("All") }
    var selectedTransaction by remember { mutableStateOf<TransactionItem?>(null) }
    var showAddTransactionDialog by remember { mutableStateOf(false) }
    var reloadTrigger by remember { mutableIntStateOf(0) }

    // Read persistent data from SQLite database
    val dbTransactions = remember(reloadTrigger) { repository.getAllTransactions() }
    val balanceSummary = remember(reloadTrigger) { repository.getBalanceSummary() }

    val formattedBalance = String.format(Locale.US, "%,.0f", balanceSummary.totalBalance)
    val formattedSpent = String.format(Locale.US, "%,.0f", balanceSummary.totalSpent)
    val formattedReceived = String.format(Locale.US, "%,.0f", balanceSummary.totalReceived)

    val transactionItems = remember(dbTransactions) {
        dbTransactions.map { it.toTransactionItem() }
    }

    // Auto-refresh when BankNotificationListenerService captures a transaction
    androidx.compose.runtime.DisposableEffect(context) {
        val receiver = object : android.content.BroadcastReceiver() {
            override fun onReceive(c: android.content.Context?, intent: android.content.Intent?) {
                reloadTrigger++
            }
        }
        val filter = android.content.IntentFilter(com.pocketly.app.service.BankNotificationListenerService.ACTION_TRANSACTION_CAPTURED)
        androidx.core.content.ContextCompat.registerReceiver(
            context,
            receiver,
            filter,
            androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
        )
        onDispose {
            try {
                context.unregisterReceiver(receiver)
            } catch (_: Exception) {}
        }
    }

    if (showAddTransactionDialog) {
        AddTransactionDialog(
            onDismiss = { showAddTransactionDialog = false },
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

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 18.dp, end = 4.dp)
                        .size(54.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = CircleShape,
                            spotColor = Color(0x38000000),
                            ambientColor = Color(0x18000000)
                        )
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
                                width = 1.2.dp,
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White,
                                        Color.White.copy(alpha = 0.6f),
                                        Color(0x20000000)
                                    )
                                )
                            ),
                            shape = CircleShape
                        )
                        .clickable { showAddTransactionDialog = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Transaction",
                        tint = Color(0xFF0F172A),
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8F9FA),
                            Color(0xFFF1F3F5),
                            Color(0xFFE9ECEF)
                        )
                    )
                )
        ) {
            when (selectedTab) {
                0 -> {
                    val listState = rememberLazyListState()
                    val flingBehavior = rememberFluidFlingBehavior()

                    SmoothScrollContainer {
                        LazyColumn(
                            state = listState,
                            flingBehavior = flingBehavior,
                            modifier = Modifier
                                .fillMaxSize()
                                .trackpadSmoothScroll(listState)
                                .padding(bottom = innerPadding.calculateBottomPadding())
                        ) {
                            item {
                                TopBar()
                            }

                            item {
                                Spacer(modifier = Modifier.height(10.dp))
                                GreetingSection(
                                    name = userPreferences.userName,
                                    date = "4th, Sept 2026"
                                )
                            }

                            item {
                                Spacer(modifier = Modifier.height(6.dp))
                                BalanceCard(
                                    balance = formattedBalance,
                                    spent = formattedSpent,
                                    received = formattedReceived,
                                    onSpentClick = {
                                        transactionsInitialFilter = "Expenses"
                                        selectedTab = 1
                                    },
                                    onReceivedClick = {
                                        transactionsInitialFilter = "Income"
                                        selectedTab = 1
                                    }
                                )
                            }

                            item {
                                Spacer(modifier = Modifier.height(10.dp))
                                ConnectedAccountsCard()
                            }

                            item {
                                Spacer(modifier = Modifier.height(10.dp))
                                MonthlySpendingCard(reloadTrigger = reloadTrigger)
                            }

                            item {
                                Spacer(modifier = Modifier.height(10.dp))
                                TransactionHistoryCard(
                                    transactionsList = transactionItems,
                                    onSeeAllClick = {
                                        transactionsInitialFilter = "All"
                                        selectedTab = 1
                                    },
                                    onTransactionClick = { item ->
                                        selectedTransaction = item
                                    }
                                )
                            }

                            item {
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                }
                1 -> TransactionsScreen(
                    onBackClick = { selectedTab = 0 },
                    bottomPadding = innerPadding.calculateBottomPadding(),
                    initialFilter = transactionsInitialFilter,
                    reloadTrigger = reloadTrigger
                )
                2 -> SearchScreen(
                    bottomPadding = innerPadding.calculateBottomPadding()
                )
                3 -> AnalyticsScreen(
                    bottomPadding = innerPadding.calculateBottomPadding(),
                    reloadTrigger = reloadTrigger
                )
                4 -> ProfileScreen(
                    bottomPadding = innerPadding.calculateBottomPadding(),
                    onSignOut = onSignOut
                )
            }
        }
    }
}
