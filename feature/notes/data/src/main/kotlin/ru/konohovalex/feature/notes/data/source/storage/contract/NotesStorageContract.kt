package ru.konohovalex.feature.notes.data.source.storage.contract

import kotlinx.coroutines.flow.StateFlow
import ru.konohovalex.core.data.arch.source.storage.Storage
import ru.konohovalex.feature.notes.data.model.entity.NoteWithContentEntity

internal interface NotesStorageContract : Storage {
    suspend fun observeNotes(): StateFlow<List<NoteWithContentEntity>>
    suspend fun getNote(noteId: String): NoteWithContentEntity
    suspend fun insertNotes(notes: List<NoteWithContentEntity>)
    suspend fun updateNotes(notes: List<NoteWithContentEntity>)
    suspend fun deleteNotes(noteIds: List<String>)
    suspend fun deleteAllNotes()
}
