package ru.konohovalex.feature.notes.domain.model

import ru.konohovalex.core.utils.model.DateTime

data class NoteDomainModel(
    val id: String,
    val dateTimeCreated: DateTime,
    val dateTimeLastEdited: DateTime,
    val noteContent: List<NoteContentDomainModel>,
)
