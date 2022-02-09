package ru.konohovalex.feature.notes.data.repository.contract

import kotlinx.coroutines.flow.Flow
import ru.konohovalex.core.data.arch.repository.Repository
import ru.konohovalex.feature.notes.data.model.Note

interface NotesRepositoryContract : Repository {
    suspend fun synchronizeNotes()
    suspend fun observeNotes(): Flow<List<Note>>
    suspend fun createNote(): Note
    suspend fun getNoteDetails(noteId: String): Note
    suspend fun updateNote(note: Note): Note
    suspend fun deleteNote(noteId: String)
    suspend fun deleteAllNotes(onlyLocally: Boolean)
}
