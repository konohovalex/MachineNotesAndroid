package ru.konohovalex.machinenotes.app.presentation.main.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.feature.preferences.presentation.extension.localeOrDefault
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel
import ru.konohovalex.machinenotes.app.presentation.main.model.MainViewEvent
import ru.konohovalex.machinenotes.app.presentation.main.model.MainViewState
import ru.konohovalex.machinenotes.app.presentation.navigation.getHomeNavigationRoute
import ru.konohovalex.machinenotes.app.presentation.navigation.getWelcomeNavigationRoute
import ru.konohovalex.machinenotes.app.presentation.navigation.homeGraph
import ru.konohovalex.machinenotes.app.presentation.navigation.welcomeGraph
import java.util.Locale

@Composable
internal fun MainScreen(
    viewStateProvider: ViewStateProvider<MainViewState>,
    viewEventHandler: ViewEventHandler<MainViewEvent>,
    onThemeModeChanged: (isDarkTheme: Boolean) -> Unit,
    onLocaleChanged: (Locale) -> Unit,
    onBackPressed: () -> Unit,
) {
    val viewState by viewStateProvider.viewState.observeAsState()

    val isDarkTheme = when (viewState?.themeModeUiModel) {
        ThemeModeUiModel.LIGHT -> false
        ThemeModeUiModel.DARK -> true
        else -> isSystemInDarkTheme()
    }
    onThemeModeChanged.invoke(isDarkTheme)

    onLocaleChanged.invoke(viewState?.languageUiModel.localeOrDefault())

    Theme(isDarkTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.palette.backgroundColor),
        ) {
            when (viewState) {
                is MainViewState.Idle -> LaunchedEffect(true) {
                    viewEventHandler.handle(MainViewEvent.Init)
                }
                is MainViewState.FirstLaunch -> FirstLaunchState {
                    viewEventHandler.handle(MainViewEvent.FirstLaunchCompleted)
                }
                is MainViewState.NotFirstLaunch -> NotFirstLaunchState(onBackPressed)
                is MainViewState.Error -> ErrorState()
            }
        }
    }
}

@Composable
private fun FirstLaunchState(authorizationSuccessfulAction: () -> Unit) {
    NavHost(
        navController = rememberNavController(),
        startDestination = getWelcomeNavigationRoute(),
        modifier = Modifier
            .fillMaxSize(),
    ) {
        welcomeGraph(authorizationSuccessfulAction)
    }
}

@Composable
fun NotFirstLaunchState(onBackPressed: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = getHomeNavigationRoute(),
        modifier = Modifier
            .fillMaxSize(),
    ) {
        homeGraph(
            navController = navController,
            navigateBack = {
                navController.navigateUp()
                Unit
            },
            onBackPressed = onBackPressed,
        )
    }
}

@Composable
private fun ErrorState() {
    // tbd implement error state
}
