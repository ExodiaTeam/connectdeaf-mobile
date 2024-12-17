package com.connectdeaf.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.util.Log
import com.connectdeaf.utils.Validator
import com.connectdeaf.viewmodel.uistate.RegisterProfessionalUiState

class RegisterProfessionalViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        RegisterProfessionalUiState(
            qualifications = listOf(
                "Ensino Médio",
                "Graduação",
                "Pós-Graduação",
                "Mestrado",
                "Doutorado"
            )
        )
    )
    val uiState: StateFlow<RegisterProfessionalUiState> = _uiState.asStateFlow()

    fun onNameChange(newName: String) {
        Log.d(
            "com.connectdeaf.viewmodel.RegisterProfessionalViewModel",
            "onNameChange called with newName: $newName"
        )
        _uiState.update { it.copy(name = newName) }
    }

    fun onEmailChange(newEmail: String) {
        Log.d(
            "com.connectdeaf.viewmodel.RegisterProfessionalViewModel",
            "onEmailChange called with newEmail: $newEmail"
        )
        _uiState.update {
            it.copy(
                email = newEmail,
                isEmailValid = Validator.isValidEmail(newEmail)
            )
        }
    }

    fun onPhoneChange(newPhone: String) {
        Log.d(
            "com.connectdeaf.viewmodel.RegisterProfessionalViewModel",
            "onPhoneChange called with newPhone: $newPhone"
        )
        _uiState.update {
            it.copy(
                phone = newPhone,
                isPhoneValid = Validator.isValidPhone(newPhone)
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        Log.d(
            "com.connectdeaf.viewmodel.RegisterProfessionalViewModel",
            "onPasswordChange called with newPassword: $newPassword"
        )
        _uiState.update { it.copy(password = newPassword) }
    }

    fun onAreaDeAtuacaoChange(newAreaDeAtuacao: String) {
        Log.d(
            "com.connectdeaf.viewmodel.RegisterProfessionalViewModel",
            "onAreaDeAtuacaoChange called with newAreaDeAtuacao: $newAreaDeAtuacao"
        )
        _uiState.update { it.copy(areaDeAtuacao = newAreaDeAtuacao) }
    }

    fun onQualificationChange(newQualification: String) {
        Log.d(
            "com.connectdeaf.viewmodel.RegisterProfessionalViewModel",
            "onQualificationChange called with newQualification: $newQualification"
        )
        _uiState.update { it.copy(selectedQualifications = newQualification) }
    }
}