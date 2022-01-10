package ru.konohovalex.feature.notes.data.repository.contract

import ru.konohovalex.core.data.model.PaginationData
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteUpdateParams

interface NotesRepository {
    suspend fun createNote(): Note

    suspend fun getNoteDetails(noteId: String): Note

    suspend fun updateNote(noteUpdateParams: NoteUpdateParams): Note

    suspend fun deleteNote(noteId: String)

    suspend fun getNotes(paginationData: PaginationData): List<Note>

    suspend fun synchronizeNotes(notes: List<Note>): List<Note>

    suspend fun deleteAllNotes()
}
