package ru.konohovalex.feature.notes.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.subscribe
import kotlinx.coroutines.launch
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.data.model.PaginationData
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.domain.usecase.GetNotesUseCase
import ru.konohovalex.feature.notes.presentation.list.model.NoteListState
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import javax.inject.Inject

@HiltViewModel
internal class NoteListViewModel
@Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val noteDomainModelToNotePreviewUiModelMapper: Mapper<NoteDomainModel, NotePreviewUiModel>,
) : ViewModel() {
    private val _state = MutableLiveData<NoteListState>(NoteListState.Loading)
    val state: LiveData<NoteListState> = _state

    private val _notes = mutableListOf<NotePreviewUiModel>()

    private var _paginationData = PaginationData(
        pageSize = 25,
        pageNumber = 0,
    )

    init {
        getNextNotes()
    }

    fun getNextNotes() = getNotesUseCase.invoke(_paginationData)
        .onEach {
            when (it) {
                is OperationStatus.Pending, is OperationStatus.Processing -> handleLoadingState(it.inputData)
                is OperationStatus.Completed -> handleDataLoaded(
                    it.inputData,
                    it.outputData,
                )
                is OperationStatus.Error -> handleError(
                    it.inputData,
                    it.throwable,
                )
            }
        }
        .launchIn(viewModelScope)

    fun refresh() {
    }

    private fun handleLoadingState(inputData: PaginationData) {
        _state.value = NoteListState.Loading
    }

    private fun handleDataLoaded(paginationData: PaginationData, notes: List<NoteDomainModel>) {
        _paginationData = paginationData
        _notes.addAll(notes.map(noteDomainModelToNotePreviewUiModelMapper))
        _state.value = NoteListState.Data(_notes)
    }

    private fun handleError(paginationData: PaginationData, throwable: Throwable) {
        _paginationData = paginationData
        _state.value = NoteListState.Error(throwable)
    }
}
