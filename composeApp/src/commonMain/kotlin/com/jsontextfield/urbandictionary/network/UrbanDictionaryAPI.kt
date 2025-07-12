package com.jsontextfield.urbandictionary.network

import com.jsontextfield.urbandictionary.network.model.UrbanDictionaryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

class UrbanDictionaryAPI(private val client: HttpClient) {
    suspend fun getRandomWords(): UrbanDictionaryResponse {
        return try {
            client.get {
                url {
                    path("random")
                }
            }.body()
        } catch (_: Exception) {
            UrbanDictionaryResponse(emptyList())
        }
    }

    suspend fun getWordsOfTheDay(page: Int = 1): UrbanDictionaryResponse {
        return try {
            client.get {
                url {
                    path("words_of_the_day")
                    parameter("page", page)
                }
            }.body()
        } catch (_: Exception) {
            UrbanDictionaryResponse(emptyList())
        }
    }

    suspend fun getWordDefinition(id: Int): UrbanDictionaryResponse {
        return try {
            client.get {
                url {
                    path("define")
                    parameter("defid", id)
                }
            }.body()
        } catch (_: Exception) {
            UrbanDictionaryResponse(emptyList())
        }
    }

    suspend fun getWordDefinition(word: String, page: Int = 1): UrbanDictionaryResponse {
        return try {
            client.get {
                url {
                    path("define")
                    parameter("term", word)
                    parameter("page", page)
                }
            }.body()
        } catch (_: Exception) {
            UrbanDictionaryResponse(emptyList())
        }
    }

    suspend fun getAutoCompleteSuggestions(query: String): List<String> {
        return try {
            client.get {
                url {
                    path("autocomplete")
                    parameter("term", query)
                }
            }.body()
        } catch (_: Exception) {
            emptyList()
        }
    }
}