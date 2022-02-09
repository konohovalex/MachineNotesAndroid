package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepositoryContract
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import javax.inject.Inject

class GetNoteDetailsUseCase
@Inject constructor(
    private val notesRepository: NotesRepositoryContract,
    private val noteToNoteDomainModelMapper: Mapper<Note, NoteDomainModel>,
) {
    operator fun invoke(noteId: String): Flow<OperationStatus.WithInputData<String, NoteDomainModel>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(noteId))

            emit(OperationStatus.WithInputData.Processing(noteId))

            val note = notesRepository.getNoteDetails(noteId)
            val noteDomainModel = noteToNoteDomainModelMapper.invoke(note)

            emit(OperationStatus.WithInputData.Completed(noteId, noteDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(noteId, throwable))
        }
    }
}
