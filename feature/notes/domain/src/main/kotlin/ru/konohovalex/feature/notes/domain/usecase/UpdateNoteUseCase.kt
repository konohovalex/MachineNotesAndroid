package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteUpdateParams
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepository
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteUpdateParamsDomainModel
import javax.inject.Inject

class UpdateNoteUseCase
@Inject constructor(
    private val notesRepository: NotesRepository,
    private val noteUpdateParamsDomainModelToNoteUpdateParamsMapper: Mapper<NoteUpdateParamsDomainModel, NoteUpdateParams>,
    private val noteToNoteDomainModelMapper: Mapper<Note, NoteDomainModel>,
) {
    operator fun invoke(
        noteUpdateParamsDomainModel: NoteUpdateParamsDomainModel,
    ): Flow<OperationStatus<NoteUpdateParamsDomainModel, NoteDomainModel>> = flow {
        emit(OperationStatus.Processing(noteUpdateParamsDomainModel))

        try {
            val noteUpdateParams = noteUpdateParamsDomainModelToNoteUpdateParamsMapper.invoke(noteUpdateParamsDomainModel)
            val note = notesRepository.updateNote(noteUpdateParams)
            val noteDomainModel = noteToNoteDomainModelMapper.invoke(note)

            emit(OperationStatus.Completed(noteUpdateParamsDomainModel, noteDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.Error(noteUpdateParamsDomainModel, throwable))
        }
    }
}
