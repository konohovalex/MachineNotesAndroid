package ru.konohovalex.core.design.model

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.konohovalex.core.design.extension.LocalMaterialElevations
import ru.konohovalex.core.design.extension.LocalPaddings
import ru.konohovalex.core.design.extension.LocalPalette
import ru.konohovalex.core.design.extension.LocalShapes
import ru.konohovalex.core.design.extension.LocalSizes
import ru.konohovalex.core.design.extension.LocalTypography
import ru.konohovalex.core.design.extension.darkPalette
import ru.konohovalex.core.design.extension.lightPalette
import ru.konohovalex.core.design.extension.roundedCornerShapes

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

    val sizes: Sizes
        @Composable
        get() = LocalSizes.current
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
    val sizes = Sizes()

    CompositionLocalProvider(
        LocalPalette provides palette,
        LocalTypography provides typography,
        LocalShapes provides shapes,
        LocalMaterialElevations provides materialElevations,
        LocalPaddings provides paddings,
        LocalSizes provides sizes,
        content = content
    )
}
