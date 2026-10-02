package com.example.data.model

import androidx.compose.ui.graphics.Color

enum class Subject(
    val displayName: String,
    val primaryColor: Color,
    val accentColor: Color,
    val surfaceColor: Color
) {
    PHYSICS(
        displayName = "Physics",
        primaryColor = Color(0xFF10B981), // Emerald
        accentColor = Color(0xFF34D399),
        surfaceColor = Color(0x1A10B981)
    ),
    CHEMISTRY(
        displayName = "Chemistry",
        primaryColor = Color(0xFFF59E0B), // Amber
        accentColor = Color(0xFFFBBF24),
        surfaceColor = Color(0x1AF59E0B)
    ),
    MATHEMATICS(
        displayName = "Mathematics",
        primaryColor = Color(0xFF06B6D4), // Cyan
        accentColor = Color(0xFF22D3EE),
        surfaceColor = Color(0x1A06B6D4)
    )
}

enum class Weightage(val label: String, val color: Color) {
    HIGH("High / Do-or-Die", Color(0xFFEF4444)),
    MEDIUM("Medium Weightage", Color(0xFFF59E0B)),
    LOW("Standard Weightage", Color(0xFF3B82F6))
}
