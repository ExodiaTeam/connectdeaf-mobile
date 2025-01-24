package com.connectdeaf.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.connectdeaf.viewmodel.ServicesViewModel

class ServicesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesViewModel::class.java)) {
            return ServicesViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
