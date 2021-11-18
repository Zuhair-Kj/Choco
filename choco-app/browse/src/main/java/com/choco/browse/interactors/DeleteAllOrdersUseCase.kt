package com.choco.browse.interactors

import com.choco.browse.repo.OrdersRepository

class DeleteAllOrdersUseCase(private val ordersRepository: OrdersRepository) {
    suspend fun clearOrders() = ordersRepository.clearOrders()
}