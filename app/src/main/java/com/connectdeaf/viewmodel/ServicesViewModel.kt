package com.connectdeaf.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.connectdeaf.viewmodel.Service

class ServicesViewModel : ViewModel() {

    private val _serviceList = mutableStateListOf<Service>()
    val serviceList: List<Service> get() = _serviceList

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> get() = _searchQuery

    private val _currentPage = mutableStateOf(0)
    val currentPage: State<Int> get() = _currentPage

    private val servicesPerPage = 10

    // Busca e atualização dos serviços (mockado aqui)
    init {
        loadServices()
    }

    fun loadServices() {
        viewModelScope.launch {
            // Simulação de carregamento de dados
            val mockData = List(25) { index ->
                Service(
                    id = "service_$index",
                    name = "Serviço ${index + 1}",
                    description = "Descrição do Serviço ${index + 1}",
                    category = listOf("Categoria A", "Categoria B"),
                    value = "R$ ${(100 + index * 10)}",
                    imageUrl = "https://via.placeholder.com/150"
                )
            }
            _serviceList.clear()
            _serviceList.addAll(mockData)
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun nextPage() {
        if ((_currentPage.value + 1) * servicesPerPage < _serviceList.size) {
            _currentPage.value++
        }
    }

    fun previousPage() {
        if (_currentPage.value > 0) {
            _currentPage.value--
        }
    }

    fun getPaginatedList(): List<Service> {
        val startIndex = _currentPage.value * servicesPerPage
        val endIndex = (startIndex + servicesPerPage).coerceAtMost(_serviceList.size)
        return _serviceList.subList(startIndex, endIndex)
    }
}
