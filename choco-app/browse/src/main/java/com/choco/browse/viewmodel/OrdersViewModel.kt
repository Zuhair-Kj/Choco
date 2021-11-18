package com.choco.browse.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choco.browse.model.Order
import com.choco.browse.repo.OrdersRepository
import com.choco.core.util.DispatchersProvider
import com.choco.core.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val ordersRepository: OrdersRepository,
    private val ordersMutableLiveData: MutableLiveData<Resource<List<Order>>> = MutableLiveData(),
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {
    val ordersLiveData: LiveData<Resource<List<Order>>>
        get() = ordersMutableLiveData

    fun getOrders() {
        dispatchersProvider.viewModelScope(this).launch(dispatchersProvider.io()) {
            ordersMutableLiveData.postValue(Resource.loading())

            try {
                ordersMutableLiveData.postValue(
                    Resource.success(
                        ordersRepository.getOrders()
                    )
                )
            } catch (throwable: Throwable) {
                ordersMutableLiveData.postValue(
                    Resource.error()
                )
            }
        }
    }
}