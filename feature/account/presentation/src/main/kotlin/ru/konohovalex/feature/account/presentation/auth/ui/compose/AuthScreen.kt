package ru.konohovalex.feature.account.presentation.auth.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
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
    // tbd solve keyboard problem
    val viewState by viewStateProvider.viewState.observeAsState()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            viewState.safeCast<AuthViewState.Data>()?.let {
                SnackbarHost(
                    throwable = it.throwable,
                    errorAction = {
                        viewEventHandler.handle(AuthViewEvent.ErrorProcessed)
                        it.onErrorActionButtonClick?.invoke()
                    },
                    snackbarHostState = scaffoldState.snackbarHostState,
                    coroutineScope = rememberCoroutineScope(),
                    onDismissed = {
                        viewEventHandler.handle(AuthViewEvent.ErrorProcessed)
                    },
                )
            }
        },
        backgroundColor = Theme.palette.backgroundColor,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
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
                    )
                }
                is AuthViewState.AuthorizationSuccessful -> LaunchedEffect(true) {
                    authorizationSuccessfulAction.invoke()
                }
                is AuthViewState.AuthorizationDeclined -> LaunchedEffect(true) {
                    authorizationDeclinedAction.invoke()
                }
                is AuthViewState.Error -> with(viewState as AuthViewState.Error) {
                    ErrorState(throwable, onActionButtonClick)
                }
            }
        }
    }
}

@Composable
private fun SnackbarHost(
    throwable: Throwable?,
    errorAction: (() -> Unit)?,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    onDismissed: () -> Unit,
) {
    throwable?.let {
        ThemedSnackbarHost(
            messageTextWrapper = it.toTextWrapper(),
            snackbarHostState = snackbarHostState,
            onActionButtonClick = errorAction,
            coroutineScope = coroutineScope,
            onDismissed = onDismissed,
        )
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
