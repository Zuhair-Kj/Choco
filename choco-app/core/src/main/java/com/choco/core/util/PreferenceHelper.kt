package com.choco.core.util

import android.content.SharedPreferences

class PreferenceHelper(private val sharedPreferences: SharedPreferences) {
    fun setToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun clearToken() {
        setToken("")
    }

    fun getToken(): String? = sharedPreferences.getString(KEY_TOKEN, null)

    companion object {
        const val KEY_TOKEN = "token"
    }
}