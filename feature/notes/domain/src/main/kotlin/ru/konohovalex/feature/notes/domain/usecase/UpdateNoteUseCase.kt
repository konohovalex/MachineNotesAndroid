package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepositoryContract
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import javax.inject.Inject

class UpdateNoteUseCase
@Inject constructor(
    private val notesRepository: NotesRepositoryContract,
    private val noteDomainModelToNoteMapper: Mapper<NoteDomainModel, Note>,
    private val noteToNoteDomainModelMapper: Mapper<Note, NoteDomainModel>,
) {
    operator fun invoke(
        noteDomainModel: NoteDomainModel,
    ): Flow<OperationStatus.WithInputData<NoteDomainModel, NoteDomainModel>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(noteDomainModel))

            emit(OperationStatus.WithInputData.Processing(noteDomainModel))

            val note = noteDomainModelToNoteMapper.invoke(noteDomainModel)
            val updatedNote = notesRepository.updateNote(note)
            val updatedNoteDomainModel = noteToNoteDomainModelMapper.invoke(updatedNote)

            emit(OperationStatus.WithInputData.Completed(noteDomainModel, updatedNoteDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(noteDomainModel, throwable))
        }
    }
}
