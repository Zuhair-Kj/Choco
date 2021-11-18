package com.choco.core.auth

interface UserSessionManager {
    fun isUserLoggedIn(): Boolean
    fun storeToken(token: String)
    fun getToken(): String?
    fun clearSession()
}