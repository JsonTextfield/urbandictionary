package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.jsontextfield.urbandictionary.ui.theme.MyApplicationTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import urbandictionary.composeapp.generated.resources.Res
import urbandictionary.composeapp.generated.resources.back
import urbandictionary.composeapp.generated.resources.bookmarks
import urbandictionary.composeapp.generated.resources.random_words

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MyApplicationTheme {
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        val mainViewModel: MainViewModel = koinViewModel()
        val listType by mainViewModel.listType.collectAsState()
        val displayList by mainViewModel.displayList.collectAsState(emptyList())
        val autoCompleteSuggestions by mainViewModel.autoCompleteSuggestions.collectAsState()
        val listState = rememberLazyListState()
        LaunchedEffect(listType) {
            listState.scrollToItem(0)
        }
        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastIndex ->
                    if (lastIndex == displayList.lastIndex) {
                        mainViewModel.loadMore()
                    }
                }
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        if (listType != ListType.HOME) {
                            IconButton(onClick = {
                                mainViewModel.onListTypeChanged(ListType.HOME)
                            }) {
                                Icon(
                                    Icons.AutoMirrored.Rounded.ArrowBack,
                                    contentDescription = stringResource(Res.string.back)
                                )
                            }
                        }
                    },
                    title = {
                        when (listType) {
                            ListType.HOME, ListType.SEARCH -> {
                                SearchBar(
                                    value = mainViewModel.searchText,
                                    onValueChanged = mainViewModel::onSearchTextChanged,
                                    onSearch = {
                                        mainViewModel.onListTypeChanged(ListType.SEARCH)
                                    },
                                    onTextCleared = { mainViewModel.onListTypeChanged(ListType.HOME) },
                                    modifier = Modifier.focusRequester(focusRequester)
                                )
                            }

                            ListType.RANDOM -> {
                                Text(stringResource(Res.string.random_words))
                            }

                            ListType.BOOKMARKS -> {
                                Text(stringResource(Res.string.bookmarks))
                            }
                        }
                    },
                    actions = {
                        if (listType == ListType.HOME) {
                            IconButton(onClick = {
                                mainViewModel.onListTypeChanged(ListType.BOOKMARKS)
                            }) {
                                Icon(
                                    Icons.Rounded.Bookmark,
                                    contentDescription = stringResource(Res.string.bookmarks)
                                )
                            }
                        }
                        if (listType == ListType.HOME || listType == ListType.RANDOM) {
                            IconButton(onClick = {
                                mainViewModel.onListTypeChanged(ListType.RANDOM)
                            }) {
                                Icon(
                                    Icons.Rounded.Shuffle,
                                    contentDescription = stringResource(Res.string.random_words)
                                )
                            }
                        }
                    },
                )
            },
        ) {
            DefinitionsList(
                definitions = displayList,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                listState = listState,
                onTextClick = { text ->
                    mainViewModel.onAutoCompleteSuggestionSelected(text)
                },
                onBookmarkPressed = mainViewModel::onBookmark,
            )

            if (autoCompleteSuggestions.size > 1) {
                Surface {
                    AutocompleteSuggestions(
                        suggestions = autoCompleteSuggestions,
                        onSuggestionSelected = { suggestion ->
                            mainViewModel.onAutoCompleteSuggestionSelected(suggestion)
                            focusManager.clearFocus()
                        },
                        modifier = Modifier
                            .padding(it)
                            .imePadding()
                    )
                }
            }
        }
    }
}