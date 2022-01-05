package com.himbrhms.checkapp.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.himbrhms.checkapp.ui.util.LightDesertSand

private val DarkColorPalette = darkColors(
    primary = Color.LightDesertSand,
    primaryVariant = Color.Red,
    secondary = Color.Yellow
)

private val LightColorPalette = lightColors(
    primary = Color.LightDesertSand,
    primaryVariant = Color.Red,
    secondary = Color.Yellow,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CheckAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
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