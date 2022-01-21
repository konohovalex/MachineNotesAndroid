package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.presentation.arch.state.ScreenState

internal sealed class PreferencesScreenState : ScreenState {
    object Idle : PreferencesScreenState()

    object Loading : PreferencesScreenState()

    data class Data(val preferencesUiModel: PreferencesUiModel) : PreferencesScreenState()

    data class Error(val throwable: Throwable) : PreferencesScreenState()
}
