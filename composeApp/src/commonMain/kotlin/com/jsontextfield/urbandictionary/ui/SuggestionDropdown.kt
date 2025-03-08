package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun SuggestionDropdown(
    suggestions: List<String>,
    onSuggestionSelected: (String) -> Unit = {},
) {

    DropdownMenu(
        expanded = suggestions.isNotEmpty(),
        modifier = Modifier
            .width(200.dp)
            .heightIn(max = 200.dp),
        onDismissRequest = { },
        properties = PopupProperties(
            focusable = false,
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
        )
    ) {
        suggestions.forEach {
            DropdownMenuItem(
                text = { Text(it) },
                onClick = { onSuggestionSelected(it) },
            )
        }
    }
}