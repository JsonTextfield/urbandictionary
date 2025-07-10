package com.jsontextfield.urbandictionary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.Font
import urbandictionary.composeapp.generated.resources.Res
import urbandictionary.composeapp.generated.resources.lora_bold
import urbandictionary.composeapp.generated.resources.lora_bold_italic
import urbandictionary.composeapp.generated.resources.lora_italic
import urbandictionary.composeapp.generated.resources.lora_regular
import urbandictionary.composeapp.generated.resources.source_sans_pro_bold
import urbandictionary.composeapp.generated.resources.source_sans_pro_bold_italic
import urbandictionary.composeapp.generated.resources.source_sans_pro_italic
import urbandictionary.composeapp.generated.resources.source_sans_pro_regular

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = primaryDark,
            onPrimary = onPrimaryDark,
            primaryContainer = primaryContainerDark,
            onPrimaryContainer = onPrimaryContainerDark,
            secondary = secondaryDark,
            onSecondary = onSecondaryDark,
            secondaryContainer = secondaryContainerDark,
            onSecondaryContainer = onSecondaryContainerDark,
            tertiary = tertiaryDark,
            onTertiary = onTertiaryDark,
            tertiaryContainer = tertiaryContainerDark,
            onTertiaryContainer = onTertiaryContainerDark,
            error = errorDark,
            onError = onErrorDark,
            errorContainer = errorContainerDark,
            onErrorContainer = onErrorContainerDark,
            background = backgroundDark,
            onBackground = onBackgroundDark,
            surface = surfaceDark,
            onSurface = onSurfaceDark,
            surfaceVariant = surfaceVariantDark,
            onSurfaceVariant = onSurfaceVariantDark,
            outline = outlineDark,
            outlineVariant = outlineVariantDark,
            scrim = scrimDark,
            inverseSurface = inverseSurfaceDark,
            inverseOnSurface = inverseOnSurfaceDark,
            inversePrimary = inversePrimaryDark,
            surfaceDim = surfaceDimDark,
            surfaceBright = surfaceBrightDark,
            surfaceContainerLowest = surfaceContainerLowestDark,
            surfaceContainerLow = surfaceContainerLowDark,
            surfaceContainer = surfaceContainerDark,
            surfaceContainerHigh = surfaceContainerHighDark,
            surfaceContainerHighest = surfaceContainerHighestDark,
        )
    } else {
        lightColorScheme(
            primary = primaryLight,
            onPrimary = onPrimaryLight,
            primaryContainer = primaryContainerLight,
            onPrimaryContainer = onPrimaryContainerLight,
            secondary = secondaryLight,
            onSecondary = onSecondaryLight,
            secondaryContainer = secondaryContainerLight,
            onSecondaryContainer = onSecondaryContainerLight,
            tertiary = tertiaryLight,
            onTertiary = onTertiaryLight,
            tertiaryContainer = tertiaryContainerLight,
            onTertiaryContainer = onTertiaryContainerLight,
            error = errorLight,
            onError = onErrorLight,
            errorContainer = errorContainerLight,
            onErrorContainer = onErrorContainerLight,
            background = backgroundLight,
            onBackground = onBackgroundLight,
            surface = surfaceLight,
            onSurface = onSurfaceLight,
            surfaceVariant = surfaceVariantLight,
            onSurfaceVariant = onSurfaceVariantLight,
            outline = outlineLight,
            outlineVariant = outlineVariantLight,
            scrim = scrimLight,
            inverseSurface = inverseSurfaceLight,
            inverseOnSurface = inverseOnSurfaceLight,
            inversePrimary = inversePrimaryLight,
            surfaceDim = surfaceDimLight,
            surfaceBright = surfaceBrightLight,
            surfaceContainerLowest = surfaceContainerLowestLight,
            surfaceContainerLow = surfaceContainerLowLight,
            surfaceContainer = surfaceContainerLight,
            surfaceContainerHigh = surfaceContainerHighLight,
            surfaceContainerHighest = surfaceContainerHighestLight,
        )
    }
    val typography = Typography(
        headlineLarge = MaterialTheme.typography.headlineLarge.copy(
            fontFamily = FontFamily(
                Font(
                    Res.font.lora_regular,
                    weight = FontWeight.Normal,
                    style = FontStyle.Normal,
                ),
                Font(
                    Res.font.lora_bold,
                    weight = FontWeight.Bold,
                    style = FontStyle.Normal,
                ),
                Font(
                    Res.font.lora_italic,
                    weight = FontWeight.Normal,
                    style = FontStyle.Italic,
                ),
                Font(
                    Res.font.lora_bold_italic,
                    weight = FontWeight.Bold,
                    style = FontStyle.Italic,
                ),
            ),
        ),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = FontFamily(
                Font(
                    Res.font.source_sans_pro_regular,
                    weight = FontWeight.Normal,
                    style = FontStyle.Normal,
                ),
                Font(
                    Res.font.source_sans_pro_bold,
                    weight = FontWeight.Bold,
                    style = FontStyle.Normal,
                ),
                Font(
                    Res.font.source_sans_pro_italic,
                    weight = FontWeight.Normal,
                    style = FontStyle.Italic,
                ),
                Font(
                    Res.font.source_sans_pro_bold_italic,
                    weight = FontWeight.Bold,
                    style = FontStyle.Italic,
                ),
            ),
        ),
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
