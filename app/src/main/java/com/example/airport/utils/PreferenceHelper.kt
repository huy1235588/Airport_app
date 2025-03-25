package com.example.airport.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceHelper(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveEmail(email: String) {
        prefs.edit() { putString("email", email) }
    }

    fun getEmail(): String? {
        return prefs.getString("email", null)
    }

    fun savePassword(password: String) {
        prefs.edit() { putString("password", password) }
    }

    fun getPassword(): String? {
        return prefs.getString("password", null)
    }

    fun clearCredentials() {
        prefs.edit() { remove("email").remove("password") }
    }
}