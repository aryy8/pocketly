package com.pocketly.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

/**
 * Helper to post real Android system notifications directly into the OS notification tray.
 * This triggers the Android NotificationListenerService passively just like real bank SMS apps.
 */
object SystemNotificationSimulator {
    const val CHANNEL_ID = "pocketly_bank_alerts"

    fun postNotification(
        context: Context,
        title: String = "HDFC Bank Alert",
        message: String = "Dear Customer, Rs. 480.00 debited from a/c **2673 on 04-Sep-26 to Swiggy UPI. Available balance Rs. 87,520."
    ): Boolean {
        return try {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Bank & UPI Alerts",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Simulated bank and UPI transaction alerts"
                    enableVibration(true)
                }
                notificationManager.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            val notificationId = (System.currentTimeMillis() % 10000).toInt()
            notificationManager.notify(notificationId, notification)
            true
        } catch (e: Exception) {
            android.util.Log.e("SystemNotifSim", "Error posting notification", e)
            false
        }
    }
}
