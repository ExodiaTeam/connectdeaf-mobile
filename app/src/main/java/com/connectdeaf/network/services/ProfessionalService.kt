package com.connectdeaf.network.services

import com.connectdeaf.viewmodel.Profile
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfessionalService {
    @GET("api/professionals/{id}")
    suspend fun getProfessional(@Path("id") id: String): Profile
}