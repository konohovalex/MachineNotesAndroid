package ru.konohovalex.feature.notes.presentation.details.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import ru.konohovalex.feature.notes.presentation.details.model.NoteContentUiModel
import ru.konohovalex.feature.notes.presentation.details.model.NoteUiModel
import javax.inject.Inject

internal class NoteUiModelToNoteDomainModelMapper
@Inject constructor(
    private val noteContentDomainModelToNoteContentUiModelMapper: Mapper<NoteContentUiModel, NoteContentDomainModel>,
) : Mapper<NoteUiModel, NoteDomainModel> {
    override fun invoke(input: NoteUiModel) = with(input) {
        NoteDomainModel(
            id = id,
            dateTimeCreated = dateTimeCreated,
            dateTimeLastEdited = dateTimeLastEdited,
            noteContent = noteContent.map(noteContentDomainModelToNoteContentUiModelMapper),
        )
    }
}
