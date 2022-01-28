package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.core.utils.model.PaginationData
import ru.konohovalex.core.utils.extension.unwrap
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepositoryContract
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import javax.inject.Inject

class GetNotesUseCase
@Inject constructor(
    private val notesRepository: NotesRepositoryContract,
    private val noteToNoteDomainModelMapper: Mapper<Note, NoteDomainModel>,
) {
    operator fun invoke(
        filter: String? = null,
        paginationData: PaginationData,
    ): Flow<OperationStatus.WithInputData<Pair<String?, PaginationData>, List<NoteDomainModel>>> = flow {
        try {
            emit(OperationStatus.WithInputData.Pending(Pair(filter, paginationData)))

            emit(OperationStatus.WithInputData.Processing(Pair(filter, paginationData)))

            val notes = notesRepository.getNotes(
                filter = filter,
                paginationData = paginationData,
            ).unwrap()
            val notesDomainModels = notes.map(noteToNoteDomainModelMapper)

            emit(
                value = OperationStatus.WithInputData.Completed(
                    inputData = Pair(filter, paginationData),
                    outputData = notesDomainModels,
                ),
            )
        }
        catch (throwable: Throwable) {
            emit(
                value = OperationStatus.WithInputData.Error(
                    inputData = Pair(filter, paginationData),
                    throwable = throwable,
                ),
            )
        }
    }
}
