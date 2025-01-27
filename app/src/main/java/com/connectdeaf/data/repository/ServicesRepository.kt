package com.connectdeaf.data.repository

import android.content.Context
import android.util.Log
import com.connectdeaf.domain.model.Service
import com.connectdeaf.network.dtos.ServiceRequest
import com.connectdeaf.network.retrofit.ApiServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ServicesRepository(context: Context) {
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

    suspend fun createService(professionalId: String, serviceRequest: ServiceRequest, context: Context): Result<Service> {
        val apiServiceFactory = ApiServiceFactory(context)
        val serviceService = apiServiceFactory.serviceService

        return try {
            val createdService = withContext(Dispatchers.IO) {
                serviceService.createService(professionalId, serviceRequest) // Passando como Query Param
            }
            Result.success(createdService)
        } catch (e: Exception) {
            Log.e("ServiceRepository", "Erro ao criar serviço: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun deleteService(serviceId: String, context: Context): Result<Unit> {
        val apiServiceFactory = ApiServiceFactory(context)
        val serviceService = apiServiceFactory.serviceService

        return try {
            Log.d("ServiceRepository", "Tentando deletar serviço com ID: $serviceId")

            withContext(Dispatchers.IO) {
                serviceService.deleteService(serviceId)
            }

            Log.d("ServiceRepository", "Serviço deletado com sucesso: $serviceId")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("ServiceRepository", "Erro ao deletar serviço (ID: $serviceId): ${e.message}", e)
            Result.failure(e)
        }
    }
}