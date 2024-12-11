package com.connectdeaf.viewmodel.uistate

open class BaseRegisterUiState(
    open val name: String = "",
    open val email: String = "",
    open val phone: String = "",
    open val password: String = "",
    open val isEmailValid: Boolean = true,
    open val isPhoneValid: Boolean = true
) {
    private val isInputValid: Boolean
        get() = name.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && password.isNotBlank()

    val isFormValid: Boolean
        get() = isEmailValid && isPhoneValid && isInputValid
}