package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = BiharBlue,
    onPrimary = Color.White,
    primaryContainer = BiharSky,
    onPrimaryContainer = BiharBlue,
    secondary = BiharNavyDark,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDCE8F5),
    onSecondaryContainer = BiharNavyDark,
    tertiary = TricolorGreen,
    onTertiary = Color.White,
    tertiaryContainer = GreenSurface,
    onTertiaryContainer = GreenText,
    background = BiharBackground,
    onBackground = BiharTextDark,
    surface = BiharSurface,
    onSurface = BiharTextDark,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = BiharTextMid,
    outline = BiharBorder,
    outlineVariant = Color(0xFFCBD5E1)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF60A5FA),
    onPrimary = Color(0xFF0F172A),
    primaryContainer = Color(0xFF1E3A8A),
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = Color(0xFF93C5FD),
    onSecondary = Color(0xFF0F172A),
    tertiary = Color(0xFF4ADE80),
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onBackground = Color(0xFFF8FAFC),
    onSurface = Color(0xFFF8FAFC)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep Bihar Govt branding consistent by default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
