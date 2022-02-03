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
import androidx.compose.material.rememberScaffoldState
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

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            viewState.safeCast<PreferencesViewState.Data>()?.let {
                SnackbarHost(
                    throwable = it.throwable,
                    snackbarHostState = scaffoldState.snackbarHostState,
                    coroutineScope = rememberCoroutineScope(),
                    onDismissed = {
                        viewEventHandler.handle(PreferencesViewEvent.ErrorProcessed)
                    },
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

            when (viewState) {
                is PreferencesViewState.Idle -> LaunchedEffect(true) {
                    viewEventHandler.handle(PreferencesViewEvent.GetPreferences)
                }
                is PreferencesViewState.Loading -> LoadingState()
                is PreferencesViewState.Data -> with((viewState as PreferencesViewState.Data)) {
                    DataState(
                        preferencesUiModel = preferencesUiModel,
                        onSelectedLanguageChanged = {
                            viewEventHandler.handle(PreferencesViewEvent.UpdateLanguage(it))
                        },
                        onSelectedThemeModeChanged = {
                            viewEventHandler.handle(PreferencesViewEvent.UpdateThemeMode(it))
                        },
                    )
                }
                is PreferencesViewState.Error -> with(viewState as PreferencesViewState.Error) {
                    ErrorState(throwable, onActionButtonClick)
                }
            }
        }
    }
}

@Composable
private fun SnackbarHost(
    throwable: Throwable?,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    onDismissed: () -> Unit,
) {
    throwable?.let {
        ThemedSnackbarHost(
            messageTextWrapper = it.toTextWrapper(),
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
            onDismissed = onDismissed,
        )
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
