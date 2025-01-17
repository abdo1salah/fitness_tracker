package com.example.weatherapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = White,
    primaryVariant = LightGray,
    secondary = Yellow,
    onSecondary = Black,
    background = LightBlack,
    surface = DarkGray,
    onPrimary = LightBlack,
    onSurface = LightGray,
    onBackground = LightBlack
)

private val LightColorPalette = lightColors(
    primary = Black,
    primaryVariant = Gray,
    secondary = Purple,
    onSecondary = White,
    background = White,
    surface = OffWhite,
    onSurface = HighBlack,
    onBackground = OffWhite



)

@Composable
fun WeatherAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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