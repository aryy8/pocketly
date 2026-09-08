package com.pocketly.app

import org.junit.Assert.assertEquals
import org.junit.Test

data class SimpleTransaction(
    val title: String,
    val amount: Double,
    val isCredit: Boolean,
    val category: String
)

class FinancialCalculationTest {

    private fun calculateFinancials(transactions: List<SimpleTransaction>): Triple<Double, Double, Double> {
        var spent = 0.0
        var received = 0.0

        for (tx in transactions) {
            if (tx.isCredit) {
                received += tx.amount
            } else {
                spent += tx.amount
            }
        }
        val balance = (received - spent).coerceAtLeast(0.0)
        return Triple(balance, spent, received)
    }

    private fun calculateCategoryPercentages(transactions: List<SimpleTransaction>): Map<String, Int> {
        val expenses = transactions.filter { !it.isCredit }
        val totalExpense = expenses.sumOf { it.amount }
        if (totalExpense == 0.0) return emptyMap()

        return expenses
            .groupBy { it.category }
            .mapValues { (_, items) ->
                val categorySum = items.sumOf { it.amount }
                Math.round((categorySum / totalExpense) * 100.0).toInt()
            }
    }

    @Test
    fun `test total balance, spent, and received calculations`() {
        val transactions = listOf(
            SimpleTransaction("Salary", 50000.0, true, "Income"),
            SimpleTransaction("Freelance", 10000.0, true, "Income"),
            SimpleTransaction("Rent", 15000.0, false, "Bills"),
            SimpleTransaction("Groceries", 5000.0, false, "Food")
        )

        val (balance, spent, received) = calculateFinancials(transactions)

        assertEquals(60000.0, received, 0.001)
        assertEquals(20000.0, spent, 0.001)
        assertEquals(40000.0, balance, 0.001)
    }

    @Test
    fun `test category percentage aggregation`() {
        val transactions = listOf(
            SimpleTransaction("Dinner", 400.0, false, "Food"),
            SimpleTransaction("Lunch", 600.0, false, "Food"), // Total Food = 1000 (50%)
            SimpleTransaction("Uber", 600.0, false, "Transport"), // 30%
            SimpleTransaction("Movie", 400.0, false, "Entertainment") // 20%
        )

        val percentages = calculateCategoryPercentages(transactions)

        assertEquals(50, percentages["Food"])
        assertEquals(30, percentages["Transport"])
        assertEquals(20, percentages["Entertainment"])
    }

    @Test
    fun `test budget remaining calculation`() {
        val monthlyBudget = 25000.0
        val spent = 18000.0
        val remaining = monthlyBudget - spent
        val percentageUsed = (spent / monthlyBudget) * 100.0

        assertEquals(7000.0, remaining, 0.001)
        assertEquals(72.0, percentageUsed, 0.001)
    }
}
