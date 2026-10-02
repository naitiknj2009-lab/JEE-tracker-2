package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

enum class AppThemeMode {
    DARK,
    LIGHT,
    SYSTEM
}

// Dark Palette (Obsidian Luxury Glass)
val DarkBackgroundCanvas = Color(0xFF070A11)
val DarkSurface = Color(0xFF0D1424)
val DarkSurfaceElevated = Color(0xFF131D31)
val DarkGlassSurface = Color(0x1AFFFFFF) // 10% white
val DarkGlassSurfaceElevated = Color(0x24FFFFFF)
val DarkGlassBorderTop = Color(0x38FFFFFF) // 22% white highlight
val DarkGlassBorderBottom = Color(0x0FFFFFFF) // 6% white
val DarkTextPrimary = Color(0xFFF8FAFC)
val DarkTextSecondary = Color(0xFF94A3B8)
val DarkTextMuted = Color(0xFF64748B)

// Light Palette (Frosted Crisp Crystal)
val LightBackgroundCanvas = Color(0xFFF1F5F9)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceElevated = Color(0xFFF8FAFC)
val LightGlassSurface = Color(0xE6FFFFFF) // 90% white frosted
val LightGlassSurfaceElevated = Color(0xF2FFFFFF)
val LightGlassBorderTop = Color(0xFFE2E8F0)
val LightGlassBorderBottom = Color(0xFFCBD5E1)
val LightTextPrimary = Color(0xFF0F172A)
val LightTextSecondary = Color(0xFF475569)
val LightTextMuted = Color(0xFF94A3B8)

// Subject Accents
val PhysicsEmerald = Color(0xFF10B981)
val PhysicsEmeraldLight = Color(0xFF059669)
val ChemistryAmber = Color(0xFFF59E0B)
val ChemistryAmberLight = Color(0xFFD97706)
val MathCyan = Color(0xFF06B6D4)
val MathCyanLight = Color(0xFF0284C7)

// Brand & Alerts
val ElectricViolet = Color(0xFF8B5CF6)
val ElectricVioletLight = Color(0xFF7C3AED)
val CrimsonNegative = Color(0xFFF43F5E)
val CrimsonNegativeLight = Color(0xFFE11D48)

// Backward compatible aliases
val BackgroundCanvas = DarkBackgroundCanvas
val SurfaceDark = DarkSurface
val SurfaceElevated = DarkSurfaceElevated
val GlassSurface = DarkGlassSurface
val GlassSurfaceElevated = DarkGlassSurfaceElevated
val GlassBorderTop = DarkGlassBorderTop
val GlassBorderBottom = DarkGlassBorderBottom
val TextPrimary = DarkTextPrimary
val TextSecondary = DarkTextSecondary
val TextMuted = DarkTextMuted

data class JeeColors(
    val backgroundCanvas: Color,
    val surface: Color,
    val surfaceElevated: Color,
    val glassSurface: Color,
    val glassSurfaceElevated: Color,
    val glassBorderTop: Color,
    val glassBorderBottom: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val physicsPrimary: Color,
    val physicsSurface: Color,
    val chemistryPrimary: Color,
    val chemistrySurface: Color,
    val mathPrimary: Color,
    val mathSurface: Color,
    val violetPrimary: Color,
    val crimsonPrimary: Color,
    val chipBg: Color,
    val chipBorder: Color,
    val isDark: Boolean
)

val DarkJeeColors = JeeColors(
    backgroundCanvas = DarkBackgroundCanvas,
    surface = DarkSurface,
    surfaceElevated = DarkSurfaceElevated,
    glassSurface = DarkGlassSurface,
    glassSurfaceElevated = DarkGlassSurfaceElevated,
    glassBorderTop = DarkGlassBorderTop,
    glassBorderBottom = DarkGlassBorderBottom,
    textPrimary = DarkTextPrimary,
    textSecondary = DarkTextSecondary,
    textMuted = DarkTextMuted,
    physicsPrimary = PhysicsEmerald,
    physicsSurface = Color(0x2410B981),
    chemistryPrimary = ChemistryAmber,
    chemistrySurface = Color(0x24F59E0B),
    mathPrimary = MathCyan,
    mathSurface = Color(0x2406B6D4),
    violetPrimary = ElectricViolet,
    crimsonPrimary = CrimsonNegative,
    chipBg = Color(0x14FFFFFF),
    chipBorder = Color(0x24FFFFFF),
    isDark = true
)

val LightJeeColors = JeeColors(
    backgroundCanvas = LightBackgroundCanvas,
    surface = LightSurface,
    surfaceElevated = LightSurfaceElevated,
    glassSurface = LightGlassSurface,
    glassSurfaceElevated = LightGlassSurfaceElevated,
    glassBorderTop = LightGlassBorderTop,
    glassBorderBottom = LightGlassBorderBottom,
    textPrimary = LightTextPrimary,
    textSecondary = LightTextSecondary,
    textMuted = LightTextMuted,
    physicsPrimary = PhysicsEmeraldLight,
    physicsSurface = Color(0x1E059669),
    chemistryPrimary = ChemistryAmberLight,
    chemistrySurface = Color(0x1ED97706),
    mathPrimary = MathCyanLight,
    mathSurface = Color(0x1E0284C7),
    violetPrimary = ElectricVioletLight,
    crimsonPrimary = CrimsonNegativeLight,
    chipBg = Color(0xFFF1F5F9),
    chipBorder = Color(0xFFE2E8F0),
    isDark = false
)

val LocalJeeColors = staticCompositionLocalOf { DarkJeeColors }

object JeeTheme {
    val colors: JeeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalJeeColors.current
}
