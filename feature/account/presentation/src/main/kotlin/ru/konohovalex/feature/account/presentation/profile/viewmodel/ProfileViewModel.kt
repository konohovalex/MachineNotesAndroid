package ru.konohovalex.feature.account.presentation.profile.viewmodel

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
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.domain.auth.usecase.DeleteAccountUseCase
import ru.konohovalex.feature.account.domain.auth.usecase.LogOutUseCase
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import ru.konohovalex.feature.account.domain.profile.usecase.ObserveProfileUseCase
import ru.konohovalex.feature.account.presentation.profile.model.ProfileIsNullError
import ru.konohovalex.feature.account.presentation.profile.model.ProfileUiModel
import ru.konohovalex.feature.account.presentation.profile.model.ProfileViewEvent
import ru.konohovalex.feature.account.presentation.profile.model.ProfileViewState
import ru.konohovalex.feature.notes.domain.usecase.DeleteAllNotesUseCase
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel
@Inject constructor(
    private val observeProfileUseCase: ObserveProfileUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val deleteAllNotesUseCase: DeleteAllNotesUseCase,
    private val profileDomainModelToProfileUiModelMapper: Mapper<ProfileDomainModel, ProfileUiModel>,
) : ViewModel(),
    ViewEventHandler<ProfileViewEvent>,
    ViewStateProvider<ProfileViewState> by ViewStateProviderDelegate(ProfileViewState.Idle) {
    private var observeProfileJob: Job? = null
    private var logOutJob: Job? = null
    private var deleteAccountJob: Job? = null
    private var deleteAllNotesJob: Job? = null

    override fun handle(viewEvent: ProfileViewEvent) {
        when (viewEvent) {
            ProfileViewEvent.GetProfile -> getProfile()
            ProfileViewEvent.LogOut -> logOut()
            ProfileViewEvent.DeleteAccount -> deleteAccount()
            ProfileViewEvent.DeleteAllNotes -> deleteAllNotes()
        }
    }

    private fun getProfile() {
        observeProfileJob?.cancel()
        observeProfileJob = viewModelScope.launch {
            observeProfileUseCase.invoke()
                .onEach {
                    when (it) {
                        is OperationStatus.Plain.Pending -> setLoadingState()
                        is OperationStatus.Plain.Processing -> {}
                        is OperationStatus.Plain.Completed -> setDataState(it.outputData)
                        is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                    }
                }
                .collect()
        }
    }

    private fun setLoadingState() {
        setViewState(ProfileViewState.Loading)
    }

    private fun setDataState(profileDomainModel: ProfileDomainModel?) {
        val profileUiModel = profileDomainModel?.let(profileDomainModelToProfileUiModelMapper::invoke)
        profileUiModel?.let {
            setViewState(ProfileViewState.Data(profileUiModel))
        } ?: setErrorState(ProfileIsNullError())
    }

    private fun logOut() {
        logOutJob?.cancel()
        logOutJob = logOutUseCase.invoke()
            .onEach {
                when (it) {
                    is OperationStatus.Plain.Pending -> setLoadingState()
                    is OperationStatus.Plain.Processing,
                    is OperationStatus.Plain.Completed,
                    -> {
                    }
                    is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun deleteAccount() {
        deleteAccountJob?.cancel()
        deleteAccountJob = deleteAccountUseCase.invoke()
            .onEach {
                when (it) {
                    is OperationStatus.Plain.Pending -> setLoadingState()
                    is OperationStatus.Plain.Processing,
                    is OperationStatus.Plain.Completed,
                    -> {
                    }
                    is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun deleteAllNotes() {
        withViewState(ProfileViewState.Data::class.java) { viewState ->
            deleteAllNotesJob?.cancel()
            deleteAllNotesJob = deleteAllNotesUseCase.invoke()
                .onEach {
                    when (it) {
                        is OperationStatus.Plain.Pending -> setLoadingState()
                        is OperationStatus.Plain.Processing -> {}
                        // tbd - there must be ObserveNotesUseCase
                        is OperationStatus.Plain.Completed -> setViewState(viewState)
                        is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun setErrorState(throwable: Throwable) {
        setViewState(ProfileViewState.Error(throwable))
    }
}
