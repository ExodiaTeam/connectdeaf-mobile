package com.connectdeaf.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val rememberMe: Boolean = false,
    val isSubmitting: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val errorMessage: String? = null
)

class SignInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, emailError = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, passwordError = null)
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
    }

    fun onRememberMeChange(checked: Boolean) {
        _uiState.value = _uiState.value.copy(rememberMe = checked)
    }

    fun onSignIn() {
        val currentState = _uiState.value
        var hasError = false

        // Validate inputs
        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(emailError = "Insira um email válido!")
            hasError = true
        }
        if (currentState.password.length < 8) {
            _uiState.value = currentState.copy(passwordError = "A senha deve ter no mínimo 8 caracteres!")
            hasError = true
        }

        if (hasError) return

        // Perform login
        viewModelScope.launch {
            _uiState.value = currentState.copy(isSubmitting = true)
            try {
                val success = true
                if (success) {
                    // Navigate to the appropriate screen
                } else {
                    _uiState.value = _uiState.value.copy(errorMessage = "Erro ao fazer login")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } finally {
                _uiState.value = _uiState.value.copy(isSubmitting = false)
            }
        }
    }
}
