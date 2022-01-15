package ru.konohovalex.feature.notes.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

//    private val _notes = mutableListOf<NotePreviewUiModel>()
    private var _notes = listOf<NotePreviewUiModel>()

    private var _filter: String? = null
    private var _paginationData = PaginationData(
        pageSize = 25,
        pageNumber = 0,
    )

    private var getNotesJob: Job? = null
    private var filterJob: Job? = null

    init {
        getNextNotes()
    }

    fun getNextNotes() {
        getNotesJob?.cancel()
        getNotesJob = getNotesUseCase.invoke(_filter, _paginationData)
            .onEach {
                when (it) {
                    is OperationStatus.Pending, is OperationStatus.Processing -> handleLoadingState(
                        it.inputData.first,
                        it.inputData.second,
                    )
                    is OperationStatus.Completed -> handleDataLoaded(
                        it.inputData.first,
                        it.inputData.second,
                        it.outputData,
                    )
                    is OperationStatus.Error -> handleError(
                        it.inputData.first,
                        it.inputData.second,
                        it.throwable,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun filterNotes(filter: String?) {
        if (filter != _filter) {
            _filter = filter
            filterJob?.cancel()
            filterJob = viewModelScope.launch {
                delay(1000)
                getNextNotes()
            }
        }
    }

    fun refresh() {
    }

    private fun handleLoadingState(
        filter: String?,
        paginationData: PaginationData,
    ) {
        _filter = filter
        _paginationData = paginationData
        _state.value = NoteListState.Loading
    }

    private fun handleDataLoaded(
        filter: String?,
        paginationData: PaginationData,
        notes: List<NoteDomainModel>,
    ) {
        _filter = filter
        _paginationData = paginationData
//        _notes.addAll(notes.map(noteDomainModelToNotePreviewUiModelMapper))
        _notes = notes.map(noteDomainModelToNotePreviewUiModelMapper)
        _state.value = NoteListState.Data(_notes)
    }

    private fun handleError(
        filter: String?,
        paginationData: PaginationData,
        throwable: Throwable,
    ) {
        _filter = filter
        _paginationData = paginationData
        _state.value = NoteListState.Error(throwable)
    }
}
