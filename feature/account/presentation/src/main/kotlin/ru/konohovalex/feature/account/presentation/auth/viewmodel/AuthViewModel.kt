package ru.konohovalex.feature.account.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.presentation.extension.setErrorViewState
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

    private var observeProfileJob: Job? = null
    private var logInJob: Job? = null

    override fun handle(viewEvent: AuthViewEvent) {
        when (viewEvent) {
            is AuthViewEvent.Init -> init()
            is AuthViewEvent.LogIn -> logIn(viewEvent.authDataUiModel)
            is AuthViewEvent.DeclineAuthorization -> declineAuthorization()
            is AuthViewEvent.ErrorProcessed -> errorProcessed()
        }
    }

    private fun init() {
        observeProfileJob?.cancel()
        observeProfileJob = viewModelScope.launch {
            observeProfileUseCase.invoke()
                .onEach {
                    when (it) {
                        is OperationStatus.Plain.Pending -> setLoadingState()
                        is OperationStatus.Plain.Processing -> {}
                        is OperationStatus.Plain.Completed -> setInitialDataState(it.outputData)
                        is OperationStatus.Plain.Error -> setErrorViewState(
                            AuthViewState.Error(it.throwable) {
                                init()
                            }
                        )
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

    // tbd will always be "Back", fix profile observation
    private fun getDeclineAuthorizationButtonTextResId(): Int = _initialProfile?.let { R.string.back } ?: R.string.skip

    private fun logIn(authDataUiModel: AuthDataUiModel?) {
        logInJob?.cancel()
        val authDataDomainModel = authDataUiModel?.let(authDataUiModelToAuthDataDomainModelMapper::invoke)
        logInJob = logInUseCase.invoke(authDataDomainModel)
            .onEach {
                when (it) {
                    is OperationStatus.WithInputData.Pending -> setLoadingState()
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> setAuthorizationSuccessfulState()
                    is OperationStatus.WithInputData.Error -> setLogInErrorState(authDataUiModel, it.throwable) {
                        logIn(authDataUiModel)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun setAuthorizationSuccessfulState() {
        setViewState(AuthViewState.AuthorizationSuccessful)
    }

    private fun declineAuthorization() {
        if (_initialProfile != null) setAuthorizationSuccessfulState()
        else logIn(null)
    }

    private fun errorProcessed() {
        withViewState(AuthViewState.Data::class.java) {
            setViewState(
                it.copy(
                    throwable = null,
                    onErrorActionButtonClick = null,
                )
            )
        }
    }

    private fun setLogInErrorState(
        authDataUiModel: AuthDataUiModel?,
        throwable: Throwable,
        onErrorActionButtonClick: () -> Unit,
    ) {
        setViewState(
            authDataUiModel?.let {
                AuthViewState.Data(
                    authDataUiModel = authDataUiModel,
                    throwable = throwable,
                    onErrorActionButtonClick = onErrorActionButtonClick,
                )
            } ?: AuthViewState.Data.empty(
                declineAuthorizationButtonTextResId = getDeclineAuthorizationButtonTextResId(),
                throwable = throwable,
                onErrorActionButtonClick = onErrorActionButtonClick,
            )
        )
    }
}
