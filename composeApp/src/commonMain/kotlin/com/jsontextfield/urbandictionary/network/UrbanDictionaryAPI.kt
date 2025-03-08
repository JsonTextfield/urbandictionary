package com.jsontextfield.urbandictionary.network

import com.jsontextfield.urbandictionary.network.model.UrbanDictionaryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

class UrbanDictionaryAPI(private val client: HttpClient) {
    suspend fun getRandomWords(): UrbanDictionaryResponse {
        return client.get {
            url {
                path("v0/random")
            }
        }.body()
    }

    suspend fun getWordsOfTheDay(page: Int = 1): UrbanDictionaryResponse {
        return client.get {
            url {
                path("v0/words_of_the_day")
                parameter("page", page)
            }
        }.body()
    }

    suspend fun getWordDefinition(id: Int): UrbanDictionaryResponse {
        return client.get {
            url {
                path("v0/define")
                parameter("defid", id)
            }
        }.body()
    }

    suspend fun getWordDefinition(word: String, page: Int = 1): UrbanDictionaryResponse {
        return client.get {
            url {
                path("v0/define")
                parameter("term", word)
                parameter("page", page)
            }
        }.body()
    }

    suspend fun getAutoCompleteSuggestions(query: String): List<String> {
        return client.get {
            url {
                path("v0/autocomplete")
                parameter("term", query)
            }
        }.body()
    }
}