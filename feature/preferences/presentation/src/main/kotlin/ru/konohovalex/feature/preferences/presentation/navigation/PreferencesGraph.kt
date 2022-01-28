package ru.konohovalex.feature.preferences.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.konohovalex.feature.preferences.presentation.ui.compose.PreferencesScreen
import ru.konohovalex.feature.preferences.presentation.viewmodel.PreferencesViewModel

fun getPreferencesNavigationRoute() = PreferencesRoute.Preferences.buildNavigationRoute()

fun NavGraphBuilder.preferencesScreen() {
    composable(route = PreferencesRoute.Preferences.buildGraphRoute()) {
        val viewModel = hiltViewModel<PreferencesViewModel>()

        PreferencesScreen(
            viewStateProvider = viewModel,
            viewEventHandler = viewModel,
        )
    }
}
