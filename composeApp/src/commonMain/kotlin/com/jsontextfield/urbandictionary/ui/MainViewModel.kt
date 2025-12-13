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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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
        preferencesRepository.getBookmarks().map { bookmarks ->
            _displayList.value = displayList.value.map {
                it.copy(isBookmarked = it.defid in bookmarks)
            }
        }.launchIn(viewModelScope)
        onListTypeChanged(ListType.HOME)
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
            val bookmarks = preferencesRepository.getBookmarks().first()
            _listType.value = value
            _displayList.value = when (value) {
                ListType.HOME -> dictionaryDataSource.getWordsOfTheDay(0)
                ListType.SEARCH -> dictionaryDataSource.getDefinitions(
                    searchText.text.lowercase(), 0
                )

                ListType.RANDOM -> dictionaryDataSource.getRandomWords()
                ListType.BOOKMARKS -> bookmarks.flatMap {
                    dictionaryDataSource.getDefinition(it)
                }.toList()
            }.map {
                it.copy(isBookmarked = it.defid in bookmarks)
            }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            delay(400)
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

    fun onBookmark(id: String) {
        viewModelScope.launch {
            preferencesRepository.setBookmarks(id)
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