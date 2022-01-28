package ru.konohovalex.feature.account.presentation.auth.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.feature.account.presentation.auth.model.AuthDataUiModel
import ru.konohovalex.feature.account.presentation.auth.model.AuthViewEvent
import ru.konohovalex.feature.account.presentation.auth.model.AuthViewState
import ru.konohovalex.feature.account.presentation.auth.validator.PasswordValidator
import ru.konohovalex.feature.account.presentation.auth.validator.UserNameValidator

@Composable
internal fun AuthScreen(
    viewStateProvider: ViewStateProvider<AuthViewState>,
    viewEventHandler: ViewEventHandler<AuthViewEvent>,
    authorizationSuccessfulAction: () -> Unit,
    authorizationDeclinedAction: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.palette.backgroundColor),
        contentAlignment = Alignment.BottomCenter,
    ) {
        val viewState = viewStateProvider.viewState.observeAsState()

        val onLogInButtonClick = remember {
            { authDataUiModel: AuthDataUiModel ->
                viewEventHandler.handle(AuthViewEvent.LogIn(authDataUiModel))
            }
        }

        val onDeclineAuthorizationButtonClick = remember {
            {
                viewEventHandler.handle(AuthViewEvent.DeclineAuthorization)
            }
        }

        when (val viewStateValue = viewState.value) {
            is AuthViewState.Idle -> viewEventHandler.handle(AuthViewEvent.Init)
            is AuthViewState.Loading -> LoadingState()
            is AuthViewState.Data -> DataState(
                authDataUiModel = viewStateValue.authDataUiModel,
                throwable = viewStateValue.throwable,
                onLogInButtonClick = onLogInButtonClick,
                onDeclineAuthorizationButtonClick = onDeclineAuthorizationButtonClick,
            )
            is AuthViewState.AuthorizationSuccessful -> authorizationSuccessfulAction.invoke()
            is AuthViewState.AuthorizationDeclined -> authorizationDeclinedAction.invoke()
            is AuthViewState.Error -> ErrorState()
        }
    }
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
    authDataUiModel: AuthDataUiModel,
    throwable: Throwable?,
    onLogInButtonClick: (AuthDataUiModel) -> Unit,
    onDeclineAuthorizationButtonClick: () -> Unit,
) {
    val userNameValidator = remember {
        UserNameValidator()
    }
    val passwordValidator = remember {
        PasswordValidator()
    }

    AuthView(
        authDataUiModel = authDataUiModel,
        userNameValidator = userNameValidator,
        passwordValidator = passwordValidator,
        onLogInButtonClick = onLogInButtonClick,
        onDeclineAuthorizationButtonClick = onDeclineAuthorizationButtonClick,
        throwable = throwable,
    )
}

@Composable
private fun ErrorState() {
    // tbd implement error state (for both first and further pages)
}
