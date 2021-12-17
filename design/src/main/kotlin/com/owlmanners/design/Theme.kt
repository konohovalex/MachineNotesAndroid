package com.owlmanners.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val palette = if (darkTheme) darkPalette else lightPalette

    CompositionLocalProvider(
        LocalPalette provides palette,
        LocalTypography provides typography,
        LocalShapes provides shapes,
        content = content
    )
}

val LocalPalette = staticCompositionLocalOf<Palette> {
    error("No colors provided")
}

val LocalTypography = staticCompositionLocalOf<Typography> {
    error("No typography provided")
}

val LocalShapes = staticCompositionLocalOf<Shapes> {
    error("No shapes provided")
}
