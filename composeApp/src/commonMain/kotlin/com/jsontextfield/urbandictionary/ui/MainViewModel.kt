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

class MainViewModel(private val dictionaryDataSource: IUrbanDictionaryDataSource) : ViewModel() {

    private var _wordsOfTheDay: MutableStateFlow<List<Definition>> = MutableStateFlow(emptyList())
    val wordsOfTheDay: StateFlow<List<Definition>> = _wordsOfTheDay.asStateFlow()

    private var _randomWords: MutableStateFlow<List<Definition>> = MutableStateFlow(emptyList())
    val randomWords: StateFlow<List<Definition>> = _randomWords.asStateFlow()

    private var _autoCompleteSuggestions: MutableStateFlow<List<String>> =
        MutableStateFlow(emptyList())
    val autoCompleteSuggestions: StateFlow<List<String>> = _autoCompleteSuggestions.asStateFlow()

    private var suggestionsJob: Job? = null

    var searchText by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            _wordsOfTheDay.value = dictionaryDataSource.getWordsOfTheDay()
        }
    }

    fun getRandomWords() {
        viewModelScope.launch {
            _randomWords.value = dictionaryDataSource.getRandomWords()
        }
    }

    private fun getAutoCompleteSuggestions(text: String) {
        if (text.isEmpty()) {
            _autoCompleteSuggestions.value = emptyList()
        }
        else {
            viewModelScope.launch {
                _autoCompleteSuggestions.value =
                    dictionaryDataSource.getAutocompleteSuggestions(text)
            }
        }
    }

    fun onSearchTextChanged(value: String) {
        searchText = value
        suggestionsJob?.cancel()
        suggestionsJob = viewModelScope.launch {
            delay(1000)
            getAutoCompleteSuggestions(searchText)
        }
    }

    override fun onCleared() {
        suggestionsJob?.cancel()
        suggestionsJob = null
        super.onCleared()
    }
}