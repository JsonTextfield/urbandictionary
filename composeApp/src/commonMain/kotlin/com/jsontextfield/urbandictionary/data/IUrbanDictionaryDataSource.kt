package com.jsontextfield.urbandictionary.data

import com.jsontextfield.urbandictionary.network.model.Definition

interface IUrbanDictionaryDataSource {
    suspend fun getDefinition(id: String): List<Definition>
    suspend fun getDefinitions(term: String, size: Int): List<Definition>
    suspend fun getWordsOfTheDay(size: Int): List<Definition>
    suspend fun getAutocompleteSuggestions(query: String): List<String>
    suspend fun getRandomWords(): List<Definition>
}