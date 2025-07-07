package com.jsontextfield.urbandictionary.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jsontextfield.urbandictionary.data.IPreferencesRepository
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
    private val preferencesRepository: IPreferencesRepository,
) : ViewModel() {

    private val _listType: MutableStateFlow<ListType> = MutableStateFlow(ListType.HOME)
    val listType: StateFlow<ListType> = _listType.asStateFlow()
    private val _displayList: MutableStateFlow<List<Definition>> = MutableStateFlow(emptyList())
    val displayList: StateFlow<List<Definition>> = _displayList.asStateFlow()
    private val _autoCompleteSuggestions: MutableStateFlow<List<String>> =
        MutableStateFlow(emptyList())
    val autoCompleteSuggestions: StateFlow<List<String>> = _autoCompleteSuggestions.asStateFlow()

    private var suggestionsJob: Job? = null

    var searchText by mutableStateOf(TextFieldValue(""))
        private set

    init {
        viewModelScope.launch {
            onListTypeChanged(ListType.HOME)
        }
    }

    fun onSearchTextChanged(value: TextFieldValue) {
        searchText = value
        suggestionsJob?.cancel()
        suggestionsJob = viewModelScope.launch {
            if (searchText.text.isNotBlank()) {
                delay(500)
                _autoCompleteSuggestions.value =
                    dictionaryDataSource.getAutocompleteSuggestions(value.text)
            } else {
                _autoCompleteSuggestions.value = emptyList()
            }
        }
    }

    fun onAutoCompleteSuggestionSelected(value: String) {
        searchText = TextFieldValue(value)
        _autoCompleteSuggestions.value = emptyList()
        onListTypeChanged(ListType.SEARCH)
    }

    fun onListTypeChanged(value: ListType) {
        viewModelScope.launch {
            _listType.value = value
            _displayList.value = when (value) {
                ListType.HOME -> dictionaryDataSource.getWordsOfTheDay(0)
                ListType.SEARCH -> dictionaryDataSource.getDefinitions(
                    searchText.text.lowercase(), 0
                )

                ListType.RANDOM -> dictionaryDataSource.getRandomWords()
                ListType.BOOKMARKS -> preferencesRepository.getBookmarks().flatMap {
                    dictionaryDataSource.getDefinition(it)
                }
            }.map {
                it.copy(isBookmarked = it.defid in preferencesRepository.getBookmarks())
            }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            if (displayList.value.size > 9) {
                val moreDefinitions: List<Definition> = when (listType.value) {
                    ListType.SEARCH -> dictionaryDataSource.getDefinitions(
                        searchText.text.lowercase(), displayList.value.size
                    )

                    ListType.HOME -> dictionaryDataSource.getWordsOfTheDay(displayList.value.size)
                    else -> emptyList()
                }
                _displayList.value = displayList.value + moreDefinitions
            }
        }
    }

    fun onBookmark(id: Int) {
        viewModelScope.launch {
            if (id in preferencesRepository.getBookmarks()) {
                preferencesRepository.removeBookmark(id)
            } else {
                preferencesRepository.addBookmark(id)
            }
            _displayList.value = displayList.value.map {
                if (it.defid == id) {
                    it.copy(isBookmarked = id in preferencesRepository.getBookmarks())
                } else {
                    it
                }
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