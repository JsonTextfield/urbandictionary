package com.jsontextfield.urbandictionary.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsontextfield.urbandictionary.data.IUrbanDictionaryDataSource
import com.jsontextfield.urbandictionary.network.model.Definition
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val dictionaryDataSource: IUrbanDictionaryDataSource,
//    private val preferencesRepository: IPreferencesRepository,
) : ViewModel() {

    private var _listType: MutableStateFlow<ListType> = MutableStateFlow(ListType.HOME)
    val listType: StateFlow<ListType> = _listType.asStateFlow()
    private var _wordsOfTheDay: MutableStateFlow<List<Definition>> = MutableStateFlow(emptyList())
    var displayList by mutableStateOf(emptyList<Definition>())
    private var _autoCompleteSuggestions: MutableStateFlow<List<String>> =
        MutableStateFlow(emptyList())
    val autoCompleteSuggestions: StateFlow<List<String>> = _autoCompleteSuggestions.asStateFlow()

    private var suggestionsJob: Job? = null

    var searchText by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            _wordsOfTheDay.value = dictionaryDataSource.getWordsOfTheDay()
            onListTypeChanged(ListType.HOME)
        }
    }

    private fun getAutoCompleteSuggestions(text: String) {
        viewModelScope.launch {
            _autoCompleteSuggestions.value =
                dictionaryDataSource.getAutocompleteSuggestions(text)
        }
    }

    /**
     * Show the autocomplete options when the search text is updated
     */
    fun onSearchTextChanged(value: String) {
        searchText = value
        suggestionsJob?.cancel()
        suggestionsJob = viewModelScope.launch {
            if (searchText.isNotBlank()) {
                delay(1000)
                getAutoCompleteSuggestions(searchText)
            } else {
                _autoCompleteSuggestions.value = emptyList()
            }
        }
    }

    fun onListTypeChanged(value: ListType) {
        viewModelScope.launch {
            _listType.value = value
            displayList = when (value) {
                ListType.HOME -> _wordsOfTheDay.value
                ListType.SEARCH -> dictionaryDataSource.getDefinitions(searchText)
                ListType.RANDOM -> dictionaryDataSource.getRandomWords()
                ListType.BOOKMARKS -> emptyList()
            }
        }
    }

    override fun onCleared() {
        suggestionsJob?.cancel()
        suggestionsJob = null
        super.onCleared()
    }
}

enum class ListType {
    HOME, SEARCH, RANDOM, BOOKMARKS
}