package ru.konohovalex.feature.account.presentation.auth.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import ru.konohovalex.core.design.model.Theme
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.ui.compose.ErrorCard
import ru.konohovalex.core.ui.compose.ThemedCircularProgressBar
import ru.konohovalex.core.ui.compose.ThemedSnackbarHost
import ru.konohovalex.core.ui.extension.toTextWrapper
import ru.konohovalex.core.utils.extension.safeCast
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
    val viewState by viewStateProvider.viewState.observeAsState()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { snackbarHostState ->
            viewState.safeCast<AuthViewState.Data>()?.let {
                SnackbarHost(
                    viewState = it,
                    viewEventHandler = viewEventHandler,
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope,
                )
            }
        },
        backgroundColor = Theme.palette.backgroundColor,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            processViewState(
                viewState = viewState,
                viewEventHandler = viewEventHandler,
                authorizationSuccessfulAction = authorizationSuccessfulAction,
                authorizationDeclinedAction = authorizationDeclinedAction,
            )
        }
    }
}

@Composable
private fun SnackbarHost(
    viewState: AuthViewState.Data,
    viewEventHandler: ViewEventHandler<AuthViewEvent>,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
) {
    viewState.throwable?.let {
        ThemedSnackbarHost(
            messageTextWrapper = it.toTextWrapper(),
            snackbarHostState = snackbarHostState,
            onActionButtonClick = {
                viewEventHandler.handle(AuthViewEvent.ErrorProcessed)
                viewState.onErrorActionButtonClick?.invoke()
            },
            coroutineScope = coroutineScope,
            onDismissed = {
                viewEventHandler.handle(AuthViewEvent.ErrorProcessed)
            },
        )
    }
}

@Composable
private fun processViewState(
    viewState: AuthViewState?,
    viewEventHandler: ViewEventHandler<AuthViewEvent>,
    authorizationSuccessfulAction: () -> Unit,
    authorizationDeclinedAction: () -> Unit,
) = viewState?.let {
    when (it) {
        is AuthViewState.Idle -> LaunchedEffect(true) {
            viewEventHandler.handle(AuthViewEvent.Init)
        }
        is AuthViewState.Loading -> LoadingState()
        is AuthViewState.Data -> with(it) {
            DataState(
                authDataUiModel = authDataUiModel,
                onLogInButtonClick = {
                    viewEventHandler.handle(AuthViewEvent.LogIn(it))
                },
                onDeclineAuthorizationButtonClick = {
                    viewEventHandler.handle(AuthViewEvent.DeclineAuthorization)
                },
            )
        }
        is AuthViewState.AuthorizationSuccessful -> LaunchedEffect(true) {
            authorizationSuccessfulAction.invoke()
        }
        is AuthViewState.AuthorizationDeclined -> LaunchedEffect(true) {
            authorizationDeclinedAction.invoke()
        }
        is AuthViewState.Error -> with(it) {
            ErrorState(throwable, onActionButtonClick)
        }
    }
}

@Composable
private fun LoadingState() {
    ThemedCircularProgressBar()
}

@Composable
private fun DataState(
    authDataUiModel: AuthDataUiModel,
    onLogInButtonClick: (AuthDataUiModel) -> Unit,
    onDeclineAuthorizationButtonClick: () -> Unit,
) {
    AuthView(
        authDataUiModel = authDataUiModel,
        userNameValidator = UserNameValidator(),
        passwordValidator = PasswordValidator(),
        onLogInButtonClick = onLogInButtonClick,
        onDeclineAuthorizationButtonClick = onDeclineAuthorizationButtonClick,
    )
}

@Composable
private fun ErrorState(
    throwable: Throwable,
    onActionButtonClick: () -> Unit,
) {
    ErrorCard(
        descriptionTextWrapper = throwable.toTextWrapper(),
        onActionButtonClick = onActionButtonClick,
    )
}
