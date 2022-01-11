package ru.konohovalex.core.design

import androidx.compose.ui.graphics.Color

// tbd remove defaults and create concrete Palette in app module
data class Palette(
    val backgroundColor: Color,
    val accentColor: Color,
    val fillEnabledColor: Color,
    val fillDisabledColor: Color,
    val iconTintColor: Color,
    val errorColor: Color,
    val titleColor: Color,
    val bodyColor: Color,
    val labelColor: Color,
)

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
