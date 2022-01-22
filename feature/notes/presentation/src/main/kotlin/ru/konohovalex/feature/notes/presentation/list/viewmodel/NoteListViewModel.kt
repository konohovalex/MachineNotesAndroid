package ru.konohovalex.feature.notes.presentation.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.data.model.PaginationData
import ru.konohovalex.core.presentation.arch.viewevent.ViewEventHandler
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProvider
import ru.konohovalex.core.presentation.arch.viewstate.ViewStateProviderDelegate
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.domain.usecase.GetNotesUseCase
import ru.konohovalex.feature.notes.domain.utils.isValidNotesFilterValue
import ru.konohovalex.feature.notes.presentation.list.model.NoteListScreenViewEvent
import ru.konohovalex.feature.notes.presentation.list.model.NoteListViewState
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import javax.inject.Inject

@HiltViewModel
internal class NoteListViewModel
@Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val noteDomainModelToNotePreviewUiModelMapper: Mapper<NoteDomainModel, NotePreviewUiModel>,
) : ViewModel(),
    ViewEventHandler<NoteListScreenViewEvent>,
    ViewStateProvider<NoteListViewState> by ViewStateProviderDelegate(NoteListViewState.Idle) {
    private var getNotesJob: Job? = null

    private var _filter: String = ""

    override fun handle(viewEvent: NoteListScreenViewEvent) {
        when (viewEvent) {
            is NoteListScreenViewEvent.GetNotes -> getNextNotes(viewEvent.filter)
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

    private fun getNextNotesFlow(filter: String) =
        getNotesUseCase.invoke(
            filter = filter,
            paginationData = PaginationData(
                pageSize = 20,
                pageNumber = 0,
            )
        ).onEach {
            when (it) {
                is OperationStatus.WithInputData.Pending -> setLoadingState()
                is OperationStatus.WithInputData.Processing -> {}
                is OperationStatus.WithInputData.Completed -> setDataState(it.outputData)
                is OperationStatus.WithInputData.Error -> setErrorState(it.throwable)
            }
        }

    private fun setLoadingState() {
        setViewState(NoteListViewState.Loading)
    }

    private fun setDataState(noteDomainModelList: List<NoteDomainModel>) {
        val notesUiModelList = noteDomainModelList.map(noteDomainModelToNotePreviewUiModelMapper)
        setViewState(NoteListViewState.Data(notesUiModelList))
    }

    private fun setErrorState(throwable: Throwable) {
        setViewState(NoteListViewState.Error(throwable))
    }
}
