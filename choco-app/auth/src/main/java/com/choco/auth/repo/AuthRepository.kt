package com.choco.auth.repo

import com.choco.auth.model.LoginRequestParams
import com.choco.auth.network.AuthApiService

class AuthRepository(private val authApiService: AuthApiService) {
    suspend fun login(requestParams: LoginRequestParams) = authApiService.login(requestParams)
}