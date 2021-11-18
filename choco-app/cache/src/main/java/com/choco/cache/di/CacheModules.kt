package com.choco.cache.di

import androidx.room.Room
import com.choco.browse.model.EntryDataTypeConverter
import com.choco.browse.model.OrderDataTypeConverter
import com.choco.browse.model.ProductDataTypeConverter
import com.choco.cache.ChocoDB
import com.choco.cache.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@JvmField
val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(), ChocoDB::class.java, DATABASE_NAME)
            .build()
    }

    single {
        get<ChocoDB>().ordersDao()
    }
}