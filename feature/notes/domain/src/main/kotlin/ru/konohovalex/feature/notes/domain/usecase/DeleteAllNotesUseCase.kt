package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepositoryContract
import ru.konohovalex.feature.notes.domain.model.NotesDeletionMode
import javax.inject.Inject

class DeleteAllNotesUseCase
@Inject constructor(private val notesRepository: NotesRepositoryContract) {
    operator fun invoke(notesDeletionMode: NotesDeletionMode): Flow<OperationStatus.Plain<Unit>> = flow {
        try {
            emit(OperationStatus.Plain.Pending())

            emit(OperationStatus.Plain.Processing())

            val outputData = notesRepository.deleteAllNotes(
                onlyLocally = notesDeletionMode == NotesDeletionMode.ONLY_LOCALLY,
            )

            emit(OperationStatus.Plain.Completed(outputData))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Plain.Error(throwable))
        }
    }
}
