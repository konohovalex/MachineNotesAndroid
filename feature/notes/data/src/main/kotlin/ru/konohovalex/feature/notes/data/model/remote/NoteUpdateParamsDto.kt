package ru.konohovalex.feature.notes.data.model.remote

import ru.konohovalex.core.utils.model.DateTime

internal data class NoteUpdateParamsDto(
    val dateTimeLastEdited: DateTime,
    val noteContent: List<NoteContentDto>,
)
