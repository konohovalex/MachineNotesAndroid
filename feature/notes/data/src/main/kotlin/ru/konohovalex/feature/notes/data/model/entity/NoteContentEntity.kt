package ru.konohovalex.feature.notes.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = NoteContentEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = arrayOf(NoteEntity.NOTE_ID_COLUMN_NAME),
            childColumns = arrayOf(NoteContentEntity.NOTE_ID_COLUMN_NAME),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
internal data class NoteContentEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(
        name = NOTE_ID_COLUMN_NAME,
        index = true,
    )
    val noteId: String,
    @ColumnInfo(name = TEXT_CONTENT_COLUMN_NAME)
    val content: String?,
    @ColumnInfo(name = IMAGE_CONTENT_URL_COLUMN_NAME)
    val imageContentUrl: String?,
    @ColumnInfo(name = AUDIO_CONTENT_URL_COLUMN_NAME)
    val audioContentUrl: String?,
) {
    companion object {
        const val TABLE_NAME = "note_content"
        const val NOTE_ID_COLUMN_NAME = "note_id"
        const val TEXT_CONTENT_COLUMN_NAME = "content"
        const val IMAGE_CONTENT_URL_COLUMN_NAME = "image_content_url"
        const val AUDIO_CONTENT_URL_COLUMN_NAME = "audio_content_url"
    }
}
