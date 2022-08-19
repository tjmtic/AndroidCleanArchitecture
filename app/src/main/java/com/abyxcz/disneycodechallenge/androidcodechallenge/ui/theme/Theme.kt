package com.abyxcz.disneycodechallenge.androidcodechallenge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Purple200,
    //primaryVariant = Purple700,
    //secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = BlueCustom,
    //primaryVariant = Purple700,
    //secondary = Teal200

)

@Composable
fun AndroidCodeChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}