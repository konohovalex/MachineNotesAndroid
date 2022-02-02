package ru.konohovalex.machinenotes.app.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.konohovalex.feature.account.presentation.navigation.authScreen
import ru.konohovalex.feature.account.presentation.navigation.profileScreen
import ru.konohovalex.feature.notes.presentation.navigation.noteDetailsScreen
import ru.konohovalex.feature.notes.presentation.navigation.noteListScreen
import ru.konohovalex.feature.preferences.presentation.navigation.preferencesScreen
import ru.konohovalex.machinenotes.app.presentation.home.ui.compose.HomeScreen
import ru.konohovalex.machinenotes.app.presentation.welcome.ui.compose.WelcomeScreen

internal fun getWelcomeNavigationRoute() = AppRoute.Welcome.buildNavigationRoute()

internal fun NavGraphBuilder.welcomeGraph(authorizationSuccessfulAction: () -> Unit) {
    composable(route = AppRoute.Welcome.buildGraphRoute()) {
        WelcomeScreen(authorizationSuccessfulAction)
    }
}

internal fun getHomeNavigationRoute() = AppRoute.Home.buildNavigationRoute()

internal fun NavGraphBuilder.homeGraph(
    navController: NavController,
    navigateBack: () -> Unit,
    onBackPressed: () -> Unit,
) {
    homeScreen(navController, onBackPressed)

    noteDetailsScreen(navigateBack)

    authScreen(
        authorizationSuccessfulAction = navigateBack,
        authorizationDeclinedAction = navigateBack,
    )
}

internal fun NavGraphBuilder.homeScreen(
    navController: NavController,
    onBackPressed: () -> Unit,
) {
    composable(route = AppRoute.Home.buildGraphRoute()) {
        HomeScreen(navController, onBackPressed)
    }
}

internal fun NavGraphBuilder.bottomNavigationGraph(navController: NavController) {
    noteListScreen(navController)
    preferencesScreen()
    profileScreen(navController)
}
