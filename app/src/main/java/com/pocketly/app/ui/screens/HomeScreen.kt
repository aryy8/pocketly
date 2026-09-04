package com.pocketly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var transactionsInitialFilter by remember { mutableStateOf("All") }
    var selectedTransaction by remember { mutableStateOf<TransactionItem?>(null) }

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
                                    name = "Aryan",
                                    date = "4th, Sept 2026"
                                )
                            }

                            item {
                                Spacer(modifier = Modifier.height(6.dp))
                                BalanceCard(
                                    balance = "88,000",
                                    spent = "6,000",
                                    received = "10,000",
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
                                MonthlySpendingCard()
                            }

                            item {
                                Spacer(modifier = Modifier.height(10.dp))
                                TransactionHistoryCard(
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
                    initialFilter = transactionsInitialFilter
                )
                2 -> SearchScreen(
                    bottomPadding = innerPadding.calculateBottomPadding()
                )
                3 -> AnalyticsScreen(
                    bottomPadding = innerPadding.calculateBottomPadding()
                )
                4 -> ProfileScreen(
                    bottomPadding = innerPadding.calculateBottomPadding()
                )
            }
        }
    }
}
