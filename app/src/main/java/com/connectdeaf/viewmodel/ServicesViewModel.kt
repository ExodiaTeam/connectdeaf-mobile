package com.connectdeaf.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectdeaf.domain.model.Service
import com.connectdeaf.network.retrofit.ApiServiceFactory
import kotlinx.coroutines.launch

class ServicesViewModel(context: Context) : ViewModel() {

    private val _serviceList = mutableStateListOf<Service>()
    val serviceList: List<Service> get() = _serviceList

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> get() = _searchQuery

    private val _currentPage = mutableIntStateOf(0)
    val currentPage: State<Int> get() = _currentPage

    private val servicesPerPage = 10

    private val _selectedState = mutableStateOf("")
    val selectedState: State<String> = _selectedState

    private val _selectedCity = mutableStateOf("")
    val selectedCity: State<String> = _selectedCity

    init {
        loadServices(context)
    }

    private fun loadServices(context: Context) {
        viewModelScope.launch {
            try {
                val apiServiceFactory = ApiServiceFactory(context)
                val apiService = apiServiceFactory.serviceService

                val services = apiService.getServices()

                _serviceList.clear()
                _serviceList.addAll(services)
            } catch (e: Exception) {
                Log.e("ServicesViewModel", "Erro ao carregar serviços: ${e.message}", e)
            }
        }
    }


    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun nextPage() {
        if ((_currentPage.intValue + 1) * servicesPerPage < _serviceList.size) {
            _currentPage.intValue++
        }
    }

    fun previousPage() {
        if (_currentPage.intValue > 0) {
            _currentPage.intValue--
        }
    }

    fun getPaginatedList(): List<Service> {
        val startIndex = _currentPage.intValue * servicesPerPage
        val endIndex = (startIndex + servicesPerPage).coerceAtMost(_serviceList.size)
        return _serviceList.subList(startIndex, endIndex)
    }

    fun updateState(newState: String) {
        Log.d("ServicesViewModel", "Updating state to $newState")
        _selectedState.value = newState

    }


    fun updateCity(newCity: String) {
        Log.d("ServicesViewModel", "Updating city to $newCity")
        _selectedCity.value = newCity
        // Atualiza a lista de serviços com base na cidade selecionada
    }
}

