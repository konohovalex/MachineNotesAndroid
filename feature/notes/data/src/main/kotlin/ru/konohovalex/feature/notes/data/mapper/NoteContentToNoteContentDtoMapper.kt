package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import javax.inject.Inject

internal class NoteContentToNoteContentDtoMapper
@Inject constructor() : Mapper<NoteContent, NoteContentDto> {
    override fun invoke(input: NoteContent) = with(input) {
        when (this) {
            is NoteContent.Text -> NoteContentDto.Text(
                id = id,
                content = content,
            )
            is NoteContent.Image -> NoteContentDto.Image(
                id = id,
                contentUrl = contentUrl,
            )
            is NoteContent.Audio -> NoteContentDto.Audio(
                id = id,
                contentUrl = contentUrl,
            )
        }
    }
}
