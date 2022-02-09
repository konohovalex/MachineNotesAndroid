package ru.konohovalex.feature.preferences.presentation.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.ErrorCard
import ru.konohovalex.core.ui.compose.Logo
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.core.ui.compose.ThemedSnackbarHost
import ru.konohovalex.core.ui.compose.Tumbler
import ru.konohovalex.core.ui.extension.toTextWrapper
import ru.konohovalex.core.ui.model.Position
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.core.ui.model.TumblerData
import ru.konohovalex.core.utils.extension.safeCast
import ru.konohovalex.feature.preferences.presentation.R
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesViewEvent
import ru.konohovalex.feature.preferences.presentation.model.PreferencesViewState
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel

@Composable
internal fun PreferencesScreen(
    viewStateProvider: ViewStateProvider<PreferencesViewState>,
    viewEventHandler: ViewEventHandler<PreferencesViewEvent>,
) {
    val viewState by viewStateProvider.viewState.observeAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { snackbarHostState ->
            viewState.safeCast<PreferencesViewState.Data>()?.let {
                SnackbarHost(
                    viewState = it,
                    viewEventHandler = viewEventHandler,
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope,
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.palette.backgroundColor),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Logo()

            processViewState(
                viewState = viewState,
                viewEventHandler = viewEventHandler,
            )
        }
    }
}

@Composable
private fun SnackbarHost(
    viewState: PreferencesViewState.Data,
    viewEventHandler: ViewEventHandler<PreferencesViewEvent>,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
) {
    viewState.throwable?.let {
        ThemedSnackbarHost(
            messageTextWrapper = it.toTextWrapper(),
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
            onDismissed = {
                viewEventHandler.handle(PreferencesViewEvent.ErrorProcessed)
            },
        )
    }
}

@Composable
private fun ColumnScope.processViewState(
    viewState: PreferencesViewState?,
    viewEventHandler: ViewEventHandler<PreferencesViewEvent>,
) = viewState?.let {
    when (it) {
        is PreferencesViewState.Idle -> LaunchedEffect(true) {
            viewEventHandler.handle(PreferencesViewEvent.GetPreferences)
        }
        is PreferencesViewState.Loading -> LoadingState()
        is PreferencesViewState.Data -> with((it)) {
            DataState(
                preferencesUiModel = preferencesUiModel,
                onSelectedLanguageChanged = { languageUiModel ->
                    viewEventHandler.handle(PreferencesViewEvent.UpdateLanguage(languageUiModel))
                },
                onSelectedThemeModeChanged = { themeModeUiModel ->
                    viewEventHandler.handle(PreferencesViewEvent.UpdateThemeMode(themeModeUiModel))
                },
            )
        }
        is PreferencesViewState.Error -> with(it) {
            ErrorState(throwable, onActionButtonClick)
        }
    }
}

@Composable
private fun LoadingState() {
    ThemedCircularProgressBar(
        modifier = Modifier
            .size(Theme.sizes.logo)
            .padding(Theme.paddings.contentMedium),
    )
}

@Composable
private fun DataState(
    preferencesUiModel: PreferencesUiModel,
    onSelectedLanguageChanged: (LanguageUiModel) -> Unit,
    onSelectedThemeModeChanged: (ThemeModeUiModel) -> Unit,
) {
    with(preferencesUiModel) {
        Tumbler(
            tumblerData = TumblerData(
                titleTextWrapper = TextWrapper.StringResource(resourceId = R.string.language_tumbler_title),
                infoTextWrapper = TextWrapper.StringResource(resourceId = R.string.language_tumbler_info),
                positions = availableLanguages.mapToTumblerPositions(),
            ),
            selectedPositionData = currentLanguageUiModel,
            actionsEnabled = languageActionsEnabled,
            onSelectedPositionChanged = onSelectedLanguageChanged,
        )

        Tumbler(
            tumblerData = TumblerData(
                titleTextWrapper = TextWrapper.StringResource(resourceId = R.string.theme_mode_tumbler_title),
                positions = availableThemeModes.mapToTumblerPositions(),
            ),
            selectedPositionData = currentThemeModeUiModel,
            actionsEnabled = themeModeActionsEnabled,
            onSelectedPositionChanged = onSelectedThemeModeChanged,
        )
    }
}

@JvmName("mapLanguageUiModelsToTumblerPositions")
private fun List<LanguageUiModel>.mapToTumblerPositions() = map {
    Position.Text(
        data = it,
        textWrapper = it.textWrapper,
    )
}

@JvmName("mapThemeModeUiModelsToTumblerPositions")
private fun List<ThemeModeUiModel>.mapToTumblerPositions() = map {
    Position.Image(
        data = it,
        imageWrapper = it.imageWrapper,
    )
}

@Composable
private fun ColumnScope.ErrorState(
    throwable: Throwable,
    onActionButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .weight(1f),
        contentAlignment = Alignment.Center,
    ) {
        ErrorCard(
            descriptionTextWrapper = throwable.toTextWrapper(),
            onActionButtonClick = onActionButtonClick,
        )
    }
}
