package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import urbandictionary.composeapp.generated.resources.Res
import urbandictionary.composeapp.generated.resources.thumbs_down
import urbandictionary.composeapp.generated.resources.thumbs_up

@Composable
fun RatingSection(
    upvotes: String,
    downvotes: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                Icons.Rounded.ThumbUp,
                contentDescription = stringResource(Res.string.thumbs_up)
            )
            Text(upvotes)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                Icons.Rounded.ThumbUp,
                contentDescription = stringResource(Res.string.thumbs_down),
                modifier = Modifier.rotate(180f)
            )
            Text(downvotes)
        }
    }
}