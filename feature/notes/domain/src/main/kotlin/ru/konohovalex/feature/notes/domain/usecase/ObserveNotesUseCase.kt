package ru.konohovalex.feature.notes.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationStatus
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepositoryContract
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import javax.inject.Inject

class ObserveNotesUseCase
@Inject constructor(
    private val notesRepository: NotesRepositoryContract,
    private val noteToNoteDomainModelMapper: Mapper<Note, NoteDomainModel>,
) {
    suspend operator fun invoke(): Flow<OperationStatus.Plain<List<NoteDomainModel>>> =
        notesRepository.observeNotes()
            .map<List<Note>, OperationStatus.Plain<List<NoteDomainModel>>> {
                val noteDomainModels = it.map(noteToNoteDomainModelMapper)
                OperationStatus.Plain.Completed(noteDomainModels)
            }
            .onStart { emit(OperationStatus.Plain.Pending()) }
            .catch { exception -> emit(OperationStatus.Plain.Error(exception)) }
}
