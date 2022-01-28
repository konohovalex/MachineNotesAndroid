package ru.konohovalex.core.design.extension

import androidx.compose.foundation.layout.PaddingValues
import ru.konohovalex.core.design.model.Paddings

fun Paddings.buttonPaddingsAsPaddingValues() = PaddingValues(
    horizontal = buttonHorizontal,
    vertical = buttonVertical,
)
