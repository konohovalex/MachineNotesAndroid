package ru.konohovalex.feature.notes.data.source.storage

import androidx.room.Dao

@Dao
internal interface NotesDao {
    // tbd fix
    /*@Query("SELECT * FROM ${NoteEntity.TABLE_NAME}")
    suspend fun getNoteDetails(noteId: String): NoteEntity?

    @Update
    suspend fun updateNotes(notes: List<NoteEntity>)

    suspend fun getNotes(): List<NoteEntity>*/
}
