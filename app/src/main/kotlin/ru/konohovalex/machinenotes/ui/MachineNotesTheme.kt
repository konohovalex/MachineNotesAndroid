package ru.konohovalex.machinenotes.ui

import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import ru.konohovalex.core.design.LocalPalette
import ru.konohovalex.core.design.LocalShapes
import ru.konohovalex.core.design.LocalTypography
import ru.konohovalex.core.design.Palette

object MachineNotesTheme {
    val palette: Palette
        @Composable
        get() = LocalPalette.current

    val typography: Typography
        @Composable
        get() = LocalTypography.current

    val shapes: Shapes
        @Composable
        get() = LocalShapes.current
}
