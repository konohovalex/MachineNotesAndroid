package ru.konohovalex.core.design.extension

import androidx.compose.runtime.Composable
import ru.konohovalex.core.design.model.Palette
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.design.model.gray100
import ru.konohovalex.core.design.model.gray200
import ru.konohovalex.core.design.model.gray300
import ru.konohovalex.core.design.model.gray400
import ru.konohovalex.core.design.model.gray500
import ru.konohovalex.core.design.model.gray600
import ru.konohovalex.core.design.model.gray700
import ru.konohovalex.core.design.model.gray800
import ru.konohovalex.core.design.model.gray900
import ru.konohovalex.core.design.model.lightGreen400
import ru.konohovalex.core.design.model.lightGreen700
import ru.konohovalex.core.design.model.red700

val lightPalette = Palette(
    backgroundColor = gray200,
    accentColor = lightGreen700,
    fillEnabledColor = lightGreen400,
    fillDisabledColor = gray500,
    iconTintColor = gray900,
    errorColor = red700,
    titleColor = gray900,
    bodyColor = gray700,
    labelColor = gray600,
)

val darkPalette = Palette(
    backgroundColor = gray800,
    accentColor = lightGreen700,
    fillEnabledColor = lightGreen400,
    fillDisabledColor = gray500,
    iconTintColor = gray100,
    errorColor = red700,
    titleColor = gray100,
    bodyColor = gray300,
    labelColor = gray400,
)

@Composable
fun getFillColor(isEnabled: Boolean) =
    if (isEnabled) Theme.palette.fillEnabledColor
    else Theme.palette.fillDisabledColor
