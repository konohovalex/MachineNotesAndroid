package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import ru.konohovalex.feature.notes.data.model.remote.NoteDto
import javax.inject.Inject

internal class NoteToNoteDtoMapper
@Inject constructor(
    private val noteContentToNoteContentDtoMapper: Mapper<NoteContent, NoteContentDto>,
) : Mapper<Note, NoteDto> {
    override fun invoke(input: Note) = with(input) {
        NoteDto(
            id,
            dateTimeCreated,
            dateTimeLastEdited,
            noteContent.map(noteContentToNoteContentDtoMapper)
        )
    }
}
