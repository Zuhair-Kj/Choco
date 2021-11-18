package com.choco.auth.interactors

import com.choco.auth.model.LoginRequestParams
import com.choco.auth.repo.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend fun login(userName: String, password: String): String? {
        return repository.login(LoginRequestParams(userName, password)).token
    }
}