package ru.konohovalex.machinenotes.app.presentation.welcome.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.feature.account.presentation.navigation.authScreen
import ru.konohovalex.feature.account.presentation.navigation.getAuthNavigationRoute
import ru.konohovalex.feature.preferences.presentation.navigation.getPreferencesNavigationRoute
import ru.konohovalex.feature.preferences.presentation.navigation.preferencesScreen

@Composable
internal fun WelcomeScreen(authorizationSuccessfulAction: () -> Unit) {
    // TODO("fix collapsing preferences, if auth fields focused")
    Column {
        NavHost(
            modifier = Modifier
                .weight(1f),
            navController = rememberNavController(),
            startDestination = getPreferencesNavigationRoute(),
        ) {
            preferencesScreen()
        }

        NavHost(
            modifier = Modifier
                .weight(1f),
            navController = rememberNavController(),
            startDestination = getAuthNavigationRoute(),
        ) {
            authScreen(
                isFirstLaunch = true,
                authorizationSuccessfulAction = authorizationSuccessfulAction,
                authorizationDeclinedAction = authorizationSuccessfulAction,
            )
        }
    }
}
