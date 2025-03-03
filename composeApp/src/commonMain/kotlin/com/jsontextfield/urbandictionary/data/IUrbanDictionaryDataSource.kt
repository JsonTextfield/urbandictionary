package com.jsontextfield.urbandictionary.data

import com.jsontextfield.urbandictionary.network.model.Definition

interface IUrbanDictionaryDataSource {
    suspend fun getDefinition(id: Int): List<Definition>
    suspend fun getDefinitions(term: String): List<Definition>
    suspend fun getWordsOfTheDay(): List<Definition>
    suspend fun getAutocompleteSuggestions(query: String): List<String>
    suspend fun getRandomWords(): List<Definition>
}