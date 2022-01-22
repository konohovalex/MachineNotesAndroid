package ru.konohovalex.feature.notes.presentation.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.domain.usecase.CreateNoteUseCase
import ru.konohovalex.feature.notes.domain.usecase.GetNoteDetailsUseCase
import ru.konohovalex.feature.notes.presentation.details.model.NoteDetailsViewEvent
import ru.konohovalex.feature.notes.presentation.details.model.NoteDetailsViewState
import ru.konohovalex.feature.notes.presentation.details.model.NoteUiModel
import javax.inject.Inject

@HiltViewModel
internal class NoteDetailsViewModel
@Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase,
    private val getNoteDetailsUseCase: GetNoteDetailsUseCase,
    private val noteDomainModelToNoteUiModelMapper: Mapper<NoteDomainModel, NoteUiModel>,
) : ViewModel(),
    ViewEventHandler<NoteDetailsViewEvent>,
    ViewStateProvider<NoteDetailsViewState> by ViewStateProviderDelegate(NoteDetailsViewState.Idle) {
    override fun handle(viewEvent: NoteDetailsViewEvent) {
        when (viewEvent) {
            is NoteDetailsViewEvent.GetNoteDetails -> getNoteDetails(viewEvent.noteId)
        }
    }

    private fun getNoteDetails(noteId: String?) {
        (noteId?.let(getNoteDetailsUseCase::invoke)
            ?.onEach {
                when (it) {
                    is OperationStatus.WithInputData.Pending -> setLoadingState()
                    is OperationStatus.WithInputData.Processing -> {}
                    is OperationStatus.WithInputData.Completed -> setDataState(it.outputData)
                    is OperationStatus.WithInputData.Error -> setErrorState(it.throwable)
                }
            } ?: createNoteUseCase.invoke()
            .onEach {
                when (it) {
                    is OperationStatus.Plain.Pending -> setLoadingState()
                    is OperationStatus.Plain.Processing -> {}
                    is OperationStatus.Plain.Completed -> setDataState(it.outputData)
                    is OperationStatus.Plain.Error -> setErrorState(it.throwable)
                }
            }).launchIn(viewModelScope)
    }

    private fun setLoadingState() {
        setViewState(NoteDetailsViewState.Loading)
    }

    private fun setDataState(noteDomainModel: NoteDomainModel) {
        val noteUiModel = noteDomainModelToNoteUiModelMapper.invoke(noteDomainModel)
        setViewState(NoteDetailsViewState.Data(noteUiModel))
    }

    private fun setErrorState(throwable: Throwable) {
        setViewState(NoteDetailsViewState.Error(throwable))
    }
}
