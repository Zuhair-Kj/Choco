package com.choco.auth.network

import com.choco.auth.model.LoginRequestParams
import com.choco.auth.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("choco/login")
    suspend fun login(@Body params: LoginRequestParams): LoginResponse
}