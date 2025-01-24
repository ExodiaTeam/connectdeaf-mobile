package com.connectdeaf.viewmodel.uistate

import com.connectdeaf.domain.model.Service

data class ServicesProfessionalUiState(
    val services: List<Service> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
