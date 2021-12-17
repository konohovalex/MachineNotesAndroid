package com.owlmanners.machinenotes.ui

import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import com.owlmanners.design.LocalPalette
import com.owlmanners.design.LocalShapes
import com.owlmanners.design.LocalTypography
import com.owlmanners.design.Palette

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
