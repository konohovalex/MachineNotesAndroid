package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepository
import javax.inject.Inject

class DeleteAllNotesUseCase
@Inject constructor(
    private val notesRepository: NotesRepository,
) {
    operator fun invoke(): Flow<OperationStatus<Nothing?, Nothing?>> = flow {
        emit(OperationStatus.Processing(null))

        try {
            notesRepository.deleteAllNotes()

            emit(OperationStatus.Completed(null, null))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Error(null, throwable))
        }
    }
}
