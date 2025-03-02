package com.jsontextfield.urbandictionary.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

fun String.toAnnotatedString(): AnnotatedString {
    val regex = "\\[(.*?)\\]".toRegex()
    val matches = regex.findAll(this)
    return buildAnnotatedString {
        var lastIndex = 0
        for (match in matches) {
            append(this@toAnnotatedString.substring(lastIndex, match.range.first))
            withStyle(style = SpanStyle(color = Color.Cyan, textDecoration = TextDecoration.Underline)) {
                pushStringAnnotation(tag = "URL", annotation = match.groupValues[1])
                append(match.groupValues[1])
                pop()
            }
            lastIndex = match.range.last + 1
        }
        append(this@toAnnotatedString.substring(lastIndex))
    }
}