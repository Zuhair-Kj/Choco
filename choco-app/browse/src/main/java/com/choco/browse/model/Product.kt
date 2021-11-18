package com.choco.browse.model

import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.reflect.Type

@Parcelize
data class Product(

    @SerializedName("Id")
    val id: String?,
    val name: String?,
    @SerializedName("Description")
    val description: String?,
    val price: Int,
    val photo: String?
): Parcelable

class ProductDataTypeConverter(): KoinComponent {
    private val gson: Gson by inject()

    @TypeConverter
    fun fromString(string: String): Product {
        val type: Type = object : TypeToken<Product?>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun productFromStringArray(string: String): List<Product> {
        val type: Type = object : TypeToken<List<Order>?>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun toString(product: Product): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun toProductStringArray(list: List<Product>): String {
        return gson.toJson(list)
    }

}