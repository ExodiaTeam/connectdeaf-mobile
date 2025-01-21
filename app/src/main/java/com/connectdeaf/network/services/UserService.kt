package com.connectdeaf.network.services

import com.connectdeaf.domain.model.User
import com.connectdeaf.network.dtos.UserRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/users")
    suspend fun createUser(@Body user: UserRequest): User
}