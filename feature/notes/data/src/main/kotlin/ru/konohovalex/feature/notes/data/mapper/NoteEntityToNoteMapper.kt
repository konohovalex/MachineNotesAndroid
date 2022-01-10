package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.data.model.DateTime
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity
import javax.inject.Inject

internal class NoteEntityToNoteMapper
@Inject constructor(
    private val noteContentEntityToNoteContentMapper: Mapper<NoteContentEntity, NoteContent>,
) : Mapper<NoteEntity, Note> {
    override fun invoke(input: NoteEntity) = with(input) {
        Note(
            id = id,
            dateTimeCreated = /*dateTimeCreated*/DateTime(),
            dateTimeLastEdited = /*dateTimeLastEdited*/DateTime(),
            noteContent = /*noteContent.map(noteContentEntityToNoteContentMapper)*/listOf(),
        )
    }
}
