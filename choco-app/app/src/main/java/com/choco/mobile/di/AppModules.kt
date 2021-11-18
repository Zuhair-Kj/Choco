package com.choco.mobile.di

import com.choco.mobile.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val appModule = module {
    viewModel {
        SplashViewModel(userSessionManager = get())
    }
}