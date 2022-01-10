package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepository
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import javax.inject.Inject

class SynchronizeNotesUseCase
@Inject constructor(
    private val notesRepository: NotesRepository,
    private val noteDomainModelToNoteMapper: Mapper<NoteDomainModel, Note>,
    private val noteToNoteDomainModelMapper: Mapper<Note, NoteDomainModel>,
) {
    operator fun invoke(
        noteDomainModels: List<NoteDomainModel>,
    ): Flow<OperationStatus<List<NoteDomainModel>, List<NoteDomainModel>>> = flow {
        emit(OperationStatus.Processing(noteDomainModels))

        try {
            val notes = noteDomainModels.map(noteDomainModelToNoteMapper)
            val synchronizedNotes = notesRepository.synchronizeNotes(notes)
            val synchronizedNotesDomainModels = synchronizedNotes.map(noteToNoteDomainModelMapper)

            emit(OperationStatus.Completed(noteDomainModels, synchronizedNotesDomainModels))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Error(noteDomainModels, throwable))
        }
    }
}
