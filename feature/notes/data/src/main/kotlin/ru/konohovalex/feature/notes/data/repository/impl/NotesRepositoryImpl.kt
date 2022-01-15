package ru.konohovalex.feature.notes.data.repository.impl

import kotlinx.coroutines.delay
import ru.konohovalex.core.data.model.PaginationData
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.NoteUpdateParams
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity
import ru.konohovalex.feature.notes.data.model.remote.NoteDto
import ru.konohovalex.feature.notes.data.model.remote.NoteUpdateParamsDto
import ru.konohovalex.feature.notes.data.repository.contract.NotesRepository
import ru.konohovalex.feature.notes.data.source.api.NotesApi
import ru.konohovalex.feature.notes.data.source.storage.NotesDao
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
    override suspend fun createNote(): Note {
        delay(3000)
        return createDummyNote("1", 0)
    }

    override suspend fun getNoteDetails(noteId: String): Note {
        delay(3000)
        return createDummyNote("1", 0)
    }

    override suspend fun updateNote(noteUpdateParams: NoteUpdateParams): Note {
        delay(3000)
        return createDummyNote("1", 0)
    }

    override suspend fun deleteNote(noteId: String) {
        delay(3000)
    }

    override suspend fun getNotes(
        filter: String?,
        paginationData: PaginationData,
    ): List<Note> {
        val notesDummyList = createDummyNoteList(25).let { list ->
            filter?.takeIf { it.isNotEmpty() }?.let { filterValue ->
                mutableListOf<Note>().apply {
                    list.forEach { note ->
                        val containsFilterValue = note.noteContent.any {
                            it is NoteContent.Text && it.content.contains(filterValue, ignoreCase = true)
                        }
                        if (containsFilterValue) add(note)
                    }
                }
            } ?: list
        }
        return notesDummyList
        /*val noteDtoList = notesApi.getNotes(paginationData.pageSize, paginationData.pageNumber)
        return noteDtoList.map(noteDtoToNoteMapper)*/
    }

    override suspend fun synchronizeNotes(notes: List<Note>): List<Note> {
        delay(3000)
        return createDummyNoteList(25)
    }

    override suspend fun deleteAllNotes() {
        delay(3000)
    }
}
