package com.pocketly.app.data

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "pocketly_user_preferences"
        private const val KEY_USER_NAME = "key_user_name"
        private const val KEY_USER_EMAIL = "key_user_email"
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
        private const val KEY_REMEMBER_ME = "key_remember_me"
        private const val KEY_ACCOUNT_TYPE = "key_account_type"
        private const val KEY_MONTHLY_BUDGET = "key_monthly_budget"
        private const val KEY_BIOMETRIC_LOCK = "key_biometric_lock"
    }

    var userName: String
        get() = prefs.getString(KEY_USER_NAME, "Aryan Rastogi") ?: "Aryan Rastogi"
        set(value) = prefs.edit().putString(KEY_USER_NAME, value).apply()

    var userEmail: String
        get() = prefs.getString(KEY_USER_EMAIL, "aryanfeb17@gmail.com") ?: "aryanfeb17@gmail.com"
        set(value) = prefs.edit().putString(KEY_USER_EMAIL, value).apply()

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var rememberMe: Boolean
        get() = prefs.getBoolean(KEY_REMEMBER_ME, true)
        set(value) = prefs.edit().putBoolean(KEY_REMEMBER_ME, value).apply()

    var accountType: String
        get() = prefs.getString(KEY_ACCOUNT_TYPE, "Personal") ?: "Personal"
        set(value) = prefs.edit().putString(KEY_ACCOUNT_TYPE, value).apply()

    var monthlyBudget: Float
        get() = prefs.getFloat(KEY_MONTHLY_BUDGET, 15000f)
        set(value) = prefs.edit().putFloat(KEY_MONTHLY_BUDGET, value).apply()

    var biometricLockEnabled: Boolean
        get() = prefs.getBoolean(KEY_BIOMETRIC_LOCK, false)
        set(value) = prefs.edit().putBoolean(KEY_BIOMETRIC_LOCK, value).apply()

    fun saveUserSession(name: String, email: String, type: String = "Personal", remember: Boolean = true) {
        prefs.edit()
            .putString(KEY_USER_NAME, name.ifBlank { "Aryan Rastogi" })
            .putString(KEY_USER_EMAIL, email.ifBlank { "aryanfeb17@gmail.com" })
            .putString(KEY_ACCOUNT_TYPE, type)
            .putBoolean(KEY_REMEMBER_ME, remember)
            .putBoolean(KEY_IS_LOGGED_IN, true)
            .apply()
    }

    fun logout() {
        prefs.edit()
            .putBoolean(KEY_IS_LOGGED_IN, false)
            .apply()
    }
}
