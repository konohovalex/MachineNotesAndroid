package ru.konohovalex.machinenotes.app.presentation.main.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.konohovalex.core.data.Constants
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.extension.changeLocale
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
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
) {
    val viewState = viewStateProvider.viewState.observeAsState()

    // tbd fix
    LocalContext.current.changeLocale(
        when (viewState.value?.languageUiModel) {
            LanguageUiModel.RUS -> Constants.LanguageCodes.RUSSIAN
            else -> Constants.LanguageCodes.ENGLISH
        }
    )

    Theme(
        when (viewState.value?.themeModeUiModel) {
            ThemeModeUiModel.LIGHT -> false
            ThemeModeUiModel.DARK -> true
            else -> isSystemInDarkTheme()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.palette.backgroundColor),
        ) {
            val authorizationSuccessfulAction = remember {
                {
                    viewEventHandler.handle(MainViewEvent.FirstLaunchCompleted)
                }
            }

            when (viewState.value) {
                is MainViewState.Idle -> viewEventHandler.handle(MainViewEvent.Init)
                is MainViewState.FirstLaunch -> FirstLaunchState(authorizationSuccessfulAction)
                is MainViewState.NotFirstLaunch -> NotFirstLaunchState()
                is MainViewState.Error -> ErrorState()
            }
        }
    }
}

@Composable
private fun FirstLaunchState(authorizationSuccessfulAction: () -> Unit) {
    val navController = rememberNavController()
    val welcomeStartDestination = remember {
        getWelcomeNavigationRoute()
    }

    NavHost(
        navController = navController,
        startDestination = welcomeStartDestination,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        welcomeGraph(authorizationSuccessfulAction)
    }
}

@Composable
fun NotFirstLaunchState() {
    val navController = rememberNavController()
    val homeStartDestination = remember {
        getHomeNavigationRoute()
    }

    val navigateBack = remember {
        {
            // tbd this is incorrect with bottom navigation
            navController.navigateUp()
            Unit
        }
    }

    NavHost(
        navController = navController,
        startDestination = homeStartDestination,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        homeGraph(
            navController = navController,
            authorizationSuccessfulAction = navigateBack,
            authorizationDeclinedAction = navigateBack,
        )
    }
}

@Composable
private fun ErrorState() {
    // tbd implement error state
}
