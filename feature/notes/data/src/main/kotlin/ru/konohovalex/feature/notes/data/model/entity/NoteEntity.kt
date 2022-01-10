package ru.konohovalex.feature.notes.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = NoteEntity.TABLE_NAME,
//    foreignKeys = []
)
internal data class NoteEntity(
    @PrimaryKey
    val id: String,
    /*@ColumnInfo(name = "date_time_created")
    val dateTimeCreated: DateTime,
    @ColumnInfo(name = "date_time_last_edited")
    val dateTimeLastEdited: DateTime,
    @ForeignKey(
        entity = NoteContentEntity::class,

    )
    @Ignore
    val noteContent: List<NoteContentEntity>,
    @ColumnInfo(name = "synchronized")
    val synchronized: Boolean*/
) {
    companion object {
        const val TABLE_NAME = "notes"
    }
}
