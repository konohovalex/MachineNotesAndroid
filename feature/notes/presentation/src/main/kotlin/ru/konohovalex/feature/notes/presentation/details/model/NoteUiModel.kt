package ru.konohovalex.feature.notes.presentation.details.model

import ru.konohovalex.core.data.model.DateTime

data class NoteUiModel(
    val id: String,
    val dateTimeCreated: DateTime,
    val dateTimeLastEdited: DateTime,
    val noteContent: List<NoteContentUiModel>,
)
