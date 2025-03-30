package com.jsontextfield.urbandictionary.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jsontextfield.urbandictionary.data.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<DataStore<Preferences>> {
            createDataStore()
        }
    }