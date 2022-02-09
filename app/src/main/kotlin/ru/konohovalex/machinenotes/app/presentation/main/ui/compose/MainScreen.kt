package ru.konohovalex.machinenotes.app.presentation.main.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.ErrorCard
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.core.ui.extension.toTextWrapper
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
    onLocaleChanged: (Locale) -> Unit,
    onThemeModeChanged: (isDarkTheme: Boolean) -> Unit,
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
            processViewState(
                viewState = viewState,
                viewEventHandler = viewEventHandler,
                onBackPressed = onBackPressed,
            )
        }
    }
}

@Composable
private fun processViewState(
    viewState: MainViewState?,
    viewEventHandler: ViewEventHandler<MainViewEvent>,
    onBackPressed: () -> Unit,
) = viewState?.let {
    when (it) {
        is MainViewState.Idle -> LaunchedEffect(true) {
            viewEventHandler.handle(MainViewEvent.Init)
        }
        is MainViewState.Loading -> LoadingState()
        is MainViewState.FirstLaunch -> FirstLaunchState {
            viewEventHandler.handle(MainViewEvent.FirstLaunchCompleted)
        }
        is MainViewState.NotFirstLaunch -> NotFirstLaunchState(onBackPressed)
        is MainViewState.Error -> with(it) {
            ErrorState(throwable, onActionButtonClick)
        }
    }
}

@Composable
private fun LoadingState() {
    ThemedCircularProgressBar(
        modifier = Modifier
            .fillMaxSize(),
    )
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
private fun ErrorState(
    throwable: Throwable,
    onActionButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ErrorCard(
            descriptionTextWrapper = throwable.toTextWrapper(),
            onActionButtonClick = onActionButtonClick,
        )
    }
}
