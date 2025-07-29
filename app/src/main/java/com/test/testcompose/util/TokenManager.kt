package com.test.testcompose.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TokenManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(jwt: String, refresh: String) {
        prefs.edit { putString("jwt", jwt).putString("refresh", refresh) }
    }

    fun getJwt(): String? = prefs.getString("jwt", null)
    fun getRefresh(): String? = prefs.getString("refresh", null)
    fun clear() {
        prefs.edit { putString("jwt", null).putString("refresh", null) }
    }
}