package com.connectdeaf.data.repository

import android.content.Context
import android.util.Log
import com.connectdeaf.domain.model.User
import com.connectdeaf.network.dtos.UserRequest
import com.connectdeaf.network.retrofit.ApiServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    // Função que cria o usuário
    suspend fun createUser(userRequest: UserRequest, context: Context): Result<User> {

        val apiServiceFactory = ApiServiceFactory(context)
        val userService = apiServiceFactory.userService

        return try {
            val createdUser = withContext(Dispatchers.IO) {
                userService.createUser(userRequest)
            }

            if (createdUser.id != null) {
                Result.success(createdUser)
            } else {
                Result.failure(Exception("Falha ao criar usuário!"))
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Erro ao criar usuário: ${e.message}")
            Result.failure(e)
        }
    }
}
