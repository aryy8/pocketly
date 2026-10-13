package com.pocketly.app.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
    var showSimulateDialog by remember { mutableStateOf(false) }

    if (showSimulateDialog) {
        SimulateBankAlertDialog(onDismiss = { showSimulateDialog = false })
    }

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

            // Smart Bank Sync (Presentation Demo) Card
            item {
                val isListenerEnabled = remember(showSimulateDialog) {
                    val pkgName = context.packageName
                    val flat = android.provider.Settings.Secure.getString(
                        context.contentResolver,
                        "enabled_notification_listeners"
                    )
                    flat != null && flat.contains(pkgName)
                }

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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Smart Bank Notification Sync",
                                fontFamily = PlusJakartaSansFontFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isListenerEnabled) Color(0xFFDCFCE7) else Color(0xFFFEF3C7))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = if (isListenerEnabled) "Active" else "Setup Needed",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isListenerEnabled) ReceivedGreen else Color(0xFFD97706)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Listens passively on-device for incoming HDFC, SBI, and UPI transaction notifications and logs them automatically.",
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 12.5.sp,
                            color = TextSecondary,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (!isListenerEnabled) {
                                OutlinedButton(
                                    onClick = {
                                        val intent = android.content.Intent(android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Text(
                                        text = "Enable Access",
                                        fontFamily = PlusJakartaSansFontFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )
                                }
                            }

                            Button(
                                onClick = { showSimulateDialog = true },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A))
                            ) {
                                Text(
                                    text = "Simulate Bank Alert",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 14.dp),
                            color = Color(0xFFE2E8F0)
                        )

                        val isAutoRunning by com.pocketly.app.service.AutoNotificationScheduler.isRunning
                        val nextEpoch by com.pocketly.app.service.AutoNotificationScheduler.nextTriggerEpoch
                        var remainingSeconds by remember { mutableLongStateOf(0L) }

                        LaunchedEffect(isAutoRunning, nextEpoch) {
                            while (isAutoRunning) {
                                val diff = (nextEpoch - System.currentTimeMillis()) / 1000L
                                remainingSeconds = if (diff > 0) diff else 0L
                                kotlinx.coroutines.delay(1000L)
                            }
                        }

                        val formattedRemaining = remember(remainingSeconds) {
                            val m = remainingSeconds / 60
                            val s = remainingSeconds % 60
                            "${m}m ${s}s"
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Auto-Simulate (Every 2 Mins)",
                                        fontFamily = PlusJakartaSansFontFamily,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextPrimary
                                    )
                                    if (isAutoRunning) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(ReceivedGreen)
                                        )
                                    }
                                }
                                Text(
                                    text = if (isAutoRunning) "Active: next alert in ~$formattedRemaining" else "Delivers real bank alerts automatically every 2 mins",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.sp,
                                    color = if (isAutoRunning) ReceivedGreen else TextSecondary,
                                    lineHeight = 16.sp
                                )
                            }

                            Switch(
                                checked = isAutoRunning,
                                onCheckedChange = { checked ->
                                    userPreferences.autoSimulateAlerts = checked
                                    if (checked) {
                                        com.pocketly.app.service.AutoNotificationScheduler.start(context)
                                    } else {
                                        com.pocketly.app.service.AutoNotificationScheduler.stop(context)
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Color(0xFF1E3A8A)
                                )
                            )
                        }

                        if (isAutoRunning) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = "⚡ Skip wait & trigger now",
                                    fontFamily = PlusJakartaSansFontFamily,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF2563EB),
                                    modifier = Modifier
                                        .clickable {
                                            com.pocketly.app.service.AutoNotificationScheduler.triggerNow(context)
                                        }
                                        .padding(vertical = 2.dp)
                                )
                            }
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

@Composable
private fun SimulateBankAlertDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { com.pocketly.app.data.TransactionRepository(context) }
    var selectedPresetText by remember {
        mutableStateOf("HDFC Bank: Rs 480.00 debited from A/C XX2673 on 13-Oct for Swiggy.")
    }
    var feedbackMessage by remember { mutableStateOf<String?>(null) }
    var isSuccess by remember { mutableStateOf(true) }

    val presets = listOf(
        "Swiggy ₹480 (Food)" to "HDFC Bank: Rs 480.00 debited from A/C XX2673 on 13-Oct for Swiggy.",
        "Zomato ₹720 (Food)" to "SBI: Rs 720.00 debited from A/C XX8958 at Zomato.",
        "Uber ₹250 (Transport)" to "HDFC Bank: Rs 250.00 debited from A/C XX2673 at Uber.",
        "Airtel ₹1,000 (Bills)" to "SBI: Rs 1,000.00 debited from A/C XX8958 for Airtel Postpaid.",
        "Salary ₹15,000 (Income)" to "Salary Credited: Rs 15,000.00 deposited into A/C XX2673."
    )

    Dialog(onDismissRequest = onDismiss) {
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            shape = RoundedCornerShape(28.dp),
            elevation = 16.dp,
            backgroundBrush = Brush.verticalGradient(
                colors = listOf(Color.White, Color(0xFFF8FAFC))
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Simulate Bank Alert",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF1F5F9))
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("✕", fontSize = 14.sp, color = Color(0xFF64748B))
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Pick a preset to test on-device regex parsing and instant SQLite ingestion.",
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Select Sample Alert:",
                    fontFamily = PlusJakartaSansFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                presets.forEach { (label, smsText) ->
                    val isSelected = selectedPresetText == smsText
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) Color(0xFFEFF6FF) else Color(0xFFF8FAFC))
                            .clickable {
                                selectedPresetText = smsText
                                feedbackMessage = null
                            }
                            .padding(horizontal = 10.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color(0xFF1D4ED8) else Color(0xFF334155),
                            modifier = Modifier.weight(1f)
                        )
                        if (isSelected) {
                            Text("●", fontSize = 10.sp, color = Color(0xFF1D4ED8))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Spacer(modifier = Modifier.height(10.dp))

                feedbackMessage?.let { msg ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSuccess) Color(0xFFDCFCE7) else Color(0xFFFEE2E2))
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = msg,
                            fontFamily = PlusJakartaSansFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSuccess) Color(0xFF166534) else Color(0xFF991B1B)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // 1. Post Real Android Notification (Triggers OS NotificationListenerService)
                Button(
                    onClick = {
                        val posted = com.pocketly.app.service.SystemNotificationSimulator.postNotification(
                            context = context,
                            title = "HDFC Bank Alert",
                            message = selectedPresetText
                        )
                        if (posted) {
                            isSuccess = true
                            feedbackMessage = "Real notification pushed! Android OS is routing it through BankNotificationListenerService..."
                        } else {
                            isSuccess = false
                            feedbackMessage = "Failed to post notification. Check app permissions."
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A))
                ) {
                    Text(
                        text = "🔔 Push Real Android Notification",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 2. Direct Ingest fallback
                OutlinedButton(
                    onClick = {
                        val parsed = com.pocketly.app.service.BankNotificationParser.parse("Bank Alert", selectedPresetText)
                        if (parsed != null) {
                            repository.insertTransaction(
                                title = parsed.title,
                                amount = parsed.amount,
                                isCredit = parsed.isCredit,
                                category = parsed.category,
                                isRecurring = false,
                                accountName = parsed.accountName,
                                paymentMode = parsed.paymentMode
                            )
                            val intent = android.content.Intent(com.pocketly.app.service.BankNotificationListenerService.ACTION_TRANSACTION_CAPTURED).apply {
                                setPackage(context.packageName)
                            }
                            context.sendBroadcast(intent)
                            isSuccess = true
                            feedbackMessage = "Captured: ${parsed.title} -> ₹${parsed.amount.toInt()} (${parsed.category}) saved to SQLite!"
                        } else {
                            isSuccess = false
                            feedbackMessage = "Could not detect bank amount or transaction keyword."
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1))
                ) {
                    Text(
                        text = "⚡ Direct Ingest & Parse",
                        fontFamily = PlusJakartaSansFontFamily,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF334155)
                    )
                }
            }
        }
    }
}

