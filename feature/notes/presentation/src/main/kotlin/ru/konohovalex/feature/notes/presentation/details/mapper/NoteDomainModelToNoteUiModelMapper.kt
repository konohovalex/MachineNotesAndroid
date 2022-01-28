package ru.konohovalex.feature.notes.presentation.details.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.presentation.details.model.NoteContentUiModel
import ru.konohovalex.feature.notes.presentation.details.model.NoteUiModel
import ru.konohovalex.feature.notes.presentation.extension.getTitleTextWrapper
import javax.inject.Inject

internal class NoteDomainModelToNoteUiModelMapper
@Inject constructor(
    private val noteContentDomainModelToNoteContentUiModelMapper: Mapper<NoteContentDomainModel, NoteContentUiModel>,
) : Mapper<NoteDomainModel, NoteUiModel> {
    override fun invoke(input: NoteDomainModel) = with(input) {
        NoteUiModel(
            id = id,
            title = getTitleTextWrapper(true),
            dateTimeCreated = dateTimeCreated,
            dateTimeLastEdited = dateTimeLastEdited,
            noteContent = noteContent.map(noteContentDomainModelToNoteContentUiModelMapper),
        )
    }
}
