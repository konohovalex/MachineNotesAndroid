package ru.konohovalex.core.design.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Paddings(
    val contentMin: Dp = 2.dp,
    val contentExtraSmall: Dp = 4.dp,
    val contentSmall: Dp = 8.dp,
    val contentMedium: Dp = 16.dp,
    val contentLarge: Dp = 24.dp,
    val contentExtraLarge: Dp = 32.dp,
    val contentMax: Dp = 64.dp,

    val buttonHorizontal: Dp = 24.dp,
    val buttonVertical: Dp = 10.dp,

    val floatingActionButtonCompensation: Dp = 2.dp,

    val bottomNavigationBarVertical: Dp = 6.dp,
)
