package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jsontextfield.urbandictionary.network.model.Definition

@Composable
fun DefinitionsList(
    definitions: List<Definition>,
    modifier: Modifier = Modifier,
    state: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    onBookmarkPressed: (String) -> Unit = {},
    onTextClick: (String) -> Unit = {},
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(320.dp),
        state = state,
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 200.dp,
        ),
        modifier = modifier
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(12.dp)),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp,
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