package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val mainViewModel = koinViewModel<MainViewModel>()
        val definitions by mainViewModel.wordsOfTheDay.collectAsState()
        Scaffold {
            LazyColumn() {
                items(definitions) { definition ->
                    DefinitionItem(definition)
                }
            }
        }
    }
}