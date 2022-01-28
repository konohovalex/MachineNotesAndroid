package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.core.utils.extension.unwrap
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepositoryContract
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import javax.inject.Inject

class CreateNoteUseCase
@Inject constructor(
    private val notesRepository: NotesRepositoryContract,
    private val noteToNoteDomainModelMapper: Mapper<Note, NoteDomainModel>,
) {
    operator fun invoke(): Flow<OperationStatus.Plain<NoteDomainModel>> = flow {
        try {
            emit(OperationStatus.Plain.Pending())

            emit(OperationStatus.Plain.Processing())

            val note = notesRepository.createNote().unwrap()
            val noteDomainModel = noteToNoteDomainModelMapper.invoke(note)

            emit(OperationStatus.Plain.Completed(noteDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Plain.Error(throwable))
        }
    }
}
