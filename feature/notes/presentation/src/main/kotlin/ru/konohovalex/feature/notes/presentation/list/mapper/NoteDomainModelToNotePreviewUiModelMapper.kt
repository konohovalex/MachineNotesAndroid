package ru.konohovalex.feature.notes.presentation.list.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import ru.konohovalex.feature.notes.presentation.extension.getSubtitleTextWrapper
import ru.konohovalex.feature.notes.presentation.extension.getTitleTextWrapper
import javax.inject.Inject

internal class NoteDomainModelToNotePreviewUiModelMapper
@Inject constructor() : Mapper<NoteDomainModel, NotePreviewUiModel> {
    override fun invoke(input: NoteDomainModel): NotePreviewUiModel = with(input) {
        NotePreviewUiModel(
            id = id,
            dateTimeLastEdited = dateTimeLastEdited,
            titleTextWrapper = getTitleTextWrapper(),
            subtitleTextWrapper = getSubtitleTextWrapper(),
        )
    }
}
