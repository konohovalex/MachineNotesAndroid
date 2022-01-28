package ru.konohovalex.feature.account.presentation.auth.model

import androidx.annotation.StringRes
import ru.konohovalex.core.presentation.arch.viewstate.ViewState

sealed class AuthViewState : ViewState {
    object Idle : AuthViewState()

    object Loading : AuthViewState()

    data class Data(
        val authDataUiModel: AuthDataUiModel,
        val throwable: Throwable?,
    ) : AuthViewState() {
        companion object {
            fun empty(
                @StringRes
                declineAuthorizationButtonTextResId: Int,
                throwable: Throwable? = null
            ) = Data(
                authDataUiModel = AuthDataUiModel(
                    declineAuthorizationButtonTextResId = declineAuthorizationButtonTextResId,
                    userNameUiModel = UserNameUiModel(""),
                    passwordUiModel = PasswordUiModel(""),
                ),
                throwable = throwable,
            )
        }
    }

    object AuthorizationSuccessful : AuthViewState()

    object AuthorizationDeclined : AuthViewState()

    data class Error(val throwable: Throwable) : AuthViewState()
}
