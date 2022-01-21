package ru.konohovalex.feature.preferences.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.konohovalex.feature.preferences.presentation.ui.compose.PreferencesScreen

fun getPreferencesNavigationRoute() = PreferencesRoute.Preferences.buildNavigationRoute()

fun NavGraphBuilder.preferencesScreen() {
    composable(route = PreferencesRoute.Preferences.buildGraphRoute()) {
        PreferencesScreen()
    }
}
