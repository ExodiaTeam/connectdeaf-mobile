package com.connectdeaf.network.services

import com.connectdeaf.domain.model.FaqRequest
import com.connectdeaf.domain.model.FaqResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface FAQApiService {
    @POST("api/faq/chat")
    suspend fun sendFaqMessage(@Body request: FaqRequest): FaqResponse
}
