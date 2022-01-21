package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.data.utils.unwrap
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase
@Inject constructor(
    private val notesRepository: NotesRepository,
) {
    operator fun invoke(noteId: String): Flow<OperationStatus.WithInputData<String, Unit>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(noteId))

            emit(OperationStatus.WithInputData.Processing(noteId))

            val outputData = notesRepository.deleteNote(noteId).unwrap()

            emit(OperationStatus.WithInputData.Completed(noteId, outputData))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(noteId, throwable))
        }
    }
}
