package com.connectdeaf.viewmodel.uistate

data class RegisterProfessionalUiState(
    override val name: String = "",
    override val email: String = "",
    override val phone: String = "",
    override val password: String = "",
    override val isEmailValid: Boolean = true,
    override val isPhoneValid: Boolean = true,

    val areaDeAtuacao: String = "",
    val selectedQualifications: String ="",
    val qualifications: List<String> = emptyList()
): BaseRegisterUiState(name, email, phone, password, isEmailValid, isPhoneValid)