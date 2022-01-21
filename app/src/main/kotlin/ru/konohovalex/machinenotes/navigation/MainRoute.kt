package ru.konohovalex.machinenotes.navigation

import ru.konohovalex.core.presentation.arch.navigation.NavigationRoute

internal sealed class MainRoute(override val destinations: List<String>) : NavigationRoute {
    override val entryPoint = main

    companion object {
        private const val main = "main"
        private const val home = "home"
    }

    object Home : MainRoute(destinations = listOf(home))
}
