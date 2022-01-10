package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import ru.konohovalex.feature.notes.data.model.remote.NoteDto
import javax.inject.Inject

internal class NoteDtoToNoteMapper
@Inject constructor(
    private val noteContentDtoToNoteContentMapper: Mapper<NoteContentDto, NoteContent>,
) : Mapper<NoteDto, Note> {
    override fun invoke(input: NoteDto) = with(input) {
        Note(
            id,
            dateTimeCreated,
            dateTimeLastEdited,
            noteContent.map(noteContentDtoToNoteContentMapper)
        )
    }
}
