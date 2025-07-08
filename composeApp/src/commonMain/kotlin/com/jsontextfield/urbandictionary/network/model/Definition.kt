@file:OptIn(ExperimentalSerializationApi::class, FormatStringsInDatetimeFormats::class)

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
    @JsonNames("thumbs_down") val thumbsDown: Int,
    val isBookmarked: Boolean = false
) {
    val writtenOnDate: String?
        get() {
            return try {
                (inputFormatter1.parse(writtenOn)).format(outputFormatter)
            } catch (_: Exception) {
                try {
                    (inputFormatter2.parse(writtenOn)).format(outputFormatter)
                } catch (_: Exception) {
                    null
                }
            }
        }

    companion object {
        private val inputFormatter1 = LocalDateTime.Format {
            byUnicodePattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        }
        private val inputFormatter2 = LocalDateTime.Format {
            byUnicodePattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        }
        private val outputFormatter = LocalDateTime.Format {
            byUnicodePattern("dd/MM/yyyy")
        }
    }
}
