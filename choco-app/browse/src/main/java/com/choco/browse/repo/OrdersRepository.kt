package com.choco.browse.repo

import com.choco.browse.cache.OrderDao
import com.choco.browse.model.Order

class OrdersRepository(private val ordersDao: OrderDao) {
    suspend fun getOrders(): List<Order> {
        return ordersDao.getAllOrders()
    }

    suspend fun addOrder(order: Order) {
        return ordersDao.insertRow(order)
    }

    suspend fun clearOrders() {
        return ordersDao.deleteAll()
    }
}