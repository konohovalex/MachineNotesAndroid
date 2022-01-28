package ru.konohovalex.machinenotes.app.presentation.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.konohovalex.feature.account.presentation.navigation.authScreen
import ru.konohovalex.feature.account.presentation.navigation.getAuthNavigationRoute
import ru.konohovalex.feature.account.presentation.navigation.profileScreen
import ru.konohovalex.feature.notes.presentation.navigation.getNoteDetailsNavigationRoute
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
    authorizationSuccessfulAction: () -> Unit,
    authorizationDeclinedAction: () -> Unit,
) {
    composable(route = AppRoute.Home.buildGraphRoute()) {
        val navigateToNoteDetails = remember {
            { noteId: String? ->
                navController.navigate(getNoteDetailsNavigationRoute(noteId))
            }
        }

        val navigateToAuth = remember {
            {
                navController.navigate(getAuthNavigationRoute())
            }
        }

        HomeScreen(navigateToNoteDetails, navigateToAuth)
    }

    noteDetailsScreen(navController)

    authScreen(
        authorizationSuccessfulAction = authorizationSuccessfulAction,
        authorizationDeclinedAction = authorizationDeclinedAction,
    )
}

internal fun NavGraphBuilder.bottomNavigationGraph(
    navigateToNoteDetails: (noteId: String?) -> Unit,
    navigateToAuth: () -> Unit,
) {
    noteListScreen(navigateToNoteDetails)
    preferencesScreen()
    profileScreen(navigateToAuth)
}
