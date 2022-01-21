package ru.konohovalex.feature.notes.data.repository.impl

import kotlinx.coroutines.delay
import ru.konohovalex.core.data.model.OperationResult
import ru.konohovalex.core.data.model.PaginationData
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.core.utils.withIo
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.NoteUpdateParams
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity
import ru.konohovalex.feature.notes.data.model.remote.NoteDto
import ru.konohovalex.feature.notes.data.model.remote.NoteUpdateParamsDto
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepository
import ru.konohovalex.feature.notes.data.source.api.NotesApi
import ru.konohovalex.feature.notes.data.source.storage.NotesDao
import ru.konohovalex.feature.notes.data.utils.createDummyNote
import ru.konohovalex.feature.notes.data.utils.createDummyNoteList
import javax.inject.Inject

internal class NotesRepositoryImpl
@Inject constructor(
    private val notesApi: NotesApi,
    private val notesDao: NotesDao,
    private val noteDtoToNoteMapper: Mapper<NoteDto, Note>,
    private val noteToNoteDtoMapper: Mapper<Note, NoteDto>,
    private val noteUpdateParamsToNoteUpdateParamsDtoMapper: Mapper<NoteUpdateParams, NoteUpdateParamsDto>,
    private val noteToNoteEntityMapper: Mapper<Note, NoteEntity>,
    private val noteEntityToNoteMapper: Mapper<NoteEntity, Note>,
) : NotesRepository {
    private val dummyNoteList = createDummyNoteList(25)

    override suspend fun createNote(): OperationResult<Note> = withIo {
        delay(3000)
        val dummyNote = createDummyNote("${dummyNoteList.size}", 0)
        OperationResult.Success(dummyNote)
    }

    override suspend fun getNoteDetails(noteId: String): OperationResult<Note> = withIo {
        delay(3000)
        OperationResult.Success(dummyNoteList.find { it.id == noteId }!!)
    }

    override suspend fun updateNote(noteUpdateParams: NoteUpdateParams): OperationResult<Note> = withIo {
        delay(3000)
        val indexOfNoteToUpdate = dummyNoteList.indexOfFirst { it.id == noteUpdateParams.id }
        val noteToUpdate = dummyNoteList[indexOfNoteToUpdate]
        val updatedNote = noteToUpdate.copy(
            dateTimeLastEdited = noteUpdateParams.dateTimeLastEdited,
            noteContent = noteUpdateParams.noteContent,
        )
        dummyNoteList[indexOfNoteToUpdate] = updatedNote
        OperationResult.Success(updatedNote)
    }

    override suspend fun deleteNote(noteId: String) = withIo {
        delay(3000)
        dummyNoteList.removeAll { it.id == noteId }
        OperationResult.Success(Unit)
    }

    override suspend fun getNotes(
        filter: String?,
        paginationData: PaginationData,
    ): OperationResult<List<Note>> = withIo {
        delay(3000)
        val outputList = filter?.takeIf { it.isNotBlank() }?.let { filterValue ->
            mutableListOf<Note>().apply {
                dummyNoteList.forEach { note ->
                    val containsFilterValue = note.noteContent.any {
                        it is NoteContent.Text && it.content.contains(filterValue, ignoreCase = true)
                    }
                    if (containsFilterValue) add(note)
                }
            }
        } ?: dummyNoteList
        OperationResult.Success(outputList)
    }

    override suspend fun synchronizeNotes(notes: List<Note>): OperationResult<List<Note>> = withIo {
        delay(3000)
        OperationResult.Success(dummyNoteList)
    }

    override suspend fun deleteAllNotes() = withIo {
        delay(3000)
        dummyNoteList.clear()
        OperationResult.Success(Unit)
    }
}
