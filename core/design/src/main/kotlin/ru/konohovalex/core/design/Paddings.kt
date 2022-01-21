package ru.konohovalex.core.design

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Paddings(
    val contentDefault: Dp = 8.dp,
    val contentMedium: Dp = 16.dp,

    val buttonHorizontal: Dp = 24.dp,
    val buttonVertical: Dp = 10.dp,

    val floatingActionButtonCompensation: Dp = 2.dp,
)
