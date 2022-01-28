package ru.konohovalex.feature.account.presentation.navigation

import ru.konohovalex.core.presentation.arch.navigation.NavigationRoute

internal sealed class AccountRoute(override val destinations: List<String>) : NavigationRoute {
    companion object {
        private const val account = "account"
        private const val profile = "profile"
        private const val auth = "auth"
    }

    override val entryPoint = profile

    object Profile : AccountRoute(destinations = listOf())

    object Auth : AccountRoute(destinations = listOf(auth))
}
