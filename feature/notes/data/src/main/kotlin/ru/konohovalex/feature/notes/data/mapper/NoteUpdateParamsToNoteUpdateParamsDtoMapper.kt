package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.NoteUpdateParams
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import ru.konohovalex.feature.notes.data.model.remote.NoteUpdateParamsDto
import javax.inject.Inject

internal class NoteUpdateParamsToNoteUpdateParamsDtoMapper
@Inject constructor(
    private val noteContentToNoteContentDtoMapper: Mapper<NoteContent, NoteContentDto>,
) : Mapper<NoteUpdateParams, NoteUpdateParamsDto> {
    override fun invoke(input: NoteUpdateParams) = with(input) {
        NoteUpdateParamsDto(
            dateTimeLastEdited = dateTimeLastEdited,
            noteContent = noteContent.map(noteContentToNoteContentDtoMapper),
        )
    }
}
