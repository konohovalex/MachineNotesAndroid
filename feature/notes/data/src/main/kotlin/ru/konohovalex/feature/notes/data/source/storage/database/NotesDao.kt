package ru.konohovalex.feature.notes.data.source.storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.konohovalex.core.utils.extension.forEach
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity
import ru.konohovalex.feature.notes.data.model.entity.NoteWithContentEntity

@Dao
internal abstract class NotesDao {
    @Transaction
    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME}")
    abstract fun observeNotes(): Flow<List<NoteWithContentEntity>>

    @Transaction
    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} WHERE ${NoteEntity.NOTE_ID_COLUMN_NAME} LIKE :noteId")
    abstract suspend fun getNote(noteId: String): NoteWithContentEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertNote(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertNoteContent(noteContent: List<NoteContentEntity>)

    @Transaction
    open suspend fun insertNotes(notes: List<NoteWithContentEntity>) {
        notes.forEach {
            insertNote(it.note)
            insertNoteContent(it.noteContent)
        }
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun updateNote(note: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun updateNoteContent(noteContent: List<NoteContentEntity>)

    @Transaction
    open suspend fun updateNotes(notes: List<NoteWithContentEntity>) {
        notes.forEach {
            updateNote(it.note)
            // tbd complete logic
            insertNoteContent(it.noteContent)
        }
    }

    @Query("DELETE FROM ${NoteEntity.TABLE_NAME} WHERE ${NoteEntity.NOTE_ID_COLUMN_NAME} LIKE :noteId")
    protected abstract suspend fun deleteNote(noteId: String)

    @Transaction
    open suspend fun deleteNotes(noteIds: List<String>) {
        noteIds.forEach(::deleteNote)
    }

    @Query("DELETE FROM ${NoteEntity.TABLE_NAME}")
    abstract suspend fun deleteAllNotes()
}
