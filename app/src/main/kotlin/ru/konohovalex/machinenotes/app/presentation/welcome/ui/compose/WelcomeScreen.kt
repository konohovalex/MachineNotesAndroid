package ru.konohovalex.machinenotes.app.presentation.welcome.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.feature.account.presentation.navigation.authScreen
import ru.konohovalex.feature.account.presentation.navigation.getAuthNavigationRoute
import ru.konohovalex.feature.preferences.presentation.navigation.getPreferencesNavigationRoute
import ru.konohovalex.feature.preferences.presentation.navigation.preferencesScreen

@Composable
internal fun WelcomeScreen(authorizationSuccessfulAction: () -> Unit) {
    Column {
        val preferencesNavController = rememberNavController()
        val preferencesStartDestination = remember {
            getPreferencesNavigationRoute()
        }

        NavHost(
            navController = preferencesNavController,
            startDestination = preferencesStartDestination,
        ) {
            preferencesScreen()
        }

        val authNavController = rememberNavController()
        val authStartDestination = remember {
            getAuthNavigationRoute()
        }

        NavHost(
            navController = authNavController,
            startDestination = authStartDestination,
        ) {
            authScreen(
                authorizationSuccessfulAction = authorizationSuccessfulAction,
                authorizationDeclinedAction = authorizationSuccessfulAction,
            )
        }
    }
}
