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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pocketly.app.data.UserPreferences
import com.pocketly.app.ui.components.TopBar
import com.pocketly.app.ui.theme.GlassCard
import com.pocketly.app.ui.theme.PlusJakartaSansFontFamily
import com.pocketly.app.ui.theme.ReceivedGreen
import com.pocketly.app.ui.theme.SmoothScrollContainer
import com.pocketly.app.ui.theme.SpentRed
import com.pocketly.app.ui.theme.TextPrimary
import com.pocketly.app.ui.theme.TextSecondary
import com.pocketly.app.ui.theme.rememberFluidFlingBehavior
import com.pocketly.app.ui.theme.trackpadSmoothScroll

@Composable
fun ProfileScreen(
    bottomPadding: Dp = 0.dp,
    onSignOut: () -> Unit = {}
) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }

    var pushNotificationsEnabled by remember { mutableStateOf(true) }
    var budgetAlertsEnabled by remember { mutableStateOf(false) }

    var copyFeedback by remember { mutableStateOf(false) }
    var showSignOutDialog by remember { mutableStateOf(false) }

    if (showSignOutDialog) {
        Dialog(onDismissRequest = { showSignOutDialog = false }) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(28.dp),
                elevation = 16.dp,
                backgroundBrush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFF8FAFC))
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sign Out",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Are you sure you want to sign out of Pocketly? You can log back in at any time.",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 13.5.sp,
                        color = TextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { showSignOutDialog = false },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("Cancel", fontFamily = PlusJakartaSansFontFamily, color = TextPrimary)
                        }
                        Button(
                            onClick = {
                                showSignOutDialog = false
                                onSignOut()
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SpentRed)
                        ) {
                            Text("Sign Out", fontFamily = PlusJakartaSansFontFamily, color = Color.White)
                        }
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
                // Profile Avatar & Info Card (Sideways Layout)
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(26.dp),
                    elevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(Color(0xFF0F172A), Color(0xFF334155))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "A",
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(18.dp))

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = userPreferences.userName.ifBlank { "Aryan Rastogi" },
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "aryanfeb17@gmail.com",
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 13.5.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }

            // Linked UPI ID Card with copy button
            item {
                Spacer(modifier = Modifier.height(12.dp))
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(22.dp),
                    elevation = 6.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Primary UPI VPA",
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "aryan@okhdfcbank",
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 14.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (copyFeedback) ReceivedGreen.copy(alpha = 0.15f) else Color(0xFFF1F5F9))
                                .clickable { copyFeedback = true }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (copyFeedback) Icons.Filled.Check else Icons.Filled.ContentCopy,
                                    contentDescription = "Copy",
                                    tint = if (copyFeedback) ReceivedGreen else Color(0xFF334155),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (copyFeedback) "Copied!" else "Copy",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (copyFeedback) ReceivedGreen else Color(0xFF334155)
                                )
                            }
                        }
                    }
                }
            }

            // Settings & Preferences Card
            item {
                Spacer(modifier = Modifier.height(14.dp))
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(26.dp),
                    elevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)
                    ) {
                        Text(
                            text = "Preferences & Security",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Switch 1: Push Notifications
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Push Notifications",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 14.5.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Real-time transaction alerts",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                            Switch(
                                checked = pushNotificationsEnabled,
                                onCheckedChange = { pushNotificationsEnabled = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color.Black
                                )
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0x0C000000))

                        // Switch 2: Budget Alerts
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Budget Exceeded Alerts",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 14.5.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Alert when 80% monthly limit reached",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                            Switch(
                                checked = budgetAlertsEnabled,
                                onCheckedChange = { budgetAlertsEnabled = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color.Black
                                )
                            )
                        }
                    }
                }
            }

            // Sign out Card
            item {
                Spacer(modifier = Modifier.height(14.dp))
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(22.dp),
                    elevation = 6.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showSignOutDialog = true }
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sign Out of Pocketly",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SpentRed
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Sign Out",
                            tint = SpentRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
}
