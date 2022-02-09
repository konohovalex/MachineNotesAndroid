package ru.konohovalex.feature.notes.presentation.details.viewmodel

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
import ru.konohovalex.core.utils.model.DateTime
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.domain.usecase.CreateNoteUseCase
import ru.konohovalex.feature.notes.domain.usecase.GetNoteDetailsUseCase
import ru.konohovalex.feature.notes.domain.usecase.UpdateNoteUseCase
import ru.konohovalex.feature.notes.presentation.details.model.NoteContentUiModel
import ru.konohovalex.feature.notes.presentation.details.model.NoteDetailsViewEvent
import ru.konohovalex.feature.notes.presentation.details.model.NoteDetailsViewState
import ru.konohovalex.feature.notes.presentation.details.model.NoteUiModel
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class NoteDetailsViewModel
@Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase,
    private val getNoteDetailsUseCase: GetNoteDetailsUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val noteDomainModelToNoteUiModelMapper: Mapper<NoteDomainModel, NoteUiModel>,
    private val noteUiModelToNoteDomainModelMapper: Mapper<NoteUiModel, NoteDomainModel>,
) : ViewModel(),
    ViewEventHandler<NoteDetailsViewEvent>,
    ViewStateProvider<NoteDetailsViewState> by ViewStateProviderDelegate(NoteDetailsViewState.Idle) {
    private var getNoteDetailsOrCreateNoteJob: Job? = null
    private var updateNoteJob: Job? = null

    override fun handle(viewEvent: NoteDetailsViewEvent) {
        when (viewEvent) {
            is NoteDetailsViewEvent.GetNoteDetails -> getNoteDetails(viewEvent.noteId)
            is NoteDetailsViewEvent.NoteContentChanged -> noteContentChanged(viewEvent.noteContent)
        }
    }

    private fun getNoteDetails(noteId: String?) {
        getNoteDetailsOrCreateNoteJob?.cancel()
        getNoteDetailsOrCreateNoteJob = (
                noteId?.let(getNoteDetailsUseCase::invoke)
                    ?.onEach {
                        when (it) {
                            is OperationStatus.WithInputData.Pending -> setLoadingState()
                            is OperationStatus.WithInputData.Processing -> {}
                            is OperationStatus.WithInputData.Completed -> setDataState(it.outputData)
                            is OperationStatus.WithInputData.Error -> setErrorViewState(
                                NoteDetailsViewState.Error(it.throwable) {
                                    getNoteDetails(noteId)
                                }
                            )
                        }
                    }
                    ?: createNoteUseCase.invoke()
                        .onEach {
                            when (it) {
                                is OperationStatus.Plain.Pending -> setLoadingState()
                                is OperationStatus.Plain.Processing -> {}
                                is OperationStatus.Plain.Completed -> setDataState(it.outputData)
                                is OperationStatus.Plain.Error -> setErrorViewState(
                                    NoteDetailsViewState.Error(it.throwable) {
                                        getNoteDetails(noteId)
                                    }
                                )
                            }
                        }
                ).launchIn(viewModelScope)
    }

    private fun setLoadingState() {
        setViewState(NoteDetailsViewState.Loading)
    }

    private fun setDataState(noteDomainModel: NoteDomainModel) {
        val noteUiModel = noteDomainModelToNoteUiModelMapper.invoke(noteDomainModel)
        setViewState(NoteDetailsViewState.Data(noteUiModel))
    }

    private fun noteContentChanged(noteContent: String) {
        withViewState(NoteDetailsViewState.Data::class.java) { viewState ->
            updateNoteJob?.cancel()
            updateNoteJob = viewModelScope.launch {
                delay(1000)
                updateNoteUseCase.invoke(
                    noteUiModelToNoteDomainModelMapper.invoke(
                        viewState.noteUiModel.copy(
                            dateTimeLastEdited = DateTime(
                                SimpleDateFormat(
                                    DateTime.FORMAT,
                                    Locale.getDefault(),
                                ).format(GregorianCalendar.getInstance().time)
                            ),
                            noteContent = listOf(
                                NoteContentUiModel.Text(
                                    id = "${viewState.noteUiModel.id}_0",
                                    content = noteContent,
                                )
                            ),
                        )
                    ),
                ).collect {
                    when (it) {
                        is OperationStatus.WithInputData.Pending, is OperationStatus.WithInputData.Processing -> {}
                        is OperationStatus.WithInputData.Completed -> {
                            it
                        }
                        is OperationStatus.WithInputData.Error -> {
                            it.throwable
                        }
                    }
                }
            }
        }
    }
}
