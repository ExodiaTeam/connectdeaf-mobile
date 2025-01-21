package com.connectdeaf.data.repository

import android.content.Context
import android.util.Log
import com.auth0.android.jwt.JWT
import com.connectdeaf.network.dtos.LoginRequest
import com.connectdeaf.network.dtos.LoginResponse
import com.connectdeaf.network.retrofit.ApiServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    suspend fun login(email: String, password: String, context: Context): Result<LoginResponse> {
        return try {
            val apiServiceFactory = ApiServiceFactory(context)
            val api = apiServiceFactory.apiService

            val response = withContext(Dispatchers.IO) {
                api.login(LoginRequest(email, password))
            }

            val jwt = JWT(response.accessToken)
            val userId = jwt.getClaim("sub").asString()
            val email = jwt.getClaim("email").asString()
            val roles = jwt.getClaim("roles").asList(String::class.java)
            val professionalId = jwt.getClaim("professionalId").asString()

            if (userId != null) {
                saveAuthToken(response.accessToken, userId, email, roles, professionalId)
                Result.success(response)
            } else {
                Result.failure(Exception("ID do usuário não encontrado no token"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erro ao fazer login: ${e.message}")
            Result.failure(e)
        }
    }

    fun saveAuthToken(
        token: String,
        userId: String,
        email: String?,
        roles: List<String>?,
        professionalId: String?
    ) {
        sharedPreferences.edit().apply {
            putString("token", token)
            putString("userId", userId)
            putString("email", email)
            putString("roles", roles?.joinToString(","))
            if (professionalId != null) {
                putString("professionalId", professionalId)
            }
            apply()
        }
        Log.d(
            "AuthRepository",
            "Dados salvos: token=$token, userId=$userId, email=$email, roles=$roles, professionalId=$professionalId"
        )
    }

    fun getAuthToken(): String? = sharedPreferences.getString("token", null)

    fun getUserId(): String? = sharedPreferences.getString("userId", null)

    fun getEmail(): String? = sharedPreferences.getString("email", null)

    fun getRoles(): List<String>? = sharedPreferences.getString("roles", null)?.split(",")

    fun getProfessionalId(): String? = sharedPreferences.getString("professionalId", null)

    fun logout() {
        sharedPreferences.edit().clear().apply()
    }
}
