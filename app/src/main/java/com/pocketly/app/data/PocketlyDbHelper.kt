package com.pocketly.app.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.compose.ui.graphics.Color
import com.pocketly.app.ui.components.AvatarType
import com.pocketly.app.ui.components.TransactionItem
import java.util.Locale

data class TransactionRecord(
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val isCredit: Boolean,
    val category: String,
    val date: String,
    val month: String,
    val isRecurring: Boolean = false,
    val accountName: String = "Main Account",
    val paymentMode: String = "UPI" // UPI, Cash, Cheque
) {
    fun toTransactionItem(): TransactionItem {
        val (bg, avatar) = when (title.lowercase(Locale.ROOT)) {
            "redbus" -> Color(0xFFD65B5B) to AvatarType.RedBus
            "zomato" -> Color(0xFFE23744) to AvatarType.Zomato
            "swiggy" -> Color(0xFFFC8019) to AvatarType.Text("S")
            "uber" -> Color(0xFF1E293B) to AvatarType.Text("U")
            "netflix" -> Color(0xFFE50914) to AvatarType.Text("N")
            "myntra" -> Color(0xFF535766) to AvatarType.Text("M")
            "starbucks" -> Color(0xFF006241) to AvatarType.Text("S")
            else -> {
                val letter = title.firstOrNull()?.uppercase() ?: "P"
                val defaultColor = when (category.lowercase(Locale.ROOT)) {
                    "food" -> Color(0xFFE23744)
                    "shopping" -> Color(0xFFE58A32)
                    "transport" -> Color(0xFF386BF6)
                    "bills", "mobile bills" -> Color(0xFF2E9E68)
                    "income" -> Color(0xFF10B981)
                    else -> Color(0xFF4C6EF5)
                }
                defaultColor to AvatarType.Text(letter)
            }
        }

        val formattedAmount = if (isCredit) {
            "+ ₹" + String.format(Locale.US, "%,.2f", amount)
        } else {
            "₹" + String.format(Locale.US, "%,.2f", amount)
        }

        return TransactionItem(
            title = title,
            date = date,
            amount = formattedAmount,
            isCredit = isCredit,
            avatarBg = bg,
            avatarType = avatar
        )
    }
}

class PocketlyDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "pocketly.db"
        const val DATABASE_VERSION = 2

        const val TABLE_TRANSACTIONS = "transactions"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_IS_CREDIT = "is_credit"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_DATE = "date"
        const val COLUMN_MONTH = "month"
        const val COLUMN_IS_RECURRING = "is_recurring"
        const val COLUMN_ACCOUNT_NAME = "account_name"
        const val COLUMN_PAYMENT_MODE = "payment_mode"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_TRANSACTIONS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_AMOUNT REAL NOT NULL,
                $COLUMN_IS_CREDIT INTEGER NOT NULL,
                $COLUMN_CATEGORY TEXT NOT NULL,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_MONTH TEXT NOT NULL,
                $COLUMN_IS_RECURRING INTEGER NOT NULL DEFAULT 0,
                $COLUMN_ACCOUNT_NAME TEXT NOT NULL DEFAULT 'Main Account',
                $COLUMN_PAYMENT_MODE TEXT NOT NULL DEFAULT 'UPI'
            );
        """.trimIndent()
        db.execSQL(createTableQuery)

        seedInitialTransactions(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        onCreate(db)
    }

    private fun seedInitialTransactions(db: SQLiteDatabase) {
        // Baseline September 2026:
        // Food: ₹3,500 (Swiggy ₹480, Zomato ₹1,520, Dining ₹1,500)
        // Mobile Bills: ₹1,000 (Airtel Postpaid ₹1,000)
        // Shopping: ₹900 (Myntra ₹900)
        // Transport: ₹600 (REDBUS ₹350, Uber ₹250)
        // Total September Spend = ₹6,000
        // Received: Salary ₹85,000 + Transfer ₹9,000 = ₹94,000
        // Initial Available Balance = ₹94,000 - ₹6,000 = ₹88,000.
        val initialData = listOf(
            // September 2026 Transactions
            TransactionRecord(title = "Swiggy", amount = 480.00, isCredit = false, category = "Food", date = "3 September", month = "September 2026", paymentMode = "UPI"),
            TransactionRecord(title = "Zomato", amount = 1520.00, isCredit = false, category = "Food", date = "3 September", month = "September 2026", paymentMode = "UPI"),
            TransactionRecord(title = "Dining & Cafe", amount = 1500.00, isCredit = false, category = "Food", date = "2 September", month = "September 2026", paymentMode = "Cash"),
            TransactionRecord(title = "Airtel Postpaid", amount = 1000.00, isCredit = false, category = "Mobile Bills", date = "2 September", month = "September 2026", paymentMode = "UPI"),
            TransactionRecord(title = "Myntra", amount = 900.00, isCredit = false, category = "Shopping", date = "2 September", month = "September 2026", paymentMode = "UPI"),
            TransactionRecord(title = "REDBUS", amount = 350.00, isCredit = false, category = "Transport", date = "1 September", month = "September 2026", paymentMode = "UPI"),
            TransactionRecord(title = "Uber", amount = 250.00, isCredit = false, category = "Transport", date = "1 September", month = "September 2026", paymentMode = "UPI"),
            TransactionRecord(title = "Salary Credit", amount = 85000.00, isCredit = true, category = "Income", date = "1 September", month = "September 2026", paymentMode = "Cheque"),
            TransactionRecord(title = "Bank Transfer", amount = 9000.00, isCredit = true, category = "Income", date = "1 September", month = "September 2026", paymentMode = "UPI"),

            // August 2026 Transactions (Total Spent: ₹8,450)
            TransactionRecord(title = "Fine Dining", amount = 4200.00, isCredit = false, category = "Food", date = "28 August", month = "August 2026", paymentMode = "UPI"),
            TransactionRecord(title = "Zara Shopping", amount = 2100.00, isCredit = false, category = "Shopping", date = "22 August", month = "August 2026", paymentMode = "UPI"),
            TransactionRecord(title = "Broadband Bill", amount = 1200.00, isCredit = false, category = "Mobile Bills", date = "15 August", month = "August 2026", paymentMode = "UPI"),
            TransactionRecord(title = "City Cabs", amount = 950.00, isCredit = false, category = "Transport", date = "10 August", month = "August 2026", paymentMode = "Cash"),
            TransactionRecord(title = "Salary Credit", amount = 75000.00, isCredit = true, category = "Income", date = "1 August", month = "August 2026", paymentMode = "Cheque"),

            // Previous Months for Analytics Bar Chart
            TransactionRecord(title = "July Total Expenses", amount = 5800.00, isCredit = false, category = "Other", date = "25 July", month = "July 2026", paymentMode = "UPI"),
            TransactionRecord(title = "June Total Expenses", amount = 7200.00, isCredit = false, category = "Other", date = "25 June", month = "June 2026", paymentMode = "UPI"),
            TransactionRecord(title = "May Total Expenses", amount = 5200.00, isCredit = false, category = "Other", date = "25 May", month = "May 2026", paymentMode = "UPI")
        )

        for (item in initialData) {
            val values = ContentValues().apply {
                put(COLUMN_TITLE, item.title)
                put(COLUMN_AMOUNT, item.amount)
                put(COLUMN_IS_CREDIT, if (item.isCredit) 1 else 0)
                put(COLUMN_CATEGORY, item.category)
                put(COLUMN_DATE, item.date)
                put(COLUMN_MONTH, item.month)
                put(COLUMN_IS_RECURRING, if (item.isRecurring) 1 else 0)
                put(COLUMN_ACCOUNT_NAME, item.accountName)
                put(COLUMN_PAYMENT_MODE, item.paymentMode)
            }
            db.insert(TABLE_TRANSACTIONS, null, values)
        }
    }
}
