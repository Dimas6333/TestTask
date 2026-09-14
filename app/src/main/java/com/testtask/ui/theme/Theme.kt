package com.testtask.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2E6B4F),
    onPrimary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFF81C784),
    onTertiaryContainer = Color(0xFF06281A),
    surface = Color(0xFFFBFDF9),
    onSurface = Color(0xFF191C1A),
    outlineVariant = Color(0xFFC0C9C2),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8FD3AE),
    onPrimary = Color(0xFF06281A),
    tertiaryContainer = Color(0xFF2F6B4C),
    onTertiaryContainer = Color(0xFFCDEEDA),
    surface = Color(0xFF191C1A),
    onSurface = Color(0xFFE1E3DF),
    outlineVariant = Color(0xFF414942),
)

@Composable
fun TableTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content,
    )
}
