package com.connectdeaf.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectdeaf.domain.model.Service
import com.connectdeaf.network.retrofit.ApiServiceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServicesViewModel(context: Context) : ViewModel() {

    private val _serviceList = mutableStateListOf<Service>()
    val serviceList: List<Service> get() = _serviceList

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> get() = _isLoading

    private val _searchQuery = MutableStateFlow(TextFieldValue(""))
    val searchQuery: StateFlow<TextFieldValue> get() = _searchQuery

    private val _currentPage = mutableIntStateOf(0)
    val currentPage: State<Int> get() = _currentPage

    val servicesPerPage = 10

    private val _selectedState = mutableStateOf("")
    val selectedState: State<String> get() = _selectedState

    private val _selectedCity = mutableStateOf("")
    val selectedCity: State<String> get() = _selectedCity

    private val _filteredServiceList = mutableStateListOf<Service>()
    val filteredServiceList: List<Service> get() = _filteredServiceList

    private val _states = mutableStateListOf<String>()
    val states: List<String> get() = _states

    private val _cities = mutableStateListOf<String>()
    val cities: List<String> get() = _cities

    init {
        loadServices(context)
    }

    fun loadStatesFromIBGE(context: Context) {
        viewModelScope.launch {
            try {
                val apiServiceFactory = ApiServiceFactory(context)
                val ibgeService = apiServiceFactory.ibgeService

                val statesList = ibgeService.getStates() // Chamada Retrofit
                Log.d("ServicesViewModel", "Estados carregados do IBGE: $statesList")
                _states.clear()
                _states.add("")
                _states.addAll(statesList.map { it.sigla }.sorted()) // Converte para lista de nomes e ordena
                Log.d("ServicesViewModel", "Estados após ordenação: $_states")
            } catch (e: Exception) {
                Log.e("ServicesViewModel", "Erro ao carregar estados do IBGE: ${e.message}", e)
            }
        }
    }

    fun loadCitiesForStateFromIBGE(stateUF: String, context: Context) {
        viewModelScope.launch {
            try {
                val apiServiceFactory = ApiServiceFactory(context)
                val ibgeService = apiServiceFactory.ibgeService

                val citiesList = ibgeService.getCities(stateUF) // Chamada Retrofit
                Log.d("ServicesViewModel", "Cidades carregadas do IBGE: $citiesList")
                _cities.clear()
                _cities.add("")
                _cities.addAll(citiesList.map { it.nome }.sorted()) // Converte para lista de nomes e ordena
                Log.d("ServicesViewModel", "Cidades após ordenação: $_cities")
            } catch (e: Exception) {
                Log.e("ServicesViewModel", "Erro ao carregar cidades do IBGE: ${e.message}", e)
            }
        }
    }


    private fun loadServices(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true // Inicia o carregamento
            try {
                val apiServiceFactory = ApiServiceFactory(context)
                val apiService = apiServiceFactory.serviceService

                val services = apiService.getServices()

                _serviceList.clear()
                _serviceList.addAll(services)
                filterServices() // Filtra os serviços após o carregamento
            } catch (e: Exception) {
                Log.e("ServicesViewModel", "Erro ao carregar serviços: ${e.message}", e)
            } finally {
                _isLoading.value = false // Finaliza o carregamento
            }
        }
    }

    fun onSearchQueryChange(newQuery: TextFieldValue) {
        _searchQuery.value = newQuery
        filterServices() // Filtra os serviços ao alterar a query
    }

    fun nextPage() {
        if ((_currentPage.intValue + 1) * servicesPerPage < _filteredServiceList.size) {
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
        val endIndex = (startIndex + servicesPerPage).coerceAtMost(_filteredServiceList.size)
        return _filteredServiceList.subList(startIndex, endIndex)
    }

    fun updateState(newState: String) {
        Log.d("ServicesViewModel", "Updating state to $newState")
        _selectedState.value = newState
        filterServices() // Filtra os serviços ao alterar o estado
    }

    fun updateCity(newCity: String) {
        Log.d("ServicesViewModel", "Updating city to $newCity")
        _selectedCity.value = newCity
        filterServices() // Filtra os serviços ao alterar a cidade
    }

    private fun filterServices() {
        val filteredList = _serviceList.filter { service ->
            // Filtra por query de pesquisa
            service.name.contains(_searchQuery.value.text, ignoreCase = true) &&
                    // Filtra por estado (se selecionado)
                    (_selectedState.value.isEmpty() || service.state == _selectedState.value) &&
                    // Filtra por cidade (se selecionada)
                    (_selectedCity.value.isEmpty() || service.city == _selectedCity.value)
        }
        _filteredServiceList.clear()
        _filteredServiceList.addAll(filteredList)
        _currentPage.intValue = 0 // Reseta a paginação após filtrar
    }
}

