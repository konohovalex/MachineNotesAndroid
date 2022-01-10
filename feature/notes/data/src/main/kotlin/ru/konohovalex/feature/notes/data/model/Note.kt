package ru.konohovalex.feature.notes.data.model

import ru.konohovalex.core.data.model.DateTime

data class Note(
    val id: String,
    val dateTimeCreated: DateTime,
    val dateTimeLastEdited: DateTime,
    val noteContent: List<NoteContent>,
)
