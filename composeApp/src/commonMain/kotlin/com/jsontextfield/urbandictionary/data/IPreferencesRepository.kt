package com.jsontextfield.urbandictionary.data

interface IPreferencesRepository {
    suspend fun getBookmarks() : List<Int>

    suspend fun addBookmark(id: Int)

    suspend fun removeBookmark(id: Int)
}