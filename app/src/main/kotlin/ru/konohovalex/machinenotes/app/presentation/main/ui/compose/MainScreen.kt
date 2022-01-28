package ru.konohovalex.machinenotes.app.presentation.main.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.machinenotes.app.presentation.main.model.MainViewEvent
import ru.konohovalex.machinenotes.app.presentation.main.model.MainViewState
import ru.konohovalex.machinenotes.app.presentation.navigation.getHomeNavigationRoute
import ru.konohovalex.machinenotes.app.presentation.navigation.getWelcomeNavigationRoute
import ru.konohovalex.machinenotes.app.presentation.navigation.homeGraph
import ru.konohovalex.machinenotes.app.presentation.navigation.welcomeGraph

@Composable
internal fun MainScreen(
    viewStateProvider: ViewStateProvider<MainViewState>,
    viewEventHandler: ViewEventHandler<MainViewEvent>,
) {
    Theme(darkTheme = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.palette.backgroundColor),
        ) {
            val viewState = viewStateProvider.viewState.observeAsState()

            val authorizationSuccessfulAction = remember {
                {
                    viewEventHandler.handle(MainViewEvent.FirstLaunchCompleted)
                }
            }

            when (val viewStateValue = viewState.value) {
                is MainViewState.Idle -> viewEventHandler.handle(MainViewEvent.GetIsFirstLaunch)
                is MainViewState.Data -> DataState(
                    isFirstLaunch = viewStateValue.isFirstLaunch,
                    authorizationSuccessfulAction = authorizationSuccessfulAction,
                )
                is MainViewState.Error -> ErrorState()
            }
        }
    }
}

@Composable
private fun DataState(
    isFirstLaunch: Boolean,
    authorizationSuccessfulAction: () -> Unit,
) {
    if (isFirstLaunch) {
        val welcomeNavController = rememberNavController()
        val welcomeStartDestination = remember {
            getWelcomeNavigationRoute()
        }

        NavHost(
            navController = welcomeNavController,
            startDestination = welcomeStartDestination,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            welcomeGraph(authorizationSuccessfulAction)
        }
    }
    else {
        val homeNavController = rememberNavController()
        val homeStartDestination = remember {
            getHomeNavigationRoute()
        }

        val navigateBack = remember {
            {
                // tbd this is incorrect with bottom navigation
                homeNavController.navigateUp()
                Unit
            }
        }

        NavHost(
            navController = homeNavController,
            startDestination = homeStartDestination,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            homeGraph(
                navController = homeNavController,
                authorizationSuccessfulAction = navigateBack,
                authorizationDeclinedAction = navigateBack,
            )
        }
    }
}

@Composable
private fun ErrorState() {
    // tbd implement error state
}
