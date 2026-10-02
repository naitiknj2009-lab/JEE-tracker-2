package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val JeeDarkColorScheme = darkColorScheme(
    primary = ElectricViolet,
    onPrimary = DarkTextPrimary,
    primaryContainer = Color(0xFF2E1A47),
    onPrimaryContainer = Color(0xFFDDD6FE),
    secondary = MathCyan,
    onSecondary = DarkTextPrimary,
    secondaryContainer = Color(0xFF0E3A42),
    tertiary = PhysicsEmerald,
    background = DarkBackgroundCanvas,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceElevated,
    onSurfaceVariant = DarkTextSecondary,
    error = CrimsonNegative,
    onError = DarkTextPrimary
)

private val JeeLightColorScheme = lightColorScheme(
    primary = ElectricVioletLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE9FE),
    onPrimaryContainer = Color(0xFF4C1D95),
    secondary = MathCyanLight,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F2FE),
    tertiary = PhysicsEmeraldLight,
    background = LightBackgroundCanvas,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceElevated,
    onSurfaceVariant = LightTextSecondary,
    error = CrimsonNegativeLight,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    themeMode: AppThemeMode = AppThemeMode.DARK,
    content: @Composable () -> Unit
) {
    val systemDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        AppThemeMode.DARK -> true
        AppThemeMode.LIGHT -> false
        AppThemeMode.SYSTEM -> systemDark
    }

    val colorScheme = if (isDark) JeeDarkColorScheme else JeeLightColorScheme
    val customColors = if (isDark) DarkJeeColors else LightJeeColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = customColors.backgroundCanvas.toArgb()
                window.navigationBarColor = customColors.backgroundCanvas.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !isDark
            }
        }
    }

    CompositionLocalProvider(LocalJeeColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
