package com.mustafacan.currencytrack

import android.app.Application
import com.mustafacan.currencytrack.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

// Created by Mustafa Can on 14.08.2022.

class CurrencyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CurrencyApplication)
            modules(listOf(AppModule.module))
        }
    }
}