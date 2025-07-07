package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jsontextfield.urbandictionary.network.model.Definition

@Composable
fun DefinitionsList(
    definitions: List<Definition>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onBookmarkPressed: (Int) -> Unit = {},
    onTextClick: (String) -> Unit = {},
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 200.dp,
        ),
        modifier = modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(definitions, key = { item -> item.defid }) { definition ->
            DefinitionItem(
                definition,
                modifier = Modifier.animateItem(),
                onBookmarkPressed = { onBookmarkPressed(definition.defid) },
                onClick = { onTextClick(definition.word) },
                onTextClick = onTextClick,
            )
        }
    }
}