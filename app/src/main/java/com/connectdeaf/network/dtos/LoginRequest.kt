package com.connectdeaf.network.dtos

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val accessToken: String, val expiresIn: Int)