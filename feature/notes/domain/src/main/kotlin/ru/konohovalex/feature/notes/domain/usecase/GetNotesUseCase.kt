package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.konohovalex.core.data.model.OperationStatus
import ru.konohovalex.core.data.model.PaginationData
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepository
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import javax.inject.Inject

class GetNotesUseCase
@Inject constructor(
    private val notesRepository: NotesRepository,
    private val noteToNoteDomainModelMapper: Mapper<Note, NoteDomainModel>,
) {
    operator fun invoke(
        filter: String? = null,
        paginationData: PaginationData,
    ): Flow<OperationStatus<Pair<String?, PaginationData>, List<NoteDomainModel>>> = flow {
        emit(
            OperationStatus.Processing(
                inputData = Pair(
                    filter,
                    paginationData,
                )
            )
        )

        try {
            val notes = notesRepository.getNotes(
                filter,
                paginationData,
            )
            val notesDomainModels = notes.map(noteToNoteDomainModelMapper)

            emit(
                OperationStatus.Completed(
                    Pair(
                        filter,
                        paginationData,
                    ),
                    notesDomainModels,
                )
            )
        }
        catch (throwable: Throwable) {
            emit(
                OperationStatus.Error(
                    Pair(
                        filter,
                        paginationData,
                    ),
                    throwable,
                )
            )
        }
    }
}
