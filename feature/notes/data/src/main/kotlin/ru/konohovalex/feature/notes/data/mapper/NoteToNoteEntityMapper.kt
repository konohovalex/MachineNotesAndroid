package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity
import javax.inject.Inject

internal class NoteToNoteEntityMapper
@Inject constructor(
    private val noteContentToNoteContentEntityMapper: Mapper<NoteContent, NoteContentEntity>,
) : Mapper<Note, NoteEntity> {
    override fun invoke(input: Note) = with(input) {
        // tbd
        NoteEntity(
            id = id,
//            dateTimeCreated = dateTimeCreated,
//            dateTimeLastEdited = dateTimeLastEdited,
//            noteContent = noteContent.map(noteContentToNoteContentEntityMapper),
        )
    }
}
