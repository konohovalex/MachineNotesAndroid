package ru.konohovalex.feature.account.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.domain.auth.model.AuthDataDomainModel
import ru.konohovalex.feature.account.domain.auth.usecase.LogInUseCase
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import ru.konohovalex.feature.account.domain.profile.usecase.ObserveProfileUseCase
import ru.konohovalex.feature.account.presentation.R
import ru.konohovalex.feature.account.presentation.auth.model.AuthDataUiModel
import ru.konohovalex.feature.account.presentation.auth.model.AuthViewEvent
import ru.konohovalex.feature.account.presentation.auth.model.AuthViewState
import javax.inject.Inject

@HiltViewModel
internal class AuthViewModel
@Inject constructor(
    private val observeProfileUseCase: ObserveProfileUseCase,
    private val logInUseCase: LogInUseCase,
    private val authDataUiModelToAuthDataDomainModelMapper: Mapper<AuthDataUiModel, AuthDataDomainModel>,
) : ViewModel(),
    ViewEventHandler<AuthViewEvent>,
    ViewStateProvider<AuthViewState> by ViewStateProviderDelegate(AuthViewState.Idle) {
    private var _initialProfile: ProfileDomainModel? = null

    override fun handle(viewEvent: AuthViewEvent) {
        when (viewEvent) {
            is AuthViewEvent.Init -> init()
            is AuthViewEvent.LogIn -> logIn(viewEvent.authDataUiModel)
            is AuthViewEvent.DeclineAuthorization -> declineAuthorization()
        }
    }

    private fun init() {
        viewModelScope.launch {
            observeProfileUseCase.invoke()
                .onEach {
                    when (it) {
                        is OperationStatus.Plain.Pending -> setLoadingState()
                        is OperationStatus.Plain.Processing -> {}
                        is OperationStatus.Plain.Completed -> setInitialDataState(it.outputData)
                        is OperationStatus.Plain.Error -> setInitErrorState(it.throwable)
                    }
                }
                .take(1)
                .collect()
        }
    }

    private fun setLoadingState() {
        setViewState(AuthViewState.Loading)
    }

    private fun setInitialDataState(profileDomainModel: ProfileDomainModel?) {
        _initialProfile = profileDomainModel
        setViewState(
            AuthViewState.Data.empty(
                declineAuthorizationButtonTextResId = getDeclineAuthorizationButtonTextResId(),
            )
        )
    }

    private fun getDeclineAuthorizationButtonTextResId(): Int = _initialProfile?.let { R.string.back } ?: R.string.skip

    private fun logIn(authDataUiModel: AuthDataUiModel?) {
        val authDataDomainModel = authDataUiModel?.let(authDataUiModelToAuthDataDomainModelMapper::invoke)
        logInUseCase.invoke(authDataDomainModel)
            .onEach {
                when (it) {
                    is OperationStatus.WithInputData.Pending -> setLoadingState()
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> setAuthorizationSuccessfulState()
                    is OperationStatus.WithInputData.Error -> setLogInErrorState(authDataUiModel, it.throwable)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun setAuthorizationSuccessfulState() {
        setViewState(AuthViewState.AuthorizationSuccessful)
    }

    private fun declineAuthorization() {
        if (_initialProfile != null) setAuthorizationDeclinedState()
        else logIn(null)
    }

    private fun setAuthorizationDeclinedState() {
        setViewState(AuthViewState.AuthorizationDeclined)
    }

    private fun setInitErrorState(throwable: Throwable) {
        setViewState(AuthViewState.Error(throwable))
    }

    private fun setLogInErrorState(
        authDataUiModel: AuthDataUiModel?,
        throwable: Throwable,
    ) {
        withViewState(AuthViewState.Data::class.java) { viewState ->
            setViewState(
                authDataUiModel?.let {
                    viewState.copy(
                        authDataUiModel = authDataUiModel,
                        throwable = throwable,
                    )
                } ?: AuthViewState.Data.empty(
                    declineAuthorizationButtonTextResId = getDeclineAuthorizationButtonTextResId(),
                    throwable = throwable,
                )
            )
        }
    }
}
