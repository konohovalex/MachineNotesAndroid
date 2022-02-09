package ru.konohovalex.feature.notes.data.model.remote

internal data class NoteUpdateParamsDto(
    val dateTimeLastEdited: String,
    val noteContent: List<NoteContentDto>,
)
