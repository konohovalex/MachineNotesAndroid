package ru.konohovalex.core.design.extension

import androidx.compose.material.Shapes
import androidx.compose.runtime.staticCompositionLocalOf
import ru.konohovalex.core.design.model.MaterialElevations
import ru.konohovalex.core.design.model.Paddings
import ru.konohovalex.core.design.model.Palette
import ru.konohovalex.core.design.model.Sizes
import ru.konohovalex.core.design.model.Typography

internal val LocalPalette = staticCompositionLocalOf<Palette> {
    error("No colors provided")
}

internal val LocalTypography = staticCompositionLocalOf<Typography> {
    error("No typography provided")
}

internal val LocalShapes = staticCompositionLocalOf<Shapes> {
    error("No shapes provided")
}

internal val LocalMaterialElevations = staticCompositionLocalOf<MaterialElevations> {
    error("No elevations provided")
}

internal val LocalPaddings = staticCompositionLocalOf<Paddings> {
    error("No paddings provided")
}

internal val LocalSizes = staticCompositionLocalOf<Sizes> {
    error("No paddings provided")
}
