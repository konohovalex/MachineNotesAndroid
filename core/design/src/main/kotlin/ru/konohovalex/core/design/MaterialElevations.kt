package ru.konohovalex.core.design

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// tbd set links to *Defaults.elevation (forex AppBarDefaults.elevation)
data class MaterialElevations(
    val dialog: Dp = 24.dp,
    val picker: Dp = 24.dp,
    val navDrawer: Dp = 16.dp,
    val bottomSheet: Dp = 16.dp,
    val bottomNavigationBar: Dp = 8.dp,
    val menu: Dp = 8.dp,
    val snackbar: Dp = 6.dp,
    val appBar: Dp = 4.dp,
    val refreshIndicator: Dp = 3.dp,
    val switch: Dp = 1.dp,

    val floatingActionButtonResting: Dp = 6.dp,
    val floatingActionButtonPressed: Dp = 12.dp,

    val cardResting: Dp = 2.dp,
    val cardPicked: Dp = 8.dp,

    val raisedButtonResting: Dp = 2.dp,
    val raisedButtonPressed: Dp = 8.dp,

    val searchBarScrolled: Dp = 3.dp,
    val searchBarResting: Dp = 3.dp,
)
