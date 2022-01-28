package ru.konohovalex.feature.notes.domain.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteDomainModel
import javax.inject.Inject

internal class NoteToNoteDomainModelMapper
@Inject constructor(
    private val noteContentToNoteContentDomainModelMapper: Mapper<NoteContent, NoteContentDomainModel>,
) : Mapper<Note, NoteDomainModel> {
    override fun invoke(input: Note) = with(input) {
        NoteDomainModel(
            id,
            dateTimeCreated,
            dateTimeLastEdited,
            noteContent.map(noteContentToNoteContentDomainModelMapper),
        )
    }
}
