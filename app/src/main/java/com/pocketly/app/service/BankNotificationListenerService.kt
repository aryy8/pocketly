package com.pocketly.app.service

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.pocketly.app.data.TransactionRepository

class BankNotificationListenerService : NotificationListenerService() {

    companion object {
        const val TAG = "BankNotifListener"
        const val ACTION_TRANSACTION_CAPTURED = "com.pocketly.app.ACTION_TRANSACTION_CAPTURED"
        const val EXTRA_TRANSACTION_TITLE = "extra_title"
        const val EXTRA_TRANSACTION_AMOUNT = "extra_amount"
        const val EXTRA_IS_CREDIT = "extra_is_credit"
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if (sbn == null) return

        val notification = sbn.notification ?: return
        val extras = notification.extras ?: return

        val title = extras.getString(Notification.EXTRA_TITLE) ?: ""
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()
            ?: extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString()
            ?: ""

        Log.d(TAG, "Notification received from ${sbn.packageName}: title='$title', text='$text'")

        val parsed = BankNotificationParser.parse(title, text) ?: return

        Log.i(TAG, "Bank Transaction Detected: ${parsed.title} -> ₹${parsed.amount} (isCredit=${parsed.isCredit})")

        // Persist transaction directly into SQLite database
        try {
            val repository = TransactionRepository(applicationContext)
            repository.insertTransaction(
                title = parsed.title,
                amount = parsed.amount,
                isCredit = parsed.isCredit,
                category = parsed.category,
                isRecurring = false,
                accountName = parsed.accountName,
                paymentMode = parsed.paymentMode
            )

            // Broadcast to any active UI screen so the UI reloads immediately
            val broadcastIntent = Intent(ACTION_TRANSACTION_CAPTURED).apply {
                setPackage(packageName)
                putExtra(EXTRA_TRANSACTION_TITLE, parsed.title)
                putExtra(EXTRA_TRANSACTION_AMOUNT, parsed.amount)
                putExtra(EXTRA_IS_CREDIT, parsed.isCredit)
            }
            sendBroadcast(broadcastIntent)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save parsed transaction into SQLite", e)
        }
    }
}
