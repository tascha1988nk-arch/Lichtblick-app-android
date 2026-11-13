package de.lichtblickraum.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val colors = lightColorScheme(
    primary = LichtblickColors.GoldDark,
    onPrimary = LichtblickColors.OnGold,
    primaryContainer = LichtblickColors.GoldSoft,
    onPrimaryContainer = LichtblickColors.Text,
    secondary = LichtblickColors.Gold,
    onSecondary = LichtblickColors.Text,
    secondaryContainer = LichtblickColors.CreamDeep,
    onSecondaryContainer = LichtblickColors.Text,
    background = LichtblickColors.Cream,
    onBackground = LichtblickColors.Text,
    surface = LichtblickColors.Cream,
    onSurface = LichtblickColors.Text,
    surfaceVariant = LichtblickColors.CreamDeep,
    onSurfaceVariant = LichtblickColors.TextMuted,
    surfaceContainer = LichtblickColors.CreamLight,
    surfaceContainerLow = LichtblickColors.CreamLight,
    surfaceContainerHigh = LichtblickColors.CreamDeep,
    outline = LichtblickColors.Gold,
    outlineVariant = LichtblickColors.GoldSoft,
    error = LichtblickColors.Error,
)

// Alle Größen in "sp": Sie wachsen mit der Systemschriftgröße mit.
private val serif = FontFamily.Serif
private val sans = FontFamily.SansSerif

private val typography = Typography(
    displaySmall = TextStyle(fontFamily = serif, fontWeight = FontWeight.Normal, fontSize = 32.sp, lineHeight = 40.sp),
    headlineMedium = TextStyle(fontFamily = serif, fontWeight = FontWeight.Normal, fontSize = 27.sp, lineHeight = 34.sp),
    headlineSmall = TextStyle(fontFamily = serif, fontWeight = FontWeight.Normal, fontSize = 23.sp, lineHeight = 30.sp),
    titleLarge = TextStyle(fontFamily = serif, fontWeight = FontWeight.Medium, fontSize = 21.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontFamily = sans, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, lineHeight = 24.sp),
    bodyLarge = TextStyle(fontFamily = sans, fontSize = 17.sp, lineHeight = 26.sp),
    bodyMedium = TextStyle(fontFamily = sans, fontSize = 15.sp, lineHeight = 22.sp),
    labelLarge = TextStyle(fontFamily = sans, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 22.sp),
    labelMedium = TextStyle(fontFamily = sans, fontWeight = FontWeight.Medium, fontSize = 13.sp, lineHeight = 18.sp),
)

private val shapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
)

@Composable
fun LichtblickTheme(content: @Composable () -> Unit) {
    // Bewusst immer hell und warm – auch im Dunkelmodus des Systems.
    MaterialTheme(colorScheme = colors, typography = typography, shapes = shapes, content = content)
}
