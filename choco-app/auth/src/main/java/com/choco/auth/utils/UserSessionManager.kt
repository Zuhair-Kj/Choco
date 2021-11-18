package com.choco.auth.utils

import com.choco.core.auth.UserSessionManager
import com.choco.core.util.PreferenceHelper

class UserSessionManagerImpl(private val preferenceHelper: PreferenceHelper): UserSessionManager {
    override fun isUserLoggedIn(): Boolean = !preferenceHelper.getToken().isNullOrBlank()

    override fun storeToken(token: String) {
        preferenceHelper.setToken(token)
    }

    override fun getToken(): String? = preferenceHelper.getToken()
    override fun clearSession() {
        preferenceHelper.clearToken()
    }
}