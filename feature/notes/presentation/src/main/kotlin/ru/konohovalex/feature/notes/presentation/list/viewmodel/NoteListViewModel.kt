package ru.konohovalex.feature.notes.presentation.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.presentation.extension.setErrorViewState
import ru.konohovalex.core.utils.extension.asError
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.notes.domain.extension.isValidNotesFilterValue
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.domain.usecase.DeleteNoteUseCase
import ru.konohovalex.feature.notes.domain.usecase.ObserveNotesUseCase
import ru.konohovalex.feature.notes.presentation.list.model.NoteListViewEvent
import ru.konohovalex.feature.notes.presentation.list.model.NoteListViewState
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import javax.inject.Inject

@HiltViewModel
internal class NoteListViewModel
@Inject constructor(
    private val observeNotesUseCase: ObserveNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val noteDomainModelToNotePreviewUiModelMapper: Mapper<NoteDomainModel, NotePreviewUiModel>,
) : ViewModel(),
    ViewEventHandler<NoteListViewEvent>,
    ViewStateProvider<NoteListViewState> by ViewStateProviderDelegate(NoteListViewState.Idle) {
    private var getNotesJob: Job? = null
    private var deleteNoteJob: Job? = null

    private var _filter: String = ""

    override fun handle(viewEvent: NoteListViewEvent) {
        when (viewEvent) {
            is NoteListViewEvent.GetNotes -> getNextNotes(viewEvent.filter)
            is NoteListViewEvent.DeleteNote -> deleteNote(viewEvent.noteId)
            NoteListViewEvent.ErrorProcessed -> errorProcessed()
        }
    }

    private fun getNextNotes(filter: String) {
        val previousFilterWasValid = _filter.isValidNotesFilterValue()
        _filter = filter
        val filterIsValid = _filter.isValidNotesFilterValue()
        val needToLaunchGetNotes = filterIsValid
                || !filterIsValid && previousFilterWasValid
                || _filter.isEmpty() && viewState.value is NoteListViewState.Idle
        if (needToLaunchGetNotes) {
            getNotesJob?.cancel()
            getNotesJob = viewModelScope.launch {
                if (filterIsValid) delay(500)
                getNextNotesFlow(if (filterIsValid) _filter else "").collect()
            }
        }
    }

    private suspend fun getNextNotesFlow(filter: String) =
        observeNotesUseCase.invoke().onEach {
            when (it) {
                is OperationStatus.Plain.Pending -> setLoadingState()
                is OperationStatus.Plain.Processing -> {}
                is OperationStatus.Plain.Completed -> setDataState(it.outputData)
                is OperationStatus.Plain.Error -> setErrorViewState(
                    NoteListViewState.Error(it.throwable) {
                        getNextNotes(filter)
                    }
                )
            }
        }

    private fun setLoadingState() {
        setViewState(NoteListViewState.Loading)
    }

    private fun setDataState(noteDomainModelList: List<NoteDomainModel>) {
        val notesUiModelList = noteDomainModelList.map(noteDomainModelToNotePreviewUiModelMapper)
        setViewState(NoteListViewState.Data(notesUiModelList))
    }

    private fun deleteNote(noteId: String) {
        deleteNoteJob?.cancel()
        deleteNoteJob = deleteNoteUseCase.invoke(noteId)
            .onEach {
                it.asError()?.let(::showNoteDeletionErrorMessage)
            }
            .launchIn(viewModelScope)
    }

    private fun showNoteDeletionErrorMessage(operationStatus: OperationStatus.Error<Unit>) {
        withViewState(NoteListViewState.Data::class.java) {
            setViewState(
                it.copy(
                    throwable = operationStatus.throwable,
                )
            )
        }
    }

    private fun errorProcessed() {
        withViewState(NoteListViewState.Data::class.java) {
            setViewState(
                it.copy(
                    throwable = null,
                )
            )
        }
    }
}
