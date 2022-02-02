package ru.konohovalex.feature.account.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.konohovalex.feature.account.presentation.auth.ui.compose.AuthScreen
import ru.konohovalex.feature.account.presentation.auth.viewmodel.AuthViewModel
import ru.konohovalex.feature.account.presentation.profile.ui.compose.ProfileScreen
import ru.konohovalex.feature.account.presentation.profile.viewmodel.ProfileViewModel

fun getAuthNavigationRoute() = AccountRoute.Auth.buildNavigationRoute()

fun NavGraphBuilder.authScreen(
    authorizationSuccessfulAction: () -> Unit,
    authorizationDeclinedAction: () -> Unit,
) {
    composable(route = AccountRoute.Auth.buildGraphRoute()) {
        val viewModel = hiltViewModel<AuthViewModel>()

        AuthScreen(
            viewStateProvider = viewModel,
            viewEventHandler = viewModel,
            authorizationSuccessfulAction = authorizationSuccessfulAction,
            authorizationDeclinedAction = authorizationDeclinedAction,
        )
    }
}

fun getProfileNavigationRoute() = AccountRoute.Profile.buildNavigationRoute()

fun NavGraphBuilder.profileScreen(navController: NavController) {
    composable(route = AccountRoute.Profile.buildGraphRoute()) {
        val viewModel = hiltViewModel<ProfileViewModel>()

        ProfileScreen(
            viewStateProvider = viewModel,
            viewEventHandler = viewModel,
            navigateToAuth = {
                navController.navigate(getAuthNavigationRoute())
            },
        )
    }
}
