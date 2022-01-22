package ru.konohovalex.feature.preferences.presentation.navigation

import ru.konohovalex.core.presentation.arch.navigation.NavigationRoute

internal sealed class PreferencesRoute(override val destinations: List<String>) : NavigationRoute {
    companion object {
        private const val preferences = "preferences"
    }

    override val entryPoint = preferences

    object Preferences : PreferencesRoute(destinations = listOf())
}
