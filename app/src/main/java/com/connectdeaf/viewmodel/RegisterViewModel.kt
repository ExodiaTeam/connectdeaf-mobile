package com.connectdeaf.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.util.Log
import com.connectdeaf.utils.Validator
import com.connectdeaf.viewmodel.uistate.RegisterUiState

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(newName: String) {
        Log.d("com.connectdeaf.viewmodel.RegisterViewModel", "onNameChange called with newName: $newName")
        _uiState.update { it.copy(name = newName) }
    }

    fun onEmailChange(newEmail: String) {
        Log.d("com.connectdeaf.viewmodel.RegisterViewModel", "onEmailChange called with newEmail: $newEmail")
        _uiState.update { it.copy(email = newEmail, isEmailValid = Validator.isValidEmail(newEmail)) }
    }

    fun onPhoneChange(newPhone: String) {
        Log.d("com.connectdeaf.viewmodel.RegisterViewModel", "onPhoneChange called with newPhone: $newPhone")
        _uiState.update { it.copy(phone = newPhone, isPhoneValid = Validator.isValidPhone(newPhone)) }
    }

    fun onPasswordChange(newPassword: String) {
        Log.d("com.connectdeaf.viewmodel.RegisterViewModel", "onPasswordChange called with newPassword: $newPassword")
        _uiState.update { it.copy(password = newPassword) }
    }
}
