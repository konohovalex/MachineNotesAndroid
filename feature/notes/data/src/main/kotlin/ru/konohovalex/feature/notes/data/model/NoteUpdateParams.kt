package ru.konohovalex.feature.notes.data.model

import ru.konohovalex.core.data.model.DateTime

data class NoteUpdateParams(
    val id: String,
    val dateTimeLastEdited: DateTime,
    val noteContent: List<NoteContent>,
)
