package com.choco.auth.di

import com.choco.auth.interactors.LoginUseCase
import com.choco.auth.network.AuthApiService
import com.choco.auth.repo.AuthRepository
import com.choco.auth.utils.UserSessionManagerImpl
import com.choco.auth.viewmodel.LoginViewModel
import com.choco.core.auth.UserSessionManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

@JvmField
val authModule = module {
    viewModel {
        LoginViewModel(
            loginUseCase = get(),
            networkHelper = get(),
            userSessionManager = get(),
            dispatchersProvider = get()
        )
    }

    single { AuthRepository(authApiService = get()) }
    single { get<Retrofit>().create(AuthApiService::class.java) }
    single {
        LoginUseCase(repository = get())
    }

    single { UserSessionManagerImpl(preferenceHelper = get()) } bind UserSessionManager::class
}