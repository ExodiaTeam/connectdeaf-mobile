package com.connectdeaf.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.connectdeaf.data.repository.FirebaseRepository
import com.connectdeaf.data.repository.ProfessionalRepository
import com.connectdeaf.data.repository.UserRepository
import com.connectdeaf.viewmodel.RegisterViewModel

class RegisterViewModelFactory() : ViewModelProvider.Factory {
    private val userRepository: UserRepository = UserRepository()
    private val professionalRepository: ProfessionalRepository = ProfessionalRepository()
    private val firebaseRepository: FirebaseRepository = FirebaseRepository()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepository, professionalRepository, firebaseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
