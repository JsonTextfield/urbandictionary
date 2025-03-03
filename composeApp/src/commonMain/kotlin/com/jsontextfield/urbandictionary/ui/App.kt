package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.jsontextfield.urbandictionary.ui.theme.MyApplicationTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MyApplicationTheme {
        val mainViewModel = koinViewModel<MainViewModel>()
        val definitions by mainViewModel.wordsOfTheDay.collectAsState()
        Scaffold(topBar = {
            TopAppBar(title = {
                BasicTextField(
                    value = mainViewModel.searchText,
                    onValueChange = mainViewModel::onSearchTextChanged,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.heightIn(min = 56.dp)
                        ) {
                            Icon(Icons.Rounded.Search, contentDescription = null)
                            Box(modifier = Modifier.weight(1f)) {
                                if (mainViewModel.searchText.isEmpty()) {
                                    Text(
                                        "Search",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = .8f
                                            )
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        }
                    }
                )
            }, actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.Shuffle, contentDescription = null)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Rounded.Bookmark, contentDescription = null)
                }
            })
        }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 12.dp).clip(
                    RoundedCornerShape(12.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(definitions) { definition ->
                    DefinitionItem(definition)
                }
            }
        }
    }
}