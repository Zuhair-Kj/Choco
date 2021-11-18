package com.choco.account.viewmodel

import androidx.lifecycle.ViewModel
import com.choco.core.auth.UserSessionManager

class ProfileViewModel(private val userSessionManager: UserSessionManager): ViewModel() {
    fun logout() {
//        userSessionManager.clearSession()
    }
}