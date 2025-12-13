package com.jsontextfield.urbandictionary.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jsontextfield.urbandictionary.data.DatastorePreferencesRepository
import com.jsontextfield.urbandictionary.data.IPreferencesRepository
import com.jsontextfield.urbandictionary.data.IUrbanDictionaryDataSource
import com.jsontextfield.urbandictionary.data.UrbanDictionaryDataSource
import com.jsontextfield.urbandictionary.network.UrbanDictionaryAPI
import com.jsontextfield.urbandictionary.ui.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect val platformModule: Module

val dataModule = module {
    singleOf(::UrbanDictionaryAPI)
    single<IUrbanDictionaryDataSource> {
        UrbanDictionaryDataSource(get<UrbanDictionaryAPI>())
    }
    single<IPreferencesRepository> {
        DatastorePreferencesRepository(get<DataStore<Preferences>>())
        //CachePreferencesRepository()
    }
}

val viewModelModule = module {
    factoryOf(::MainViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            dataModule,
            viewModelModule,
            platformModule,
        )
    }
}
