package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.Note
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import ru.konohovalex.feature.notes.data.model.remote.NoteUpdateParamsDto
import javax.inject.Inject

internal class NoteToNoteUpdateParamsDtoMapper
@Inject constructor(
    private val noteContentToNoteContentDtoMapper: Mapper<NoteContent, NoteContentDto>,
) : Mapper<Note, NoteUpdateParamsDto> {
    override fun invoke(input: Note) = with(input) {
        NoteUpdateParamsDto(
            dateTimeLastEdited = dateTimeLastEdited.raw,
            noteContent = noteContent.map(noteContentToNoteContentDtoMapper),
        )
    }
}
