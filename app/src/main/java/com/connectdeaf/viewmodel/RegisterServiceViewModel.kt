package com.connectdeaf.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.connectdeaf.viewmodel.uistate.RegisterServicesUiState
import kotlinx.coroutines.flow.update

class RegisterServiceViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(RegisterServicesUiState())

    val uiState: StateFlow<RegisterServicesUiState> = _uiState.asStateFlow()

    fun onNameServiceChange(newNameService: String) {
        Log.d("RegisterServiceViewModel", "Nome do Serviço: $newNameService")
        _uiState.update { it.copy(nameService = newNameService) }
    }

    fun onDescriptionChange(newDescription: String) {
        Log.d("RegisterServiceViewModel", "Descrição: $newDescription")
        _uiState.update { it.copy(description = newDescription) }
    }

    fun onPriceChange(newPrice: String) {
        Log.d("RegisterServiceViewModel", "Preço: $newPrice")
        _uiState.update { it.copy(price = newPrice.toDoubleOrNull() ?: 0.0) }
    }

    fun onCategoryChange(newCategories: String) {
        Log.d("RegisterServiceViewModel", "Categorias: $newCategories")
        _uiState.update { it.copy(categories = newCategories) }
    }

    fun onImageSelected(uri: Uri) {
        Log.d("RegisterServiceViewModel", "Imagem Selecionada: $uri")
        _uiState.update { it.copy(imageUri = uri) }
    }


}
