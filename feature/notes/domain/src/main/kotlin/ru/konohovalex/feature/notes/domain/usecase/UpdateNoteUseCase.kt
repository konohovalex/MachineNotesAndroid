package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.core.utils.extension.unwrap
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteUpdateParams
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepositoryContract
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteUpdateParamsDomainModel
import javax.inject.Inject

class UpdateNoteUseCase
@Inject constructor(
    private val notesRepository: NotesRepositoryContract,
    private val noteUpdateParamsDomainModelToNoteUpdateParamsMapper: Mapper<NoteUpdateParamsDomainModel, NoteUpdateParams>,
    private val noteToNoteDomainModelMapper: Mapper<Note, NoteDomainModel>,
) {
    operator fun invoke(
        noteUpdateParamsDomainModel: NoteUpdateParamsDomainModel,
    ): Flow<OperationStatus.WithInputData<NoteUpdateParamsDomainModel, NoteDomainModel>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(noteUpdateParamsDomainModel))

            emit(OperationStatus.WithInputData.Processing(noteUpdateParamsDomainModel))

            val noteUpdateParams = noteUpdateParamsDomainModelToNoteUpdateParamsMapper.invoke(noteUpdateParamsDomainModel)
            val note = notesRepository.updateNote(noteUpdateParams).unwrap()
            val noteDomainModel = noteToNoteDomainModelMapper.invoke(note)

            emit(OperationStatus.WithInputData.Completed(noteUpdateParamsDomainModel, noteDomainModel))
        }
        catch (throwable: Throwable) {
            emit(OperationStatus.WithInputData.Error(noteUpdateParamsDomainModel, throwable))
        }
    }
}
