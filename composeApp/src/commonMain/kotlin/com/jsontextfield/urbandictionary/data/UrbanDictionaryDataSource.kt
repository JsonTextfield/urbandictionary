package com.jsontextfield.urbandictionary.data

import com.jsontextfield.urbandictionary.network.UrbanDictionaryAPI
import com.jsontextfield.urbandictionary.network.model.Definition

class UrbanDictionaryDataSource(
    private val urbanDictionaryAPI: UrbanDictionaryAPI,
) : IUrbanDictionaryDataSource {
    override suspend fun getDefinitions(term: String): List<Definition> {
        return urbanDictionaryAPI.getWordDefinition(term).definitions
    }

    override suspend fun getWordsOfTheDay(): List<Definition> {
        return urbanDictionaryAPI.getWordsOfTheDay().definitions
    }

    override suspend fun getAutocompleteSuggestions(query: String): List<String> {
        return urbanDictionaryAPI.getAutoCompleteSuggestions(query)
    }

    override suspend fun getRandomWords(): List<Definition> {
        return urbanDictionaryAPI.getRandomWords().definitions
    }
}