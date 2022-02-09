package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity
import javax.inject.Inject

internal class NoteToNoteEntityMapper
@Inject constructor() : Mapper<Note, NoteEntity> {
    override fun invoke(input: Note) = with(input) {
        NoteEntity(
            id = id,
            dateTimeCreated = dateTimeCreated,
            dateTimeLastEdited = dateTimeLastEdited,
        )
    }
}
