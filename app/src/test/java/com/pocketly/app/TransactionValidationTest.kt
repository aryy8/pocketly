package com.pocketly.app

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Locale

class TransactionValidationTest {

    private fun validateTransactionInput(title: String, amountText: String): Pair<Boolean, String?> {
        if (title.isBlank()) {
            return false to "Title cannot be empty"
        }
        val amount = amountText.toDoubleOrNull()
        if (amount == null) {
            return false to "Amount must be a valid number"
        }
        if (amount <= 0.0) {
            return false to "Amount must be greater than zero"
        }
        return true to null
    }

    @Test
    fun `empty title should fail validation`() {
        val (isValid, error) = validateTransactionInput("", "500")
        assertFalse(isValid)
        assertEquals("Title cannot be empty", error)
    }

    @Test
    fun `whitespace title should fail validation`() {
        val (isValid, error) = validateTransactionInput("   ", "500")
        assertFalse(isValid)
        assertEquals("Title cannot be empty", error)
    }

    @Test
    fun `non-numeric amount should fail validation`() {
        val (isValid, error) = validateTransactionInput("Coffee", "abc")
        assertFalse(isValid)
        assertEquals("Amount must be a valid number", error)
    }

    @Test
    fun `negative or zero amount should fail validation`() {
        val (isValidZero, errorZero) = validateTransactionInput("Coffee", "0")
        assertFalse(isValidZero)
        assertEquals("Amount must be greater than zero", errorZero)

        val (isValidNeg, errorNeg) = validateTransactionInput("Coffee", "-150")
        assertFalse(isValidNeg)
        assertEquals("Amount must be greater than zero", errorNeg)
    }

    @Test
    fun `valid title and positive amount should succeed`() {
        val (isValid, error) = validateTransactionInput("Groceries", "1250.50")
        assertTrue(isValid)
        assertEquals(null, error)
    }

    @Test
    fun `currency formatting should format commas and decimals properly`() {
        val amount = 88000.0
        val formatted = String.format(Locale.US, "%,.2f", amount)
        assertEquals("88,000.00", formatted)
    }
}
