package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = ZenithPrimary,
    onPrimary = ZenithOnPrimary,
    primaryContainer = ZenithPrimaryContainer,
    onPrimaryContainer = ZenithOnPrimaryContainer,
    secondary = ZenithSecondary,
    onSecondary = ZenithOnSecondary,
    secondaryContainer = ZenithSecondaryContainer,
    onSecondaryContainer = ZenithOnSecondaryContainer,
    tertiary = ZenithTertiary,
    onTertiary = ZenithOnTertiary,
    background = ZenithBackground,
    onBackground = ZenithOnSurface,
    surface = ZenithSurface,
    onSurface = ZenithOnSurface,
    surfaceVariant = ZenithSurfaceContainerHigh,
    onSurfaceVariant = ZenithOnSurfaceVariant,
    outline = ZenithOutline,
    outlineVariant = ZenithOutlineVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = ZenithPrimaryFixed,
    onPrimary = ZenithOnPrimaryFixed,
    primaryContainer = ZenithPrimaryContainer,
    onPrimaryContainer = ZenithOnPrimaryContainer,
    secondary = ZenithSecondaryFixed,
    onSecondary = ZenithOnSecondaryFixedVariant,
    secondaryContainer = ZenithSecondary,
    onSecondaryContainer = ZenithSecondaryContainer,
    tertiary = ZenithTertiaryFixed,
    onTertiary = ZenithOnTertiaryFixed,
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF1F5F9),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF94A3B8),
    outlineVariant = Color(0xFF475569)
)

@Composable
fun ZenithTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    ZenithTheme(darkTheme = darkTheme, content = content)
}

