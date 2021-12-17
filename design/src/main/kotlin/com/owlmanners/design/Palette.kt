package com.owlmanners.design

import androidx.compose.ui.graphics.Color

data class Palette(
    val backgroundColor: Color,
    val accentColor: Color,
    val fillEnabledColor: Color,
    val fillDisabledColor: Color,
    val iconTintColor: Color,
    val errorColor: Color,
    val titleColor: Color,
    val subtitleColor: Color,
    val infoTextColor: Color,
)

val lightPalette = Palette(
    backgroundColor = gray200,
    accentColor = lightGreen700,
    fillEnabledColor = lightGreen400,
    fillDisabledColor = gray500,
    iconTintColor = gray900,
    errorColor = red700,
    titleColor = gray900,
    subtitleColor = gray700,
    infoTextColor = gray600,
)

val darkPalette = Palette(
    backgroundColor = gray800,
    accentColor = lightGreen700,
    fillEnabledColor = lightGreen400,
    fillDisabledColor = gray500,
    iconTintColor = gray100,
    errorColor = red700,
    titleColor = gray100,
    subtitleColor = gray300,
    infoTextColor = gray400,
)
