package ru.konohovalex.core.design

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

// tbd remove defaults and create concrete Shapes in app module
val shapes = Shapes(
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(100.dp),
)
