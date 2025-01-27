package com.connectdeaf.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectdeaf.data.repository.ServicesRepository
import com.connectdeaf.domain.model.Service
import com.connectdeaf.network.dtos.ServiceRequest
import com.connectdeaf.viewmodel.uistate.ServicesProfessionalUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServiceProfessionalViewModel(
    private val servicesRepository: ServicesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServicesProfessionalUiState())
    val uiState: StateFlow<ServicesProfessionalUiState> = _uiState.asStateFlow()

    private fun onServicesChange(services: List<Service>) {
        _uiState.update {
            it.copy(
                services = services,
                isLoading = false
            )
        } // Atualiza isLoading para false
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) } // Atualiza o estado de carregamento
    }

    fun getServicesByProfessional(professionalId: String, context: Context) {
        setLoadingState(true) // Inicia o carregamento
        viewModelScope.launch {
            try {
                val result = servicesRepository.getServicesByProfessional(professionalId, context)
                result
                    .onSuccess { services -> onServicesChange(services) }
                    .onFailure {
                        Log.e(
                            "ServiceProfessionalViewModel",
                            "Erro ao buscar serviços: ${it.message}"
                        )
                        setLoadingState(false) // Finaliza o carregamento em caso de falha
                    }
            } catch (e: Exception) {
                Log.e("ServiceProfessionalViewModel", "Erro ao buscar serviços: ${e.message}")
                setLoadingState(false) // Finaliza o carregamento em caso de falha
            }
        }
    }

    fun createServiceByProfessional(professionalId: String, context: Context) {
        val state = _uiState.value
        val serviceRequest = ServiceRequest(
            name = state.nameService,
            description = state.description,
            value = state.price!!,
            //categories = state.categories,
            //imageUrl = state.imageUri.toString()
        )

        Log.d("ServiceProfessionalViewModel", "ServiceRequest: $serviceRequest")
        setLoadingState(true) // Inicia o carregamento
        sendServiceByProfessional(professionalId, serviceRequest, context)
    }

    private fun sendServiceByProfessional(
        professionalId: String,
        serviceRequest: ServiceRequest,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val result = servicesRepository.createService(
                    professionalId,
                    serviceRequest,
                    context
                )

                result
                    .onSuccess { service -> onServicesChange(listOf(service)) }
                    .onFailure {
                        Log.e(
                            "ServiceProfessionalViewModel",
                            "Erro ao criar serviço: ${it.message}"
                        )
                        setLoadingState(false) // Finaliza o carregamento em caso de falha
                    }
            } catch (e: Exception) {
                Log.e("ServiceProfessionalViewModel", "Erro ao criar serviço: ${e.message}", e)
                setLoadingState(false) // Finaliza o carregamento em caso de falha
            }
        }
    }

    fun deleteService(serviceId: String, context: Context) {
        setLoadingState(true) // Inicia o carregamento
        viewModelScope.launch {
            try {
                val result = servicesRepository.deleteService(serviceId, context)

                result
                    .onSuccess {
                        val updatedServices =
                            _uiState.value.services.filterNot { it.id.toString() == serviceId }
                        onServicesChange(updatedServices)
                    }
                    .onFailure {
                        Log.e(
                            "ServiceProfessionalViewModel",
                            "Erro ao deletar serviço: ${it.message}"
                        )
                        setLoadingState(false) // Finaliza o carregamento em caso de falha
                    }
            } catch (e: Exception) {
                Log.e("ServiceProfessionalViewModel", "Erro ao deletar serviço: ${e.message}", e)
                setLoadingState(false) // Finaliza o carregamento em caso de falha
            }
        }
    }

    fun onNameServiceChange(newNameService: String) {
        Log.d("ServiceProfessionalViewModel", "Nome do Serviço: $newNameService")
        _uiState.update { it.copy(nameService = newNameService) }
    }

    fun onDescriptionChange(newDescription: String) {
        Log.d("ServiceProfessionalViewModel", "Descrição: $newDescription")
        _uiState.update { it.copy(description = newDescription) }
    }

    fun onPriceChange(newPrice: String) {
        Log.d("ServiceProfessionalViewModel", "Preço: $newPrice")
        _uiState.update { it.copy(price = newPrice.toDoubleOrNull() ?: 0.0) }
    }

    fun onCategoryChange(newCategories: String) {
        Log.d("ServiceProfessionalViewModel", "Categorias: $newCategories")
        _uiState.update { it.copy(categories = newCategories) }
    }

    fun onImageSelected(uri: Uri) {
        Log.d("ServiceProfessionalViewModel", "Imagem Selecionada: $uri")
        _uiState.update { it.copy(imageUri = uri) }
    }

}
