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
import ru.konohovalex.core.presentation.arch.effect.EffectPublisher
import ru.konohovalex.core.presentation.arch.event.EventHandler
import ru.konohovalex.core.presentation.arch.state.ScreenStateProvider
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.feature.preferences.presentation.PreferencesViewModel
import ru.konohovalex.feature.preferences.presentation.R
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenEffect
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenEvent
import ru.konohovalex.feature.preferences.presentation.model.PreferencesScreenState

@Composable
internal fun PreferencesScreen(
    screenStateProvider: ScreenStateProvider<PreferencesScreenState> = hiltViewModel<PreferencesViewModel>(),
    eventHandler: EventHandler<PreferencesScreenEvent> = hiltViewModel<PreferencesViewModel>(),
    effectPublisher: EffectPublisher<PreferencesScreenEffect> = hiltViewModel<PreferencesViewModel>(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Logo()

        val screenState = screenStateProvider.screenState.observeAsState()

        when (val screenStateValue = screenState.value) {
            is PreferencesScreenState.Idle -> eventHandler.handle(PreferencesScreenEvent.GetPreferences)
            is PreferencesScreenState.Loading -> LoadingState()
            is PreferencesScreenState.Data -> DataState(
                data = screenStateValue,
                eventHandler = eventHandler,
                effectPublisher = effectPublisher,
            )
            is PreferencesScreenState.Error -> ErrorState()
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
    data: PreferencesScreenState.Data,
    eventHandler: EventHandler<PreferencesScreenEvent>,
    effectPublisher: EffectPublisher<PreferencesScreenEffect>,
) {
    val effectState = effectPublisher.effect.observeAsState()

    LanguageTumbler(
        currentLanguageUiModel = data.preferencesUiModel.currentLanguageUiModel,
        availableLanguages = data.preferencesUiModel.availableLanguages,
        eventHandler = eventHandler,
        effectState = effectState,
    )

    ThemeModeTumbler(
        currentThemeModeUiModel = data.preferencesUiModel.currentThemeModeUiModel,
        availableThemeModes = data.preferencesUiModel.availableThemeModes,
        eventHandler = eventHandler,
        effectState = effectState,
    )
}

@Composable
private fun ErrorState() {
    // tbd implement error state
}
