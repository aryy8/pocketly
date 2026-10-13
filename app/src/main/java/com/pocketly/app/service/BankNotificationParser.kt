package com.pocketly.app.service

import java.util.Locale
import java.util.regex.Pattern

data class ParsedTransaction(
    val title: String,
    val amount: Double,
    val isCredit: Boolean,
    val category: String,
    val accountName: String = "Main Account",
    val paymentMode: String = "UPI"
)

object BankNotificationParser {

    private val AMOUNT_PATTERN = Pattern.compile(
        """(?:rs\.?|inr|₹)\s*([0-9]+(?:,[0-9]+)*(?:\.[0-9]{1,2})?)""",
        Pattern.CASE_INSENSITIVE
    )

    private val CREDIT_KEYWORDS = listOf(
        "credited", "received", "deposited", "refund", "added"
    )

    private val DEBIT_KEYWORDS = listOf(
        "debited", "spent", "paid", "sent", "transferred", "withdrawn", "deducted"
    )

    /**
     * Parse text from incoming bank/UPI notification. Returns null if not a transaction.
     */
    fun parse(title: String?, text: String?): ParsedTransaction? {
        val fullContent = "${title.orEmpty()} ${text.orEmpty()}".trim()
        if (fullContent.isBlank()) return null

        val lower = fullContent.lowercase(Locale.ROOT)

        // 1. Detect if this message represents a financial transaction
        val isCredit = CREDIT_KEYWORDS.any { lower.contains(it) }
        val isDebit = DEBIT_KEYWORDS.any { lower.contains(it) }

        if (!isCredit && !isDebit) {
            return null
        }

        // 2. Extract transaction amount
        val matcher = AMOUNT_PATTERN.matcher(fullContent)
        val amount = if (matcher.find()) {
            val amountStr = matcher.group(1)?.replace(",", "").orEmpty()
            amountStr.toDoubleOrNull() ?: return null
        } else {
            return null
        }

        if (amount <= 0.0) return null

        // 3. Extract Merchant / Beneficiary Title
        val extractedMerchant = extractMerchant(lower, fullContent)

        // 4. Categorize intelligently
        val category = if (isCredit) {
            "Income"
        } else {
            categorizeMerchant(extractedMerchant.lowercase(Locale.ROOT), lower)
        }

        // 5. Detect Account Name
        val accountName = when {
            lower.contains("hdfc") -> "HDFC Bank"
            lower.contains("sbi") -> "State Bank of India"
            lower.contains("icici") -> "ICICI Bank"
            lower.contains("axis") -> "Axis Bank"
            else -> "Primary Account"
        }

        return ParsedTransaction(
            title = extractedMerchant,
            amount = amount,
            isCredit = isCredit,
            category = category,
            accountName = accountName,
            paymentMode = if (lower.contains("upi") || lower.contains("gpay") || lower.contains("phonepe") || lower.contains("paytm")) "UPI" else "Card"
        )
    }

    private fun extractMerchant(lower: String, original: String): String {
        // Direct known brand recognition
        val knownBrands = listOf(
            "Swiggy", "Zomato", "Starbucks", "McDonald's", "KFC", "Domino's",
            "Uber", "Ola", "Rapido", "Redbus", "Metro", "IRCTC",
            "Airtel Postpaid", "Airtel", "Jio", "Vi", "BESCOM", "Electricity Bill",
            "Myntra", "Amazon", "Flipkart", "Zara", "Nykaa", "Blinkit", "Zepto", "Instamart"
        )

        for (brand in knownBrands) {
            if (lower.contains(brand.lowercase(Locale.ROOT))) {
                return brand
            }
        }

        // Regex patterns matching "to <Merchant>" or "at <Merchant>" or "for <Merchant>"
        val merchantPattern = Pattern.compile(
            """(?:to|at|for|vpa)\s+([A-Za-z0-9\s&.-]{2,20})(?:\s+on|\.|\s+ref|\s+upi|\s+avail|\s+bal|$)""",
            Pattern.CASE_INSENSITIVE
        )
        val m = merchantPattern.matcher(original)
        if (m.find()) {
            val candidate = m.group(1)?.trim()
            if (!candidate.isNullOrBlank() && candidate.length > 2) {
                return candidate.replaceFirstChar { it.uppercase() }
            }
        }

        return if (lower.contains("credited")) "Payment Received" else "Card Payment"
    }

    private fun categorizeMerchant(merchantLower: String, fullLower: String): String {
        return when {
            merchantLower.contains("swiggy") || merchantLower.contains("zomato") ||
            merchantLower.contains("starbucks") || merchantLower.contains("cafe") ||
            merchantLower.contains("dining") || merchantLower.contains("food") ||
            merchantLower.contains("kfc") || merchantLower.contains("restaurant") -> "Food"

            merchantLower.contains("uber") || merchantLower.contains("ola") ||
            merchantLower.contains("rapido") || merchantLower.contains("redbus") ||
            merchantLower.contains("metro") || merchantLower.contains("transport") ||
            merchantLower.contains("cab") -> "Transport"

            merchantLower.contains("airtel") || merchantLower.contains("jio") ||
            merchantLower.contains("vi") || merchantLower.contains("bill") ||
            merchantLower.contains("electricity") || merchantLower.contains("broadband") ||
            merchantLower.contains("recharge") -> "Mobile Bills"

            merchantLower.contains("myntra") || merchantLower.contains("amazon") ||
            merchantLower.contains("flipkart") || merchantLower.contains("zara") ||
            merchantLower.contains("nykaa") || merchantLower.contains("shopping") ||
            merchantLower.contains("blinkit") || merchantLower.contains("zepto") -> "Shopping"

            else -> "Shopping"
        }
    }
}
