package com.choco.browse.model

import android.os.Parcelable
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.reflect.Type

@Parcelize
data class Entry(
    val product: Product,
    var quantity: Int = 0
): Parcelable


class EntryDataTypeConverter: KoinComponent {
    private val gson: Gson by inject()
    @TypeConverter
    fun fromString(string: String): Entry {
        val type: Type = object : TypeToken<Entry?>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun fromStringArray(string: String): List<Entry> {
        val type: Type = object : TypeToken<List<Entry>?>() {}.type
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun toString(entry: Entry): String {
        return gson.toJson(entry)
    }

    @TypeConverter
    fun toJsonStringArray(list: List<Entry>): String {
        return gson.toJson(list)
    }
}