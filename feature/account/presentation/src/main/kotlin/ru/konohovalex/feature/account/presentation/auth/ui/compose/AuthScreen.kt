package ru.konohovalex.feature.account.presentation.auth.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
        // tbd solve keyboard problem
        contentAlignment = Alignment.BottomCenter,
    ) {
        val viewState by viewStateProvider.viewState.observeAsState()

        when (viewState) {
            is AuthViewState.Idle -> LaunchedEffect(true) {
                viewEventHandler.handle(AuthViewEvent.Init)
            }
            is AuthViewState.Loading -> LoadingState()
            is AuthViewState.Data -> with(viewState as AuthViewState.Data) {
                DataState(
                    authDataUiModel = authDataUiModel,
                    onLogInButtonClick = {
                        viewEventHandler.handle(AuthViewEvent.LogIn(it))
                    },
                    onDeclineAuthorizationButtonClick = {
                        viewEventHandler.handle(AuthViewEvent.DeclineAuthorization)
                    },
                    throwable = throwable,
                )
            }
            is AuthViewState.AuthorizationSuccessful -> LaunchedEffect(true) {
                authorizationSuccessfulAction.invoke()
            }
            is AuthViewState.AuthorizationDeclined -> LaunchedEffect(true) {
                authorizationDeclinedAction.invoke()
            }
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
    onLogInButtonClick: (AuthDataUiModel) -> Unit,
    onDeclineAuthorizationButtonClick: () -> Unit,
    throwable: Throwable?,
) {
    // tbd if throwable is not null, show notification
    AuthView(
        authDataUiModel = authDataUiModel,
        userNameValidator = UserNameValidator(),
        passwordValidator = PasswordValidator(),
        onLogInButtonClick = onLogInButtonClick,
        onDeclineAuthorizationButtonClick = onDeclineAuthorizationButtonClick,
    )
}

@Composable
private fun ErrorState() {
    // tbd implement error state
}
