package com.jsontextfield.urbandictionary.data

import com.jsontextfield.urbandictionary.network.UrbanDictionaryAPI
import com.jsontextfield.urbandictionary.network.model.Definition

class UrbanDictionaryDataSource(
    private val urbanDictionaryAPI: UrbanDictionaryAPI,
) : IUrbanDictionaryDataSource {
    override suspend fun getDefinition(id: String): List<Definition> {
        return urbanDictionaryAPI.getWordDefinition(id).definitions
    }

    override suspend fun getDefinitions(term: String, size: Int): List<Definition> {
        val page = if (size > 9 && size % 10 == 0) size / 10 + 1 else 1
        return urbanDictionaryAPI.getWordDefinition(term, page).definitions
    }

    override suspend fun getWordsOfTheDay(size: Int): List<Definition> {
        val page = if (size > 9 && size % 10 == 0) size / 10 + 1 else 1
        return urbanDictionaryAPI.getWordsOfTheDay(page).definitions
    }

    override suspend fun getAutocompleteSuggestions(query: String): List<String> {
        return urbanDictionaryAPI.getAutoCompleteSuggestions(query)
    }

    override suspend fun getRandomWords(): List<Definition> {
        return urbanDictionaryAPI.getRandomWords().definitions
    }
}