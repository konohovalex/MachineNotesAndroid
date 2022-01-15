package ru.konohovalex.feature.notes.presentation.list.model

import ru.konohovalex.core.data.model.DateTime
import ru.konohovalex.core.ui.compose.model.TextWrapper

data class NotePreviewUiModel(
    val id: String,
    val dateTimeLastEdited: DateTime,
    val titleTextWrapper: TextWrapper,
    val subtitleTextWrapper: TextWrapper,
)
