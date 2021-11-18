package com.choco.mobile

import android.app.Application
import com.choco.account.di.accountModule
import com.choco.auth.di.authModule
import com.choco.browse.di.browseModule
import com.choco.cache.di.roomModule
import com.choco.core.di.coreModule
import com.choco.core.di.networkModule
import com.choco.mobile.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            allowOverride(true)
            androidContext(this@MainApp)
            modules(
                listOf(coreModule, networkModule, authModule,
                    accountModule, appModule, browseModule, roomModule)
            )

        }
    }

}