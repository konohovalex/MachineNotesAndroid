package ru.konohovalex.feature.account.presentation.auth.model

import androidx.annotation.StringRes
import ru.konohovalex.core.presentation.arch.viewstate.ErrorViewState
import ru.konohovalex.core.presentation.arch.viewstate.ViewState

internal sealed class AuthViewState : ViewState {
    object Idle : AuthViewState()

    object Loading : AuthViewState()

    data class Data(
        val credentialsUiModel: CredentialsUiModel,
        val throwable: Throwable?,
        val onErrorActionButtonClick: (() -> Unit)?,
    ) : AuthViewState() {
        companion object {
            fun empty(
                @StringRes
                declineAuthorizationButtonTextResId: Int,
                throwable: Throwable? = null,
                onErrorActionButtonClick: (() -> Unit)? = null,
            ) = Data(
                credentialsUiModel = CredentialsUiModel(
                    declineAuthorizationButtonTextResId = declineAuthorizationButtonTextResId,
                    userNameUiModel = UserNameUiModel(""),
                    passwordUiModel = PasswordUiModel(""),
                ),
                throwable = throwable,
                onErrorActionButtonClick = onErrorActionButtonClick,
            )
        }
    }

    object AuthorizationSuccessful : AuthViewState()

    object AuthorizationDeclined : AuthViewState()

    data class Error(
        override val throwable: Throwable,
        override val onActionButtonClick: () -> Unit,
    ) : AuthViewState(), ErrorViewState
}
