package com.choco.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.choco.browse.cache.OrderDao
import com.choco.browse.model.EntryDataTypeConverter
import com.choco.browse.model.Order
import com.choco.browse.model.OrderDataTypeConverter
import com.choco.browse.model.ProductDataTypeConverter

const val DATABASE_NAME = "choco-db"
@Database(entities = [Order::class], version = 1)
@TypeConverters(value = [OrderDataTypeConverter::class, EntryDataTypeConverter::class, ProductDataTypeConverter::class])
abstract class ChocoDB: RoomDatabase() {
    abstract fun ordersDao(): OrderDao
}