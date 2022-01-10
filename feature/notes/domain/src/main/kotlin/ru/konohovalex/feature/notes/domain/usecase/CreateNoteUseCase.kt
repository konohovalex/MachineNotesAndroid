package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepository
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import javax.inject.Inject

class CreateNoteUseCase
@Inject constructor(
    private val notesRepository: NotesRepository,
    private val noteToNoteDomainModelMapper: Mapper<Note, NoteDomainModel>,
) {
    operator fun invoke(): Flow<OperationStatus<Nothing?, NoteDomainModel>> = flow {
        emit(OperationStatus.Processing(inputData = null))

        try {
            val note = notesRepository.createNote()
            val noteDomainModel = noteToNoteDomainModelMapper.invoke(note)

            emit(OperationStatus.Completed(null, noteDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Error(null, throwable))
        }
    }
}
