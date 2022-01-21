package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.data.utils.unwrap
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
    ): Flow<OperationStatus.WithInputData<List<NoteDomainModel>, List<NoteDomainModel>>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(noteDomainModels))

            emit(OperationStatus.WithInputData.Processing(noteDomainModels))

            val notes = noteDomainModels.map(noteDomainModelToNoteMapper)
            val synchronizedNotes = notesRepository.synchronizeNotes(notes).unwrap()
            val synchronizedNotesDomainModels = synchronizedNotes.map(noteToNoteDomainModelMapper)

            emit(OperationStatus.WithInputData.Completed(noteDomainModels, synchronizedNotesDomainModels))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(noteDomainModels, throwable))
        }
    }
}
