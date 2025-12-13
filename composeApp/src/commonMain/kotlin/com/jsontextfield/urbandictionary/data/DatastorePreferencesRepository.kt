package com.jsontextfield.urbandictionary.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatastorePreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : IPreferencesRepository {
    val bookmarksKey = stringSetPreferencesKey(BOOKMARKS_KEY)

    override fun getBookmarks(): Flow<Set<String>> {
        return dataStore.data.map { preferences ->
            preferences[bookmarksKey] ?: emptySet()
        }
    }

    override suspend fun setBookmarks(id: String) {
        dataStore.edit { preferences ->
            val bookmarks = (preferences[bookmarksKey] ?: emptySet())
            preferences[bookmarksKey] = if (bookmarks.contains(id)) {
                bookmarks - id
            } else {
                bookmarks + id
            }
        }
    }
}

const val BOOKMARKS_KEY = "bookmarks"