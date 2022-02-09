package ru.konohovalex.feature.notes.data.source.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity
import ru.konohovalex.feature.notes.data.source.storage.database.converter.DateTimeConverter

@Database(
    entities = [
        NoteEntity::class,
        NoteContentEntity::class,
    ],
    version = 1,
)
@TypeConverters(DateTimeConverter::class)
internal abstract class NotesDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "notes_database"
    }

    abstract fun getNotesDao(): NotesDao
}
