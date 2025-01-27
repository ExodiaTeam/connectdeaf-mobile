package com.connectdeaf.network.services

import com.connectdeaf.domain.model.Service
import com.connectdeaf.network.dtos.ServiceRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceService {
    @GET("api/services/professional/{professional_id}/services")
    suspend fun getServicesByProfessional(@Path("professional_id") id: String): List<Service>

    @POST("api/services")
    suspend fun createService(
        @Query("professionalId") professionalId: String,
        @Body serviceRequest: ServiceRequest
    ): Service

    @DELETE("api/services/{service_id}")
    suspend fun deleteService(@Path("service_id") serviced: String): Response<Unit>

    @GET("api/services")
    suspend fun getServices(): List<Service>

}