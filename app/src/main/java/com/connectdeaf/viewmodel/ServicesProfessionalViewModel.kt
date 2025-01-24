package com.connectdeaf.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectdeaf.data.repository.ServicesRepository
import com.connectdeaf.viewmodel.uistate.ServicesProfessionalUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.connectdeaf.domain.model.Service
import kotlinx.coroutines.launch

class ServicesProfessionalViewModel(
    private val servicesRepository: ServicesRepository
): ViewModel() {
        private val _uiState = MutableStateFlow(ServicesProfessionalUiState())
                val uiState: StateFlow<ServicesProfessionalUiState> = _uiState.asStateFlow()
    fun onServicesChange(services: List<Service>) {
        _uiState.value = _uiState.value.copy(services = services)
    }
    fun getServicesByProfessional(professionalId: String, context: Context) {

        viewModelScope.launch {
            try {
                val result = servicesRepository.getServicesByProfessional(professionalId, context)
                result
                    .onSuccess { services -> onServicesChange(services) }
                    .onFailure { Log.e("ServicesProfessionalViewModel", "Erro ao buscar serviços: ${it.message}")
                }
            }
            catch (e: Exception) {
                Log.e("ServicesProfessionalViewModel", "Erro ao buscar serviços: ${e.message}")
            }

        }
    }
}