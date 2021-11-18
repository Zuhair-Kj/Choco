package com.choco.browse.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choco.browse.interactors.CreateOrderUseCase
import com.choco.browse.interactors.GetAllProductsUseCase
import com.choco.browse.model.Entry
import com.choco.browse.model.Order
import com.choco.browse.model.Product
import com.choco.browse.repo.OrdersRepository
import com.choco.browse.repo.ProductsRepository
import com.choco.core.auth.UserSessionManager
import com.choco.core.util.DispatchersProvider
import com.choco.core.util.NetworkHelper
import com.choco.core.util.Resource
import kotlinx.coroutines.*
import java.util.*

class ProductsViewModel(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val networkHelper: NetworkHelper,
    private val productsMutableLiveData: MutableLiveData<Resource<List<Entry>>> = MutableLiveData(),
    private val createOrderMutableLiveData: MutableLiveData<Resource<String>> = MutableLiveData(),
    private val userSessionManager: UserSessionManager,
    private val dispatchersProvider: DispatchersProvider
): ViewModel() {
    val productsLiveData: LiveData<Resource<List<Entry>>>
        get() = productsMutableLiveData

    val createOrderLiveData: LiveData<Resource<String>>
        get() = createOrderMutableLiveData

    fun getProducts() {
        dispatchersProvider.viewModelScope(this).launch(dispatchersProvider.io()) {
            if (!networkHelper.isConnected()) {
                productsMutableLiveData.postValue(Resource.networkError())
                return@launch
            }
            productsMutableLiveData.postValue(Resource.loading())
            try {
                val token = userSessionManager.getToken()
                if (token == null) {
                    productsMutableLiveData.postValue(Resource.error())
                    return@launch
                }
                val result = getAllProductsUseCase.getAllProducts(token).map {
                    Entry(it)
                }
                productsMutableLiveData.postValue(
                    Resource.success(
                        result
                    )
                )
            } catch (throwable: Throwable) {
                productsMutableLiveData.postValue(Resource.error())
            }
        }
    }

    fun updateQuantityForProductInState(product: Product, quantity: Int) {
        val entriesCopy = productsMutableLiveData.value?.data?.map { it.copy() } ?: return

        entriesCopy.firstOrNull { it.product.id == product.id }
            ?.quantity = quantity

        productsMutableLiveData.value = (Resource.success(entriesCopy))
    }

    fun createOrder() {
        val timeStamp = Date().time
        val hash = timeStamp.hashCode().toString()
        val entries = productsLiveData.value?.data ?: emptyList()
        val order = Order(hash, timeStamp, entries)

        dispatchersProvider.viewModelScope(this).launch(dispatchersProvider.io()) {
            try {
                createOrderMutableLiveData.postValue(Resource.loading())
                createOrderUseCase.addOrder(order)
                withContext(dispatchersProvider.main()) {
                    createOrderMutableLiveData.value = Resource.success(hash)
                    clearQuantitiesForProductsInState()
                    createOrderMutableLiveData.value = Resource.noAction(hash)
                }
            } catch (throwable: Throwable) {
                createOrderMutableLiveData.postValue(Resource.error(hash))
            }
        }
    }

    private fun clearQuantitiesForProductsInState() {
        val entriesCopy = productsMutableLiveData.value?.data?.map { it.copy() } ?: return

        entriesCopy.forEach {
            it.quantity = 0
        }
        productsMutableLiveData.postValue(Resource.success(entriesCopy))
    }
}