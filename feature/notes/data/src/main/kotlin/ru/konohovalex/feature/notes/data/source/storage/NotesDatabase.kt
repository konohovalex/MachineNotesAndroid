package ru.konohovalex.feature.notes.data.source.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.konohovalex.core.data.source.contract.Storage
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
)
internal abstract class NotesDatabase : RoomDatabase(), Storage {
    companion object {
        const val DATABASE_NAME = "notes_database"
    }

    abstract fun getNotesDao(): NotesDao
}
