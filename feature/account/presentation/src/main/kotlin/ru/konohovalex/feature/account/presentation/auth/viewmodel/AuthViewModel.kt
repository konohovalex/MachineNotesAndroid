package ru.konohovalex.feature.account.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.presentation.extension.setErrorViewState
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.domain.auth.model.SignUpDataDomainModel
import ru.konohovalex.feature.account.domain.auth.model.CredentialsDomainModel
import ru.konohovalex.feature.account.domain.auth.model.PasswordDomainModel
import ru.konohovalex.feature.account.domain.auth.model.UserNameDomainModel
import ru.konohovalex.feature.account.domain.auth.usecase.SingInUseCase
import ru.konohovalex.feature.account.domain.auth.usecase.SignUpUseCase
import ru.konohovalex.feature.account.domain.profile.usecase.ObserveProfileUseCase
import ru.konohovalex.feature.account.presentation.R
import ru.konohovalex.feature.account.presentation.auth.model.CredentialsUiModel
import ru.konohovalex.feature.account.presentation.auth.model.AuthViewEvent
import ru.konohovalex.feature.account.presentation.auth.model.AuthViewState
import javax.inject.Inject

@HiltViewModel
internal class AuthViewModel
@Inject constructor(
    private val observeProfileUseCase: ObserveProfileUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val singInUseCase: SingInUseCase,
    private val credentialsUiModelToSignUpDataDomainModelMapper: Mapper<CredentialsUiModel, SignUpDataDomainModel>,
) : ViewModel(),
    ViewEventHandler<AuthViewEvent>,
    ViewStateProvider<AuthViewState> by ViewStateProviderDelegate(AuthViewState.Idle) {
    private var _isFirstLaunch = true

    private var observeProfileJob: Job? = null
    private var signUpJob: Job? = null
    private var signInJob: Job? = null

    override fun handle(viewEvent: AuthViewEvent) {
        when (viewEvent) {
            is AuthViewEvent.Init -> init(viewEvent.isFirstLaunch)
            is AuthViewEvent.SignUp -> signUp(viewEvent.credentialsUiModel)
            is AuthViewEvent.SignIn -> signIn(viewEvent.credentialsUiModel)
            is AuthViewEvent.DeclineAuthorization -> declineAuthorization()
            is AuthViewEvent.ErrorProcessed -> errorProcessed()
        }
    }

    private fun init(isFirstLaunch: Boolean) {
        _isFirstLaunch = isFirstLaunch
        observeProfileJob?.cancel()
        observeProfileJob = viewModelScope.launch {
            observeProfileUseCase.invoke()
                .onEach {
                    when (it) {
                        is OperationStatus.Plain.Pending -> setLoadingState()
                        is OperationStatus.Plain.Processing -> {}
                        is OperationStatus.Plain.Completed -> setInitialDataState()
                        is OperationStatus.Plain.Error -> setErrorViewState(
                            AuthViewState.Error(it.throwable) {
                                init(isFirstLaunch)
                            }
                        )
                    }
                }
                .collect()
        }
    }

    private fun setLoadingState() {
        setViewState(AuthViewState.Loading)
    }

    private fun setInitialDataState() {
        observeProfileJob?.cancel()

        setViewState(
            AuthViewState.Data.empty(
                declineAuthorizationButtonTextResId = getDeclineAuthorizationButtonTextResId(),
            )
        )
    }

    private fun getDeclineAuthorizationButtonTextResId(): Int = if (!_isFirstLaunch) R.string.back else R.string.skip

    private fun signUp(credentialsUiModel: CredentialsUiModel?) {
        signUpJob?.cancel()
        val signUpDataDomainModel = credentialsUiModel?.let(credentialsUiModelToSignUpDataDomainModelMapper::invoke)
            ?: SignUpDataDomainModel(null)
        signUpJob = signUpUseCase.invoke(signUpDataDomainModel)
            .onEach {
                when (it) {
                    is OperationStatus.WithInputData.Pending -> setLoadingState()
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> setAuthorizationSuccessfulState()
                    is OperationStatus.WithInputData.Error -> setDataWithErrorState(credentialsUiModel, it.throwable) {
                        signUp(credentialsUiModel)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun signIn(credentialsUiModel: CredentialsUiModel) {
        signInJob?.cancel()
        val credentialsDomainModel = CredentialsDomainModel(
            userNameDomainModel = UserNameDomainModel(credentialsUiModel.userNameUiModel.value),
            passwordDomainModel = PasswordDomainModel(credentialsUiModel.passwordUiModel.value),
        )
        signInJob = singInUseCase.invoke(credentialsDomainModel)
            .onEach {
                when (it) {
                    is OperationStatus.WithInputData.Pending -> setLoadingState()
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> setAuthorizationSuccessfulState()
                    is OperationStatus.WithInputData.Error -> setDataWithErrorState(credentialsUiModel, it.throwable) {
                        signIn(credentialsUiModel)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun setAuthorizationSuccessfulState() {
        setViewState(AuthViewState.AuthorizationSuccessful)
    }

    private fun declineAuthorization() {
        if (!_isFirstLaunch) setAuthorizationSuccessfulState()
        else signUp(null)
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

    private fun setDataWithErrorState(
        credentialsUiModel: CredentialsUiModel?,
        throwable: Throwable,
        onErrorActionButtonClick: () -> Unit,
    ) {
        setViewState(
            credentialsUiModel?.let {
                AuthViewState.Data(
                    credentialsUiModel = credentialsUiModel,
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
