package com.connectdeaf.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.connectdeaf.data.repository.ServicesRepository
import com.connectdeaf.viewmodel.ServicesViewModel
import com.connectdeaf.viewmodel.ServiceProfessionalViewModel

class ServicesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesViewModel::class.java)) {
            return ServicesViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ServicesByProfessionalViewModelFactory(
    private val context: Context,
    private val servicesRepository: ServicesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServiceProfessionalViewModel::class.java)) {
            return ServiceProfessionalViewModel(servicesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}