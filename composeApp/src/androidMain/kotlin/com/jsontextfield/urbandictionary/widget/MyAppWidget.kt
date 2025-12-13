package com.jsontextfield.urbandictionary.widget

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextDefaults
import com.jsontextfield.urbandictionary.network.UrbanDictionaryAPI
import com.jsontextfield.urbandictionary.network.model.Definition

class MyAppWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            val api = remember { UrbanDictionaryAPI() }
            var word by remember { mutableStateOf<Definition?>(null) }
            GlanceTheme {
                Scaffold(titleBar = {
//                    TitleBar(
//                        //startIcon = ImageProvider(Icon()),
//                        title = "Word of the day",
//                    )
                    Text(
                        text = "Word of the day",
                        style = TextDefaults.defaultTextStyle.copy(
                            fontWeight = FontWeight.Bold,
                            color = GlanceTheme.colors.onBackground,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                        ),
                        modifier = GlanceModifier.fillMaxWidth().padding(8.dp)
                    )
                }) {
                    LaunchedEffect(Unit) {
                        word = api.getWordsOfTheDay(1).definitions.first()
                    }
                    word?.let { w ->
                        Column() {
                            Text(
                                text = w.word,
                                style = TextDefaults.defaultTextStyle.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GlanceTheme.colors.onBackground,
                                ),
                                modifier = GlanceModifier.padding(vertical = 4.dp)
                            )
                            Text(
                                text = w.definition,
                                style = TextDefaults.defaultTextStyle.copy(
                                    color = GlanceTheme.colors.onBackground,
                                ),
                                maxLines = 10,
                            )
                        }
                    }
                }
            }
        }
    }
}