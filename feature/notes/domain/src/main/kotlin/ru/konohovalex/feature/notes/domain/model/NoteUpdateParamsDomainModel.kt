package ru.konohovalex.feature.notes.domain.model

import ru.konohovalex.core.data.model.DateTime

data class NoteUpdateParamsDomainModel(
    val id: String,
    val dateTimeLastEdited: DateTime,
    val noteContent: List<NoteContentDomainModel>,
)
