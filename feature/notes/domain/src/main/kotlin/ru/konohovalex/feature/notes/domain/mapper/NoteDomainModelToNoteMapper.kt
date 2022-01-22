package ru.konohovalex.feature.notes.domain.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import javax.inject.Inject

internal class NoteDomainModelToNoteMapper
@Inject constructor(
    private val noteContentDomainModelToNoteContentMapper: Mapper<NoteContentDomainModel, NoteContent>,
) : Mapper<NoteDomainModel, Note> {
    override fun invoke(input: NoteDomainModel) = with(input) {
        Note(
            id,
            dateTimeCreated,
            dateTimeLastEdited,
            noteContent.map(noteContentDomainModelToNoteContentMapper),
        )
    }
}
