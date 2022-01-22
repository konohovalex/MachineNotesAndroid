package ru.konohovalex.feature.preferences.presentation.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.konohovalex.core.design.Theme
import ru.konohovalex.core.presentation.arch.vieweffect.ViewEffectPublisher
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.feature.preferences.presentation.viewmodel.PreferencesViewModel
import ru.konohovalex.feature.preferences.presentation.R
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenViewEffect
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenViewEvent
import ru.konohovalex.feature.preferences.presentation.model.PreferencesViewState

@Composable
internal fun PreferencesScreen(
    viewStateProvider: ViewStateProvider<PreferencesViewState> = hiltViewModel<PreferencesViewModel>(),
    viewEventHandler: ViewEventHandler<PreferencesScreenViewEvent> = hiltViewModel<PreferencesViewModel>(),
    viewEffectPublisher: ViewEffectPublisher<PreferencesScreenViewEffect> = hiltViewModel<PreferencesViewModel>(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Logo()

        val viewState = viewStateProvider.viewState.observeAsState()

        when (val viewStateValue = viewState.value) {
            is PreferencesViewState.Idle -> viewEventHandler.handle(PreferencesScreenViewEvent.GetPreferences)
            is PreferencesViewState.Loading -> LoadingState()
            is PreferencesViewState.Data -> DataState(
                data = viewStateValue,
                viewEventHandler = viewEventHandler,
                viewEffectPublisher = viewEffectPublisher,
            )
            is PreferencesViewState.Error -> ErrorState()
        }
    }
}

@Composable
private fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = stringResource(id = R.string.logo),
        modifier = Modifier
            .padding(Theme.paddings.contentMedium),
    )
}

@Composable
private fun LoadingState() {
    ThemedCircularProgressBar(
        modifier = Modifier
            .fillMaxSize(),
    )
}

@Composable
private fun DataState(
    data: PreferencesViewState.Data,
    viewEventHandler: ViewEventHandler<PreferencesScreenViewEvent>,
    viewEffectPublisher: ViewEffectPublisher<PreferencesScreenViewEffect>,
) {
    val viewEffectState = viewEffectPublisher.viewEffect.observeAsState()

    LanguageTumbler(
        currentLanguageUiModel = data.preferencesUiModel.currentLanguageUiModel,
        availableLanguages = data.preferencesUiModel.availableLanguages,
        viewEventHandler = viewEventHandler,
        viewEffectState = viewEffectState,
    )

    ThemeModeTumbler(
        currentThemeModeUiModel = data.preferencesUiModel.currentThemeModeUiModel,
        availableThemeModes = data.preferencesUiModel.availableThemeModes,
        viewEventHandler = viewEventHandler,
        viewEffectState = viewEffectState,
    )
}

@Composable
private fun ErrorState() {
    // tbd implement error state
}
