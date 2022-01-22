package ru.konohovalex.feature.notes.presentation.details.model

import ru.konohovalex.core.data.model.DateTime
import ru.konohovalex.core.ui.compose.model.TextWrapper

internal data class NoteUiModel(
    val id: String,
    val title: TextWrapper,
    val dateTimeCreated: DateTime,
    val dateTimeLastEdited: DateTime,
    val noteContent: List<NoteContentUiModel>,
)
