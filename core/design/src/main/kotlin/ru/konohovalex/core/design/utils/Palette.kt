package ru.konohovalex.core.design.utils

import androidx.compose.runtime.Composable
import ru.konohovalex.core.design.Theme

@Composable
fun getFillColor(isEnabled: Boolean) =
    if (isEnabled) Theme.palette.fillEnabledColor
    else Theme.palette.fillDisabledColor
