package com.jsontextfield.urbandictionary.data

class PreferencesRepository : IPreferencesRepository {
    private val bookmarks = mutableListOf<Int>()
    override suspend fun getBookmarks(): List<Int> {
        return bookmarks
    }

    override suspend fun addBookmark(id: Int) {
        bookmarks.add(id)
    }

    override suspend fun removeBookmark(id: Int) {
        bookmarks.remove(id)
    }

}