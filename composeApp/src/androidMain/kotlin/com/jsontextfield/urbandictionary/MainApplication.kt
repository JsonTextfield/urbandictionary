package com.jsontextfield.urbandictionary

import android.app.Application
import com.jsontextfield.departurescreen.di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}