@file:OptIn(ExperimentalSerializationApi::class)

package com.jsontextfield.urbandictionary.network.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class UrbanDictionaryResponse(
    @JsonNames("list") val definitions: List<Definition>
)

@Serializable
data class Definition(
    val defid: Int,
    val word: String,
    val definition: String,
    val author: String,
    val date: String = "",
    val permalink: String,
    val example: String,
    @JsonNames("current_vote") val currentVote: String,
    @JsonNames("written_on") val writtenOn: String,
    @JsonNames("thumbs_up") val thumbsUp: Int,
    @JsonNames("thumbs_down") val thumbsDown: Int
){
    @OptIn(FormatStringsInDatetimeFormats::class)
    val writtenOnDate: String
        get() {
        val inputFormatter = LocalDateTime.Format{
            byUnicodePattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        }
        val outputFormatter = LocalDateTime.Format{
            byUnicodePattern("dd-MM-yyyy")
        }
        val date = inputFormatter.parse(writtenOn)
        return date.format(outputFormatter)
    }

}
