package com.connectdeaf.viewmodel.uistate

import android.net.Uri

data class RegisterServicesUiState(
    val nameService: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val categories: String = "",
    val initialPrice: Double = 0.0,
    val imageUri: Uri? = null,

    val isNameValid: Boolean = true,
    val isDescriptionValid: Boolean = true,
    val isPriceValid: Boolean = true,
    val isCategoryValid: Boolean = true,
    val isImageValid: Boolean = true,


) {
    val isFormValid: Boolean
        get() = nameService.isNotBlank() && description.isNotBlank()

    val isCategoryFormValid: Boolean
        get() = categories.isNotBlank() && initialPrice > 0

    val isImageFormValid: Boolean
        get() = imageUri != null
}
