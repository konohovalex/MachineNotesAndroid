package ru.konohovalex.feature.account.presentation.auth.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

sealed class AuthViewEvent : ViewEvent {
    object Init : AuthViewEvent()

    data class LogIn(val authDataUiModel: AuthDataUiModel) : AuthViewEvent()

    object DeclineAuthorization : AuthViewEvent()

    object ErrorProcessed : AuthViewEvent()
}
