package com.jsontextfield.departurescreen.di

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
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.HEADERS
            }
            defaultRequest {
                url("https://api.urbandictionary.com/")
            }
        }
    }
}

val dataModule = module {
    single<UrbanDictionaryAPI> { UrbanDictionaryAPI(get<HttpClient>()) }
    single<IUrbanDictionaryDataSource> {
        UrbanDictionaryDataSource(get<UrbanDictionaryAPI>())
    }
}

val viewModelModule = module {
    factoryOf(::MainViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            networkModule,
            dataModule,
            viewModelModule,
        )
    }
}
