package com.choco.browse.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.choco.browse.interactors.CreateOrderUseCase
import com.choco.browse.interactors.GetAllProductsUseCase
import com.choco.browse.model.Entry
import com.choco.browse.model.Product
import com.choco.core.auth.UserSessionManager
import com.choco.core.util.NetworkHelper
import com.choco.core.util.Resource
import com.choco.test.TestDispatchersProvider
import com.choco.test.UnitTestState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@ExperimentalCoroutinesApi
class ProductsViewModelTest {
    private lateinit var viewModel: ProductsViewModel

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @MockK lateinit var getAllProductUseCase: GetAllProductsUseCase
    @MockK lateinit var createOrderUseCase: CreateOrderUseCase
    @MockK lateinit var networkHelper: NetworkHelper
    @MockK lateinit var userSessionManager: UserSessionManager
    @SpyK
    var testState = UnitTestState<Resource<List<Entry>>>()

    private val testDispatchersProvider = TestDispatchersProvider()
    private val stubProductsList = listOf(Product("1", "p", "description", 10, null))
    private val token = "token"

    @Before
    fun init() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = ProductsViewModel(
            getAllProductUseCase,
            createOrderUseCase,
            networkHelper,
            userSessionManager = userSessionManager,
            dispatchersProvider = testDispatchersProvider
        )
    }

    @Test
    fun `should fetch products`() {
        coEvery {
            networkHelper.isConnected()
        } returns true

        coEvery {
            userSessionManager.getToken()
        } returns token
        coEvery {
            getAllProductUseCase.getAllProducts(token)
        } returns stubProductsList

        viewModel.productsLiveData.observeForever {
            testState.obj = it
        }

        viewModel.getProducts()

        coVerifySequence {
            testState.obj = Resource.loading()
            testState.obj = Resource.success(listOf(Entry(stubProductsList[0], 0)))
        }
    }

    @Test
    fun `should return network error`() {
        coEvery {
            networkHelper.isConnected()
        } returns false

        viewModel.productsLiveData.observeForever {
            testState.obj = it
        }

        viewModel.getProducts()

        coVerify(exactly = 0) { userSessionManager.getToken() }
        coVerify(exactly = 0) { getAllProductUseCase.getAllProducts(any()) }
        coVerifySequence {
            testState.obj = Resource.networkError()
        }
    }
}