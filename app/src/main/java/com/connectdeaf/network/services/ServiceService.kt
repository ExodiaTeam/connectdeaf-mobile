package com.connectdeaf.network.services

import com.connectdeaf.domain.model.Service
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceService {
    @GET("api/services/professional/{professional_id}/services")
    suspend fun getServicesByProfessional(@Path("professional_id") id: String): List<Service>

    @GET("api/services")
    suspend fun getServices(): List<Service>

}