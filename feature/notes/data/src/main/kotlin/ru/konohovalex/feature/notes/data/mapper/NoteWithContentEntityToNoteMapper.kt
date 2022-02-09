package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import ru.konohovalex.feature.notes.data.model.entity.NoteWithContentEntity
import javax.inject.Inject

internal class NoteWithContentEntityToNoteMapper
@Inject constructor(
    private val noteContentEntityToNoteContentMapper: Mapper<NoteContentEntity, NoteContent>,
) : Mapper<NoteWithContentEntity, Note> {
    override fun invoke(input: NoteWithContentEntity) = with(input) {
        Note(
            id = note.id,
            dateTimeCreated = note.dateTimeCreated,
            dateTimeLastEdited = note.dateTimeLastEdited,
            noteContent = noteContent.map(noteContentEntityToNoteContentMapper),
        )
    }
}
