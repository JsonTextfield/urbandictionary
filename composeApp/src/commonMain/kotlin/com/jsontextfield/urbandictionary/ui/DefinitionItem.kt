package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jsontextfield.urbandictionary.network.model.Definition
import com.jsontextfield.urbandictionary.util.toAnnotatedString
import org.jetbrains.compose.resources.stringResource
import urbandictionary.composeapp.generated.resources.Res
import urbandictionary.composeapp.generated.resources.bookmark

@Composable
fun DefinitionItem(
    definition: Definition,
    modifier: Modifier = Modifier,
    onBookmarkPressed: () -> Unit = {},
    onClick: () -> Unit = {},
    onTextClick: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .widthIn(max = 400.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onSecondary)
            .clickable(onClick = onClick)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                definition.word,
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onBookmarkPressed) {
                Icon(
                    if (definition.isBookmarked) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                    contentDescription = stringResource(Res.string.bookmark)
                )
            }
        }
        SelectionContainer {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    definition.definition.toAnnotatedString(
                        MaterialTheme.colorScheme.primary,
                        onLinkClicked = onTextClick
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    definition.example.toAnnotatedString(
                        MaterialTheme.colorScheme.primary,
                        onLinkClicked = onTextClick
                    ),
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        definition.author,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    definition.writtenOnDate?.let {
                        Text(
                            it,
                            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
                        )
                    }
                }
            }
        }
        RatingSection(
            definition.thumbsUp.toString(),
            definition.thumbsDown.toString(),
        )
    }
}