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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.jsontextfield.departurescreen.di.viewModelModule
import com.jsontextfield.urbandictionary.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MyApplicationTheme {
        val mainViewModel = koinViewModel<MainViewModel>()
        val listType by mainViewModel.listType.collectAsState()
        val autoCompleteSuggestions by mainViewModel.autoCompleteSuggestions.collectAsState()
        Scaffold(topBar = {
            TopAppBar(navigationIcon = {
                if (listType != ListType.HOME) {
                    IconButton(onClick = {
                        mainViewModel.onListTypeChanged(ListType.HOME)
                    }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, null)
                    }
                }
            }, title = {
                when (listType) {
                    ListType.HOME, ListType.SEARCH -> {
                        if (mainViewModel.searchText.isNotEmpty() && autoCompleteSuggestions.size > 1) {
                            var expanded by remember { mutableStateOf(true) }
                            LaunchedEffect(mainViewModel.searchText) {
                                expanded = mainViewModel.searchText !in autoCompleteSuggestions
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {},
                                properties = PopupProperties(focusable = false)
                            ) {
                                autoCompleteSuggestions.take(12).forEach {
                                    DropdownMenuItem(
                                        text = {
                                            Text(it)
                                        },
                                        onClick = {
                                            mainViewModel.onSearchTextChanged(it)
                                            mainViewModel.onListTypeChanged(ListType.SEARCH)
                                            expanded = false
                                        },
                                    )
                                }
                            }
                        }
                        BasicTextField(
                            value = TextFieldValue(
                                mainViewModel.searchText,
                                TextRange(mainViewModel.searchText.length)
                            ),
                            onValueChange = {
                                mainViewModel.onSearchTextChanged(it.text)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                            keyboardActions = KeyboardActions(onSearch = {
                                mainViewModel.onListTypeChanged(ListType.SEARCH)
                            }),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
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
                                    if (mainViewModel.searchText.isNotEmpty()) {
                                        IconButton(onClick = {
                                            mainViewModel.onSearchTextChanged("")
                                            mainViewModel.onListTypeChanged(ListType.HOME)
                                        }) {
                                            Icon(Icons.Rounded.Clear, null)
                                        }
                                    }
                                }
                            }
                        )
                    }

                    ListType.RANDOM -> {
                        Text("Random words")
                    }

                    ListType.BOOKMARKS -> {
                        Text("Bookmarks")
                    }
                }
            }, actions = {
                if (listType == ListType.HOME || listType == ListType.RANDOM) {
                    IconButton(onClick = {
                        mainViewModel.onListTypeChanged(ListType.RANDOM)
                    }) {
                        Icon(Icons.Rounded.Shuffle, contentDescription = "Random words")
                    }
                }
                if (listType == ListType.HOME) {
                    IconButton(onClick = {
                        mainViewModel.onListTypeChanged(ListType.BOOKMARKS)
                    }) {
                        Icon(Icons.Rounded.Bookmark, contentDescription = "Bookmarks")
                    }
                }
            })
        }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 12.dp)
                    .clip(RoundedCornerShape(12.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(mainViewModel.displayList, key = { item -> item.defid }) { definition ->
                    DefinitionItem(definition, modifier = Modifier.animateItem())
                }
            }
        }
    }
}