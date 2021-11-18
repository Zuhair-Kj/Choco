package com.choco.account.di

import com.choco.account.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@JvmField
val accountModule = module {
    viewModel {
        ProfileViewModel(userSessionManager = get())
    }
}