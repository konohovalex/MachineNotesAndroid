package ru.konohovalex.core.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

// tbd Should be interface, implement in app module
object Theme {
    val palette: Palette
        @Composable
        get() = LocalPalette.current

    val textStyles: TextStyles
        @Composable
        get() = LocalTextStyles.current

    val shapes: Shapes
        @Composable
        get() = LocalShapes.current
}

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val palette = if (darkTheme) darkPalette else lightPalette
    val textStyles = TextStyles()
    val shapes = shapes

    CompositionLocalProvider(
        LocalPalette provides palette,
        LocalTextStyles provides textStyles,
        LocalShapes provides shapes,
        content = content
    )
}

val LocalPalette = staticCompositionLocalOf<Palette> {
    error("No colors provided")
}

val LocalTextStyles = staticCompositionLocalOf<TextStyles> {
    error("No typography provided")
}

val LocalShapes = staticCompositionLocalOf<Shapes> {
    error("No shapes provided")
}
