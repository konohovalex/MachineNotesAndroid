package ru.konohovalex.feature.preferences.presentation.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.Logo
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.core.ui.compose.Tumbler
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

        val viewState = viewStateProvider.viewState.observeAsState()

        val onSelectedLanguageChanged = remember {
            { languageUiModel: LanguageUiModel ->
                viewEventHandler.handle(PreferencesScreenViewEvent.UpdateLanguage(languageUiModel))
            }
        }
        val onSelectedThemeModeChanged = remember {
            { themeModeUiModel: ThemeModeUiModel ->
                viewEventHandler.handle(PreferencesScreenViewEvent.UpdateThemeMode(themeModeUiModel))
            }
        }

        when (val viewStateValue = viewState.value) {
            is PreferencesViewState.Idle -> viewEventHandler.handle(PreferencesScreenViewEvent.GetPreferences)
            is PreferencesViewState.Loading -> LoadingState()
            is PreferencesViewState.Data -> DataState(
                preferencesUiModel = viewStateValue.preferencesUiModel,
                onSelectedLanguageChanged = onSelectedLanguageChanged,
                onSelectedThemeModeChanged = onSelectedThemeModeChanged,
                throwable = viewStateValue.throwable,
            )
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
    // tbd if throwable is null, show notification
    with(preferencesUiModel) {
        Tumbler(
            tumblerData = availableLanguagesTumblerData,
            selectedPositionData = currentLanguageUiModel,
            actionsEnabled = languageActionsEnabled,
            onSelectedPositionChanged = onSelectedLanguageChanged,
        )

        Tumbler(
            tumblerData = availableThemeModesTumblerData,
            selectedPositionData = currentThemeModeUiModel,
            actionsEnabled = themeModeActionsEnabled,
            onSelectedPositionChanged = onSelectedThemeModeChanged,
        )
    }
}

@Composable
private fun ErrorState() {
    // tbd implement error state
}
