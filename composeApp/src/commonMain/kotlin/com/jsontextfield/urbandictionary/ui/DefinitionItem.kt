package com.jsontextfield.urbandictionary.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jsontextfield.urbandictionary.network.model.Definition

@Composable
fun DefinitionItem(definition: Definition) {
    Card {
        Column {
            Text(definition.word)
            Text(definition.definition)
        }
    }
}