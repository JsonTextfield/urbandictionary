package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AutocompleteSuggestions(
    suggestions: List<String>,
    modifier: Modifier = Modifier,
    onSuggestionSelected: (String) -> Unit = {},
) {
    LazyColumn(modifier = modifier) {
        items(suggestions, key = { it }) { suggestion ->
            Text(
                suggestion,
                modifier = Modifier
                    .clickable { onSuggestionSelected(suggestion) }
                    .padding(20.dp)
                    .fillMaxWidth(),
            )
        }
    }
}