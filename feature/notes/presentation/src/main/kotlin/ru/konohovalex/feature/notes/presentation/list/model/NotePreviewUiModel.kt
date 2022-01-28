package ru.konohovalex.feature.notes.presentation.list.model

import ru.konohovalex.core.utils.model.DateTime
import ru.konohovalex.core.ui.model.TextWrapper

internal data class NotePreviewUiModel(
    val id: String,
    val dateTimeLastEdited: DateTime,
    val titleTextWrapper: TextWrapper,
    val subtitleTextWrapper: TextWrapper,
)
