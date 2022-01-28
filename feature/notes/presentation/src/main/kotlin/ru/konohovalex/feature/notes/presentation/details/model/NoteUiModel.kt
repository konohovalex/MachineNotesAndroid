package ru.konohovalex.feature.notes.presentation.details.model

import ru.konohovalex.core.utils.model.DateTime
import ru.konohovalex.core.ui.model.TextWrapper

internal data class NoteUiModel(
    val id: String,
    val title: TextWrapper,
    val dateTimeCreated: DateTime,
    val dateTimeLastEdited: DateTime,
    val noteContent: List<NoteContentUiModel>,
)
