package ru.konohovalex.feature.notes.presentation.list.model

import ru.konohovalex.core.data.model.DateTime

data class NotePreviewUiModel(
    val id: String,
    val dateTimeLastEdited: DateTime,
    val titleTextWrapper: TextWrapper,
    val subtitleTextWrapper: TextWrapper,
)
