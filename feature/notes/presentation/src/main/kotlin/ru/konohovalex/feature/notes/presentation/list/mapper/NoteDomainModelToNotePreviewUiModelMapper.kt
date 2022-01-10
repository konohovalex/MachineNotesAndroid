package ru.konohovalex.feature.notes.presentation.list.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.presentation.list.model.NotePreviewUiModel
import ru.konohovalex.feature.notes.presentation.utils.getSubtitleTextWrapper
import ru.konohovalex.feature.notes.presentation.utils.getTitleTextWrapper
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
