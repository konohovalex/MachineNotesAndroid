package ru.konohovalex.feature.notes.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.konohovalex.core.utils.model.DateTime

@Entity(tableName = NoteEntity.TABLE_NAME)
internal data class NoteEntity(
    @PrimaryKey
    @ColumnInfo(name = NOTE_ID_COLUMN_NAME)
    val id: String,
    @ColumnInfo(name = "date_time_created")
    val dateTimeCreated: DateTime,
    @ColumnInfo(name = "date_time_last_edited")
    val dateTimeLastEdited: DateTime,
) {
    companion object {
        const val TABLE_NAME = "notes"
        const val NOTE_ID_COLUMN_NAME = "id"
    }
}
