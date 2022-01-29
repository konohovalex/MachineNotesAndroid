package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.presentation.arch.viewstate.ViewState

internal sealed class PreferencesViewState : ViewState {
    object Idle : PreferencesViewState()

    object Loading : PreferencesViewState()

    data class Data(
        val preferencesUiModel: PreferencesUiModel,
        val throwable: Throwable? = null,
    ) : PreferencesViewState()

    data class Error(val throwable: Throwable) : PreferencesViewState()
}
