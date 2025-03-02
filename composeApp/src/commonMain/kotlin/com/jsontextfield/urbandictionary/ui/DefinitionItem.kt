package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jsontextfield.urbandictionary.network.model.Definition
import com.jsontextfield.urbandictionary.util.toAnnotatedString

@Composable
fun DefinitionItem(definition: Definition) {
    Column(
        modifier = Modifier.padding(24.dp).widthIn(max = 400.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row {
            Text(
                definition.word,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = {}) {
                Icon(Icons.Rounded.Star, contentDescription = null)
            }
        }
        Text(definition.definition.toAnnotatedString(), style = MaterialTheme.typography.bodyMedium)
        Text(
            definition.example.toAnnotatedString(),
            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                definition.author,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                definition.writtenOnDate,
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(Icons.Rounded.ThumbUp, contentDescription = null)
                Text(definition.thumbsUp.toString())
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.Rounded.ThumbUp,
                    contentDescription = null,
                    modifier = Modifier.rotate(180f)
                )
                Text(definition.thumbsDown.toString())
            }
        }
    }
}