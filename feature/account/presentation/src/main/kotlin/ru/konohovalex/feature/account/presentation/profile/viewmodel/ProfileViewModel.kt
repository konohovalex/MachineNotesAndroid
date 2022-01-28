package ru.konohovalex.feature.account.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.account.domain.profile.model.ProfileDomainModel
import ru.konohovalex.feature.account.domain.profile.usecase.GetProfileUseCase
import ru.konohovalex.feature.account.presentation.profile.model.ProfileIsNullError
import ru.konohovalex.feature.account.presentation.profile.model.ProfileUiModel
import ru.konohovalex.feature.account.presentation.profile.model.ProfileViewEvent
import ru.konohovalex.feature.account.presentation.profile.model.ProfileViewState
import ru.konohovalex.feature.notes.domain.usecase.DeleteAllNotesUseCase
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel
@Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val deleteAllNotesUseCase: DeleteAllNotesUseCase,
    private val profileDomainModelToProfileUiModelMapper: Mapper<ProfileDomainModel, ProfileUiModel>,
) : ViewModel(),
    ViewEventHandler<ProfileViewEvent>,
    ViewStateProvider<ProfileViewState> by ViewStateProviderDelegate(ProfileViewState.Idle) {
    override fun handle(viewEvent: ProfileViewEvent) {
        when (viewEvent) {
            ProfileViewEvent.GetProfile -> getProfile()
            ProfileViewEvent.LogOut -> logOut()
            ProfileViewEvent.DeleteAccount -> deleteAccount()
            ProfileViewEvent.DeleteAllNotes -> deleteAllNotes()
        }
    }

    private fun getProfile() {
        getProfileUseCase.invoke()
            .onEach {
                when (it) {
                    is OperationStatus.Plain.Pending -> setLoadingState()
                    is OperationStatus.Plain.Processing -> {}
                    is OperationStatus.Plain.Completed -> setDataState(it.outputData)
                    is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                }
            }
            .launchIn(viewModelScope)
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
        // tbd
    }

    private fun deleteAccount() {
        // tbd
    }

    private fun deleteAllNotes() {
        // tbd
    }

    private fun setErrorState(throwable: Throwable) {
        setViewState(ProfileViewState.Error(throwable))
    }
}
