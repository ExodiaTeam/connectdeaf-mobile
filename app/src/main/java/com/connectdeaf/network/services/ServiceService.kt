package com.connectdeaf.network.services

import com.connectdeaf.viewmodel.Service
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceService {
    @GET("api/services/professional/{professional_id}/services")
    suspend fun getServices(@Path("professional_id") id: String): List<Service>
}