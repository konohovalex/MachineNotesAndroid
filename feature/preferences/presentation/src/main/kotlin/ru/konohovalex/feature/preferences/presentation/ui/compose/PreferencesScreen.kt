package ru.konohovalex.feature.preferences.presentation.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.Logo
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.core.ui.compose.Tumbler
import ru.konohovalex.core.ui.model.Position
import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.core.ui.model.TumblerData
import ru.konohovalex.feature.preferences.presentation.R
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenViewEvent
import ru.konohovalex.feature.preferences.presentation.model.PreferencesUiModel
import ru.konohovalex.feature.preferences.presentation.model.PreferencesViewState
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel

@Composable
internal fun PreferencesScreen(
    viewStateProvider: ViewStateProvider<PreferencesViewState>,
    viewEventHandler: ViewEventHandler<PreferencesScreenViewEvent>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.palette.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Logo()

        val viewState by viewStateProvider.viewState.observeAsState()

        when (viewState) {
            is PreferencesViewState.Idle -> LaunchedEffect(true) {
                viewEventHandler.handle(PreferencesScreenViewEvent.GetPreferences)
            }
            is PreferencesViewState.Loading -> LoadingState()
            is PreferencesViewState.Data -> with((viewState as PreferencesViewState.Data)) {
                DataState(
                    preferencesUiModel = preferencesUiModel,
                    onSelectedLanguageChanged = {
                        viewEventHandler.handle(PreferencesScreenViewEvent.UpdateLanguage(it))
                    },
                    onSelectedThemeModeChanged = {
                        viewEventHandler.handle(PreferencesScreenViewEvent.UpdateThemeMode(it))
                    },
                    throwable = throwable,
                )
            }
            is PreferencesViewState.Error -> ErrorState()
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
    throwable: Throwable?,
) {
    // tbd if throwable is not null, show notification
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
private fun ErrorState() {
    // tbd implement error state
}
