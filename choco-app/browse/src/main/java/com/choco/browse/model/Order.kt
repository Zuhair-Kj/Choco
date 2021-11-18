package com.choco.browse.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.choco.browse.cache.TABLE_ORDERS
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.reflect.Type

@Entity(tableName = TABLE_ORDERS)
@Parcelize
data class Order(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "timeStamp")
    val date: Long,
    @ColumnInfo(name = "entries")
    val entries: List<Entry>
): Parcelable

class OrderDataTypeConverter: KoinComponent {
    private val gson: Gson by inject()

    @TypeConverter
    fun fromString(string: String): Order {
        val type: Type = object : TypeToken<Order?>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromStringArray(string: String): List<Order> {
        val type: Type = object : TypeToken<List<Order>?>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun toString(order: Order): String {
        return gson.toJson(order)
    }

    @TypeConverter
    fun toJsonStringArray(list: List<Order>): String {
        return gson.toJson(list)
    }
}