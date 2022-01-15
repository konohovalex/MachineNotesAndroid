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

    val typography: Typography
        @Composable
        get() = LocalTypography.current

    val shapes: Shapes
        @Composable
        get() = LocalShapes.current

    val materialElevations: MaterialElevations
        @Composable
        get() = LocalMaterialElevations.current

    val paddings: Paddings
        @Composable
        get() = LocalPaddings.current
}

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val palette = if (darkTheme) darkPalette else lightPalette
    val typography = Typography()
    val shapes = roundedCornerShapes
    val materialElevations = MaterialElevations()
    val paddings = Paddings()

    CompositionLocalProvider(
        LocalPalette provides palette,
        LocalTypography provides typography,
        LocalShapes provides shapes,
        LocalMaterialElevations provides materialElevations,
        LocalPaddings provides paddings,
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

val LocalMaterialElevations = staticCompositionLocalOf<MaterialElevations> {
    error("No elevations provided")
}

val LocalPaddings = staticCompositionLocalOf<Paddings> {
    error("No paddings provided")
}
