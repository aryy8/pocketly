package com.pocketly.app.data

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "pocketly_user_preferences"
        private const val KEY_USER_NAME = "key_user_name"
        private const val KEY_MONTHLY_BUDGET = "key_monthly_budget"
        private const val KEY_BIOMETRIC_LOCK = "key_biometric_lock"
    }

    var userName: String
        get() = prefs.getString(KEY_USER_NAME, "Aryan") ?: "Aryan"
        set(value) = prefs.edit().putString(KEY_USER_NAME, value).apply()

    var monthlyBudget: Float
        get() = prefs.getFloat(KEY_MONTHLY_BUDGET, 15000f)
        set(value) = prefs.edit().putFloat(KEY_MONTHLY_BUDGET, value).apply()

    var biometricLockEnabled: Boolean
        get() = prefs.getBoolean(KEY_BIOMETRIC_LOCK, false)
        set(value) = prefs.edit().putBoolean(KEY_BIOMETRIC_LOCK, value).apply()
}
