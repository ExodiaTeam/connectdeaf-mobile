package com.connectdeaf.viewmodel.uistate

data class RegisterUiState(
    override val name: String = "",
    override val email: String = "",
    override val phone: String = "",
    override val password: String = "",
    override val isEmailValid: Boolean = true,
    override val isPhoneValid: Boolean = true,
) : BaseRegisterUiState(name, email, phone, password, isEmailValid, isPhoneValid)
