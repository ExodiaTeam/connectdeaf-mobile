package com.connectdeaf.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.connectdeaf.network.retrofit.ApiServiceFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Service(
    val id: String,
    val name: String,
    val description: String,
    val category: List<String>,
    val value: String,
    val imageUrl: String
)

data class Assessment(
    val name: String,
    val stars: Int,
    val description: String
)

data class Address(
    val city: String,
    val state: String
)

data class Profile(
    val id: String,
    val name: String,
    val addresses: List<Address>,
    val description: String?,
    val imageUrl: String?,
    val category: List<String>?,
    val services: List<Service>?,
    val assessments: List<Assessment>?
)

class ProfileViewModel : ViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile

    fun fetchProfile(professionalId: String, context: Context) {
        viewModelScope.launch {
            try {
                val apiServiceFactory = ApiServiceFactory(context)

                val professionalService = apiServiceFactory.professionalService
                val serviceService = apiServiceFactory.serviceService

                val professional = professionalService.getProfessional(professionalId)

                val services = serviceService.getServices(professionalId)

                _profile.value = professional.copy(
                    services = services,
                    assessments = null
                )
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching profile", e)
            }
        }
    }
}


