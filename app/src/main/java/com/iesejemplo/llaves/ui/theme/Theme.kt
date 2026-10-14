package com.iesejemplo.llaves.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores sacados del diseño
val Navy = Color(0xFF153E75)
val Orange = Color(0xFFF28C28)
val Background = Color(0xFFF2F5FA)
val InkDark = Color(0xFF0F1B33)
val InkMuted = Color(0xFF5B6B82)
val Outline = Color(0xFFD5DCE8)
val GreenBg = Color(0xFFDDF0E4)
val GreenText = Color(0xFF1B6B3A)
val RedBg = Color(0xFFF8E0DB)
val RedText = Color(0xFF9B2C1F)

private val Scheme = lightColorScheme(
    primary = Navy,
    onPrimary = Color.White,
    secondary = Orange,
    onSecondary = InkDark,
    background = Background,
    surface = Color.White,
    onSurface = InkDark,
    outline = Outline,
)

private val AppTypography = Typography(
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 26.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 17.sp),
    bodyLarge = TextStyle(fontSize = 16.sp),
    bodyMedium = TextStyle(fontSize = 14.sp),
    bodySmall = TextStyle(fontSize = 12.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
)

private val AppShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
)

@Composable
fun AppLlavesTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = Scheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
