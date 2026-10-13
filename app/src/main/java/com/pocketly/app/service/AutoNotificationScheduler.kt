package com.pocketly.app.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf

class AutoNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AutoNotifReceiver", "AlarmManager broadcast received -> firing alert")
        AutoNotificationScheduler.fireAlert(context)
    }
}

object AutoNotificationScheduler {
    private const val TAG = "AutoNotifScheduler"
    const val INTERVAL_MILLIS = 2 * 60 * 1000L // 2 minutes

    private val handler = Handler(Looper.getMainLooper())
    private var appContext: Context? = null

    val isRunning = mutableStateOf(false)
    val nextTriggerEpoch = mutableLongStateOf(0L)
    private var alertIndex = 0

    private val sampleAlerts = listOf(
        "HDFC Bank Alert" to "Dear Customer, Rs. 480.00 debited from a/c **2673 on 04-Sep-26 to Swiggy UPI. Available balance Rs. 87,520.",
        "SBI UPI Alert" to "Paid Rs. 250.00 to Uber India via UPI ref 49201 on 04-Sep-26. Debited from SBI-8958.",
        "HDFC Bank Alert" to "Debited Rs. 720.00 from a/c **2673 for Zomato order on 04-Sep-26 via UPI.",
        "Salary Alert" to "Salary Credited: Rs 15,000.00 deposited into A/C XX2673 on 04-Sep-26 by Employer.",
        "SBI UPI Alert" to "Paid Rs. 1,000.00 for Airtel Postpaid bill on 04-Sep-26 from a/c **8958.",
        "HDFC Bank Alert" to "Dear Customer, Rs. 380.00 debited from a/c **2673 on 04-Sep-26 at Starbucks."
    )

    private val tickerRunnable = object : Runnable {
        override fun run() {
            if (!isRunning.value) return
            val now = System.currentTimeMillis()
            if (now >= nextTriggerEpoch.longValue && nextTriggerEpoch.longValue > 0L) {
                appContext?.let { fireAlert(it) }
            } else {
                handler.postDelayed(this, 1000L)
            }
        }
    }

    fun start(context: Context) {
        appContext = context.applicationContext
        isRunning.value = true
        val targetEpoch = System.currentTimeMillis() + INTERVAL_MILLIS
        nextTriggerEpoch.longValue = targetEpoch

        scheduleAlarm(appContext!!, targetEpoch)

        handler.removeCallbacks(tickerRunnable)
        handler.postDelayed(tickerRunnable, 1000L)
        Log.i(TAG, "AutoNotificationScheduler started. Next alert scheduled in 2 minutes.")
    }

    fun stop(context: Context? = null) {
        isRunning.value = false
        nextTriggerEpoch.longValue = 0L
        handler.removeCallbacks(tickerRunnable)

        val ctx = context ?: appContext
        ctx?.let { cancelAlarm(it) }
        Log.i(TAG, "AutoNotificationScheduler stopped.")
    }

    fun triggerNow(context: Context) {
        fireAlert(context.applicationContext)
    }

    fun fireAlert(context: Context) {
        val (title, text) = sampleAlerts[alertIndex % sampleAlerts.size]
        alertIndex++
        Log.i(TAG, "Firing automatic simulated bank alert: $title -> $text")

        SystemNotificationSimulator.postNotification(context, title, text)

        if (isRunning.value) {
            val nextEpoch = System.currentTimeMillis() + INTERVAL_MILLIS
            nextTriggerEpoch.longValue = nextEpoch
            scheduleAlarm(context, nextEpoch)
            handler.removeCallbacks(tickerRunnable)
            handler.postDelayed(tickerRunnable, 1000L)
        }
    }

    private fun scheduleAlarm(context: Context, triggerAtMillis: Long) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AutoNotificationReceiver::class.java)
            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
            val pendingIntent = PendingIntent.getBroadcast(context, 1001, intent, flags)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to schedule AlarmManager", e)
        }
    }

    private fun cancelAlarm(context: Context) {
        try {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AutoNotificationReceiver::class.java)
            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
            val pendingIntent = PendingIntent.getBroadcast(context, 1001, intent, flags)
            alarmManager.cancel(pendingIntent)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to cancel AlarmManager", e)
        }
    }
}
