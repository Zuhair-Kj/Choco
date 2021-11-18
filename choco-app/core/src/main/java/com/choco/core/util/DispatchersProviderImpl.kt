package com.choco.core.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class DispatchersProviderImpl: DispatchersProvider {
    override fun io() = Dispatchers.IO
    override fun default() = Dispatchers.Default
    override fun main() = Dispatchers.Main
    override fun unconfined() = Dispatchers.Unconfined

    override fun viewModelScope(viewModel: ViewModel): CoroutineScope = viewModel.viewModelScope

}