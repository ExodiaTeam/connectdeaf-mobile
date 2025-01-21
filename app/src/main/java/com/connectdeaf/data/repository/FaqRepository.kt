package com.connectdeaf.data.repository

import com.connectdeaf.domain.model.FaqRequest
import com.connectdeaf.domain.model.FaqResponse
import com.connectdeaf.network.services.FAQApiService

class FaqRepository(private val faqApiService: FAQApiService) {
    suspend fun sendMessage(request: FaqRequest): FaqResponse {
        return faqApiService.sendFaqMessage(request)
    }
}
