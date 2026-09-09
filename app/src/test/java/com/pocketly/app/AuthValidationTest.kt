package com.pocketly.app

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthValidationTest {

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    private fun validateEmail(email: String): Pair<Boolean, String?> {
        val trimmed = email.trim()
        if (trimmed.isEmpty()) {
            return false to "Email address cannot be empty"
        }
        if (!trimmed.matches(emailRegex)) {
            return false to "Enter a valid email address (e.g. user@domain.com)"
        }
        return true to null
    }

    private fun validatePassword(password: String): Pair<Boolean, String?> {
        if (password.isEmpty()) {
            return false to "Password cannot be empty"
        }
        if (password.length < 6) {
            return false to "Password must be at least 6 characters"
        }
        return true to null
    }

    private fun validateName(name: String, isSignUp: Boolean): Pair<Boolean, String?> {
        if (isSignUp && name.trim().isEmpty()) {
            return false to "Please enter your full name"
        }
        return true to null
    }

    @Test
    fun `empty email fails validation`() {
        val (isValid, error) = validateEmail("")
        assertFalse(isValid)
        assertEquals("Email address cannot be empty", error)
    }

    @Test
    fun `invalid email format fails validation`() {
        val invalidEmails = listOf("aryan", "aryan@", "aryan@pocketly", "aryan.com", "@pocketly.app")
        for (email in invalidEmails) {
            val (isValid, error) = validateEmail(email)
            assertFalse("Expected '$email' to be invalid", isValid)
            assertEquals("Enter a valid email address (e.g. user@domain.com)", error)
        }
    }

    @Test
    fun `valid email succeeds`() {
        val validEmails = listOf("aryan@pocketly.app", "user.name+tag@example.co.in", "student2427@manipal.edu")
        for (email in validEmails) {
            val (isValid, error) = validateEmail(email)
            assertTrue("Expected '$email' to be valid", isValid)
            assertNull(error)
        }
    }

    @Test
    fun `empty password fails validation`() {
        val (isValid, error) = validatePassword("")
        assertFalse(isValid)
        assertEquals("Password cannot be empty", error)
    }

    @Test
    fun `short password under 6 chars fails validation`() {
        val (isValid, error) = validatePassword("12345")
        assertFalse(isValid)
        assertEquals("Password must be at least 6 characters", error)
    }

    @Test
    fun `valid password 6 or more chars succeeds`() {
        val (isValid, error) = validatePassword("password123")
        assertTrue(isValid)
        assertNull(error)
    }

    @Test
    fun `sign up with empty name fails validation`() {
        val (isValid, error) = validateName("   ", isSignUp = true)
        assertFalse(isValid)
        assertEquals("Please enter your full name", error)
    }

    @Test
    fun `sign in without name succeeds`() {
        val (isValid, error) = validateName("", isSignUp = false)
        assertTrue(isValid)
        assertNull(error)
    }
}
