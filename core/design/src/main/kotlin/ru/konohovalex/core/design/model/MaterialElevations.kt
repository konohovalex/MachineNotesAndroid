package ru.konohovalex.core.design.model

import androidx.compose.material.AppBarDefaults
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class MaterialElevations(
    val dialog: Dp = 24.dp,
    val picker: Dp = 24.dp,
    val navDrawer: Dp = 16.dp,
    val bottomSheet: Dp = BottomSheetScaffoldDefaults.SheetElevation,
    val bottomNavigationBar: Dp = BottomNavigationDefaults.Elevation,
    val menu: Dp = 8.dp,
    val snackbar: Dp = 6.dp,
    val topAppBar: Dp = AppBarDefaults.TopAppBarElevation,
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
