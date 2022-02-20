package ru.konohovalex.feature.account.presentation.auth.model

import ru.konohovalex.core.presentation.arch.viewevent.ViewEvent

sealed class AuthViewEvent : ViewEvent {
    data class Init(val isFirstLaunch: Boolean) : AuthViewEvent()

    data class SignUp(val credentialsUiModel: CredentialsUiModel) : AuthViewEvent()

    data class SignIn(val credentialsUiModel: CredentialsUiModel) : AuthViewEvent()

    object DeclineAuthorization : AuthViewEvent()

    object ErrorProcessed : AuthViewEvent()
}
