package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import urbandictionary.composeapp.generated.resources.Res
import urbandictionary.composeapp.generated.resources.clear
import urbandictionary.composeapp.generated.resources.search

@Composable
fun SearchBar(
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    onSearch: () -> Unit,
    onTextCleared: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier
            .widthIn(max = 240.dp)
            .padding(vertical = 12.dp)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(24.dp)
            ),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch()
                defaultKeyboardAction(ImeAction.Done)
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            showKeyboardOnFocus = false
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .heightIn(min = 56.dp)
            ) {
                Icon(Icons.Rounded.Search, contentDescription = null)
                Box(modifier = Modifier.weight(1f)) {
                    if (value.text.isEmpty()) {
                        Text(
                            stringResource(Res.string.search),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(
                                    alpha = .8f
                                )
                            )
                        )
                    }
                    innerTextField()
                }
                if (value.text.isNotEmpty()) {
                    IconButton(onClick = {
                        onValueChanged(TextFieldValue(""))
                        onTextCleared()
                    }) {
                        Icon(
                            Icons.Rounded.Clear,
                            contentDescription = stringResource(Res.string.clear),
                        )
                    }
                }
            }
        }
    )
}