package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.remote.NoteContentDto
import javax.inject.Inject

internal class NoteContentDtoToNoteContentMapper
@Inject constructor() : Mapper<NoteContentDto, NoteContent> {
    override fun invoke(input: NoteContentDto) = with(input) {
        when (this) {
            is NoteContentDto.Text -> NoteContent.Text(
                id = id,
                content = content,
            )
            is NoteContentDto.Audio -> NoteContent.Audio(
                id = id,
                contentUrl = contentUrl,
            )
            is NoteContentDto.Image -> NoteContent.Image(
                id = id,
                contentUrl = contentUrl,
            )
        }
    }
}
