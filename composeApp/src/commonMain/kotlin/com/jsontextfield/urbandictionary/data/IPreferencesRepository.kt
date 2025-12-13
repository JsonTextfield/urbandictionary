package com.jsontextfield.urbandictionary.data

import kotlinx.coroutines.flow.Flow

interface IPreferencesRepository {
    fun getBookmarks() : Flow<Set<String>>

    suspend fun setBookmarks(id: String)
}