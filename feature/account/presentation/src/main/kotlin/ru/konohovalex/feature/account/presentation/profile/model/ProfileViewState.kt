package ru.konohovalex.feature.account.presentation.profile.model

import ru.konohovalex.core.presentation.arch.viewstate.ErrorViewState
import ru.konohovalex.core.presentation.arch.viewstate.ViewState

internal sealed class ProfileViewState : ViewState {
    object Idle : ProfileViewState()

    object Loading : ProfileViewState()

    data class Data(val profileUiModel: ProfileUiModel) : ProfileViewState()

    data class Error(
        override val throwable: Throwable,
        override val onActionButtonClick: () -> Unit,
    ) : ProfileViewState(), ErrorViewState
}
