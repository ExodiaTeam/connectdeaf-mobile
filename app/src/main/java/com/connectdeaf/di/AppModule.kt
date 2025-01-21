package com.connectdeaf.di

import com.connectdeaf.data.repository.FaqRepository
import com.connectdeaf.domain.usecase.SendFaqMessageUseCase
import com.connectdeaf.network.retrofit.ApiServiceFactory
import com.connectdeaf.network.services.FAQApiService
import com.connectdeaf.viewmodel.FAQViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

import org.koin.android.ext.koin.androidContext

val appModule = module {
    single { ApiServiceFactory(androidContext()) }
    single<FAQApiService> {
        get<ApiServiceFactory>().faqApiService
    }
    single { FaqRepository(get()) }
    single { SendFaqMessageUseCase(get()) }
    viewModel { FAQViewModel(get()) }
}


