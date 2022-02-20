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
import ru.konohovalex.core.presentation.extension.setErrorViewState
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.domain.auth.usecase.DeleteAccountUseCase
import ru.konohovalex.feature.account.domain.auth.usecase.SignOutUseCase
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import ru.konohovalex.feature.account.domain.profile.usecase.ObserveProfileUseCase
import ru.konohovalex.feature.account.presentation.profile.model.ProfileUiModel
import ru.konohovalex.feature.account.presentation.profile.model.ProfileViewEvent
import ru.konohovalex.feature.account.presentation.profile.model.ProfileViewState
import ru.konohovalex.feature.notes.domain.model.NotesDeletionMode
import ru.konohovalex.feature.notes.domain.usecase.DeleteAllNotesUseCase
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel
@Inject constructor(
    private val observeProfileUseCase: ObserveProfileUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val deleteAllNotesUseCase: DeleteAllNotesUseCase,
    private val profileDomainModelToProfileUiModelMapper: Mapper<ProfileDomainModel, ProfileUiModel>,
) : ViewModel(),
    ViewEventHandler<ProfileViewEvent>,
    ViewStateProvider<ProfileViewState> by ViewStateProviderDelegate(ProfileViewState.Idle) {
    private var observeProfileJob: Job? = null
    private var signOutJob: Job? = null
    private var deleteAccountJob: Job? = null
    private var deleteAllNotesJob: Job? = null

    override fun handle(viewEvent: ProfileViewEvent) {
        when (viewEvent) {
            ProfileViewEvent.GetProfile -> getProfile()
            ProfileViewEvent.SignOut -> signOut()
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
                        is OperationStatus.Plain.Error -> setErrorViewState(
                            ProfileViewState.Error(it.throwable) {
                                getProfile()
                            }
                        )
                    }
                }
                .collect()
        }
    }

    private fun setLoadingState() {
        setViewState(ProfileViewState.Loading)
    }

    private fun setDataState(profileDomainModel: ProfileDomainModel) {
        val profileUiModel = profileDomainModelToProfileUiModelMapper.invoke(profileDomainModel)
        setViewState(ProfileViewState.Data(profileUiModel))
    }

    private fun signOut() {
        signOutJob?.cancel()
        signOutJob = signOutUseCase.invoke()
            .onEach {
                when (it) {
                    is OperationStatus.Plain.Pending -> setLoadingState()
                    is OperationStatus.Plain.Processing, is OperationStatus.Plain.Completed -> {}
                    is OperationStatus.Plain.Error -> setErrorViewState(
                        ProfileViewState.Error(it.throwable) {
                            signOut()
                        }
                    )
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
                    is OperationStatus.Plain.Processing, is OperationStatus.Plain.Completed -> {}
                    is OperationStatus.Plain.Error -> setErrorViewState(
                        ProfileViewState.Error(it.throwable) {
                            deleteAccount()
                        }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun deleteAllNotes() {
        withViewState(ProfileViewState.Data::class.java) { viewState ->
            deleteAllNotesJob?.cancel()
            deleteAllNotesJob = deleteAllNotesUseCase.invoke(NotesDeletionMode.LOCALLY_AND_REMOTE)
                .onEach {
                    when (it) {
                        is OperationStatus.Plain.Pending -> setLoadingState()
                        is OperationStatus.Plain.Processing -> {}
                        is OperationStatus.Plain.Completed -> setViewState(viewState)
                        is OperationStatus.Plain.Error -> setErrorViewState(
                            ProfileViewState.Error(it.throwable) {
                                deleteAllNotes()
                            }
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}
