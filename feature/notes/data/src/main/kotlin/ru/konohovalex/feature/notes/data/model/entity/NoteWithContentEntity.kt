package ru.konohovalex.feature.notes.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

internal data class NoteWithContentEntity(
    @Embedded
    val note: NoteEntity,
    @Relation(
        parentColumn = NoteEntity.NOTE_ID_COLUMN_NAME,
        entityColumn = NoteContentEntity.NOTE_ID_COLUMN_NAME,
    )
    val noteContent: List<NoteContentEntity>,
)
