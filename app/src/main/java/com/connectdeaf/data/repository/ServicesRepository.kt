package com.connectdeaf.data.repository

import android.content.Context
import android.util.Log
import com.connectdeaf.domain.model.Service
import com.connectdeaf.network.retrofit.ApiServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ServicesRepository {
    suspend fun getServicesByProfessional(professionalId: String, context: Context): Result<List<Service>> {

        val apiServiceFactory = ApiServiceFactory(context)
        val serviceService = apiServiceFactory.serviceService

        return try {
            val services = withContext(Dispatchers.IO) {
                serviceService.getServicesByProfessional(professionalId)
            }

            if (services.isNotEmpty()) {
                Result.success(services)
            } else {
                Result.failure(Exception("Falha ao buscar serviços!"))
            }
        } catch (e: Exception) {
            Log.e("ServiceRepository", "Erro ao buscar serviços: ${e.message}")
            Result.failure(e)
        }
    }
}