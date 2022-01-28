package ru.konohovalex.feature.notes.data.repository.contract

import ru.konohovalex.core.data.arch.repository.Repository
import ru.konohovalex.core.utils.model.OperationResult
import ru.konohovalex.core.utils.model.PaginationData
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteUpdateParams

interface NotesRepositoryContract : Repository {
    suspend fun createNote(): OperationResult<Note>

    suspend fun getNoteDetails(noteId: String): OperationResult<Note>

    suspend fun updateNote(noteUpdateParams: NoteUpdateParams): OperationResult<Note>

    suspend fun deleteNote(noteId: String): OperationResult<Unit>

    suspend fun getNotes(
        filter: String?,
        paginationData: PaginationData,
    ): OperationResult<List<Note>>

    suspend fun synchronizeNotes(notes: List<Note>): OperationResult<List<Note>>

    suspend fun deleteAllNotes(): OperationResult<Unit>
}
