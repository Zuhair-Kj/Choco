package com.choco.core.di

import android.content.Context
import com.choco.core.util.DispatchersProvider
import com.choco.core.util.DispatchersProviderImpl
import com.choco.core.util.NetworkHelper
import com.choco.core.util.PreferenceHelper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://qo7vrra66k.execute-api.eu-west-1.amazonaws.com/"
const val PREFS_NAME = "CHOCO_PREFS"
@JvmField
val networkModule = module {
    single { Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(get())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }
    }

    single<Gson> {
        GsonBuilder().create()
    }
}

@JvmField
val coreModule = module {
    single { androidContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }
    single { PreferenceHelper(sharedPreferences = get()) }
    single { NetworkHelper(context = androidContext()) }
    single { DispatchersProviderImpl() } bind DispatchersProvider::class
}