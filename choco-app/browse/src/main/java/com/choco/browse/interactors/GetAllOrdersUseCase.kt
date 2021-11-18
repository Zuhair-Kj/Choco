package com.choco.browse.interactors

import com.choco.browse.repo.OrdersRepository

class GetAllOrdersUseCase(private val ordersRepository: OrdersRepository) {
    suspend fun getAllOrders() = ordersRepository.getOrders()
}