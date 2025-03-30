package com.jsontextfield.urbandictionary.data

import androidx.compose.ui.util.fastMap
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DatastorePreferencesRepository(
    private val dataStore: DataStore<Preferences>
) :
    IPreferencesRepository {
    override suspend fun getBookmarks(): List<Int> {
        return dataStore.data.map { preferences ->
            (preferences[stringSetPreferencesKey("bookmarks")]?.toList() ?: emptyList()).fastMap {
                it.toInt()
            }
        }.first()
    }

    override suspend fun addBookmark(id: Int) {
        dataStore.edit { preferences ->
            val bookmarks = preferences[stringSetPreferencesKey("bookmarks")] ?: emptySet()
            preferences[stringSetPreferencesKey("bookmarks")] = bookmarks + id.toString()
        }
    }

    override suspend fun removeBookmark(id: Int) {
        dataStore.edit { preferences ->
            val bookmarks = preferences[stringSetPreferencesKey("bookmarks")] ?: emptySet()
            preferences[stringSetPreferencesKey("bookmarks")] = bookmarks - id.toString()
        }
    }
}