package com.choco.browse.interactors

import com.choco.browse.model.Order
import com.choco.browse.repo.OrdersRepository

class CreateOrderUseCase(private val ordersRepository: OrdersRepository) {
    suspend fun addOrder(order: Order) = ordersRepository.addOrder(order)
}