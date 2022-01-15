package ru.konohovalex.core.design.utils

import androidx.compose.foundation.layout.PaddingValues
import ru.konohovalex.core.design.Paddings

fun Paddings.buttonPaddingsAsPaddingValues() = PaddingValues(
    horizontal = buttonHorizontal,
    vertical = buttonVertical,
)
