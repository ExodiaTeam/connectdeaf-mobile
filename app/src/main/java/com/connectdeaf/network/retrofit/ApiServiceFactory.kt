package com.connectdeaf.network.retrofit

import android.content.Context
import com.connectdeaf.data.repository.AuthRepository
import com.connectdeaf.network.RetrofitInstance
import com.connectdeaf.network.services.ApiService
import com.connectdeaf.network.services.FAQApiService
import com.connectdeaf.network.services.ProfessionalService
import com.connectdeaf.network.services.ServiceService
import com.connectdeaf.network.services.UserService
import retrofit2.Retrofit

class ApiServiceFactory(context: Context) {

    private val authRepository = AuthRepository(context)

    private val getToken: () -> String? = {
        authRepository.getAuthToken()
    }

    private val sharedRetrofit: Retrofit = RetrofitInstance.createRetrofit("http://10.0.2.2:8080", getToken)

    private val aiRetrofit: Retrofit = RetrofitInstance.createRetrofit("https://webapp-connectdeaf-dev.azurewebsites.net/", getToken)

    val apiService: ApiService by lazy {
        sharedRetrofit.create(ApiService::class.java)
    }

    val professionalService: ProfessionalService by lazy {
        sharedRetrofit.create(ProfessionalService::class.java)
    }

    val userService: UserService by lazy {
        sharedRetrofit.create(UserService::class.java)
    }

    val serviceService: ServiceService by lazy {
        sharedRetrofit.create(ServiceService::class.java)
    }

    val faqApiService: FAQApiService by lazy {
        aiRetrofit.create(FAQApiService::class.java)
    }
}
