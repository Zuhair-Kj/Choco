package com.choco.test

import androidx.lifecycle.ViewModel
import com.choco.core.util.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope

@ExperimentalCoroutinesApi
class TestDispatchersProvider: DispatchersProvider {
    override fun io(): CoroutineDispatcher = TestCoroutineDispatcher()

    override fun default(): CoroutineDispatcher = TestCoroutineDispatcher()

    override fun main(): CoroutineDispatcher = TestCoroutineDispatcher()

    override fun unconfined(): CoroutineDispatcher = TestCoroutineDispatcher()

    override fun viewModelScope(viewModel: ViewModel): CoroutineScope = TestCoroutineScope()
}