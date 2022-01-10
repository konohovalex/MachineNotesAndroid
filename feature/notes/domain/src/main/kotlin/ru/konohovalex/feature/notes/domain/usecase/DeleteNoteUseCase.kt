package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase
@Inject constructor(
    private val notesRepository: NotesRepository,
) {
    operator fun invoke(noteId: String): Flow<OperationStatus<String, Nothing?>> = flow {
        emit(OperationStatus.Processing(noteId))

        try {
            notesRepository.deleteNote(noteId)

            emit(OperationStatus.Completed(noteId, null))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Error(noteId, throwable))
        }
    }
}
