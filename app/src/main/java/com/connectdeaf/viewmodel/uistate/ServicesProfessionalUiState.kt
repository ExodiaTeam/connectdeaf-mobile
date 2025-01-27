package com.connectdeaf.viewmodel.uistate

import android.net.Uri
import com.connectdeaf.domain.model.Service

data class ServicesProfessionalUiState(
    val nameService: String = "",
    val description: String = "",
    val price: Double = 0.0,  // Define 0.0 como valor padrão
    val categories: String = "",
    val imageUri: Uri? = null,
    val services: List<Service> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isFormValid: Boolean
        get() = nameService.isNotBlank() &&
                description.isNotBlank() &&
                price > 0 &&  // Agora price nunca será null
                categories.isNotBlank()
}

