package ru.konohovalex.machinenotes.app.presentation.navigation

import ru.konohovalex.core.presentation.arch.navigation.NavigationRoute

internal sealed class AppRoute(override val destinations: List<String>) : NavigationRoute {
    companion object {
        private const val app = "app"
        private const val welcome = "welcome"
        private const val home = "home"
    }

    override val entryPoint = app

    object Welcome : AppRoute(destinations = listOf(welcome))

    object Home : AppRoute(destinations = listOf(home))
}
