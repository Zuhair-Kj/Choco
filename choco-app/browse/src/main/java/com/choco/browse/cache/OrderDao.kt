package com.choco.browse.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.choco.browse.model.Order

const val TABLE_ORDERS = "orders"
@Dao
abstract class OrderDao {
    @Query("SELECT * FROM $TABLE_ORDERS")
    abstract suspend fun getAllOrders(): List<Order>

    @Query("SELECT * FROM $TABLE_ORDERS WHERE id LIKE :orderId")
    abstract suspend fun getOrderWithId(orderId: String): Order

    @Query("DELETE FROM $TABLE_ORDERS")
    abstract suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRow(order: Order)
}