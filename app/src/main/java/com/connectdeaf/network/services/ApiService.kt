package com.connectdeaf.network.services

import com.connectdeaf.network.dtos.LoginRequest
import com.connectdeaf.network.dtos.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
