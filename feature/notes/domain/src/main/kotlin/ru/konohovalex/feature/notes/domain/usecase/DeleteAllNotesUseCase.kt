package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.data.utils.unwrap
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepository
import javax.inject.Inject

class DeleteAllNotesUseCase
@Inject constructor(
    private val notesRepository: NotesRepository,
) {
    operator fun invoke(): Flow<OperationStatus.Plain<Unit>> = flow {
        try {
            emit(OperationStatus.Plain.Pending())

            emit(OperationStatus.Plain.Processing())

            val outputData = notesRepository.deleteAllNotes().unwrap()

            emit(OperationStatus.Plain.Completed(outputData))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Plain.Error(throwable))
        }
    }
}
