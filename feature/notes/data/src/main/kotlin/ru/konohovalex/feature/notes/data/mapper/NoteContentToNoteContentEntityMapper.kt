package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import javax.inject.Inject

internal class NoteContentToNoteContentEntityMapper
@Inject constructor() : Mapper<NoteContent, NoteContentEntity> {
    override fun invoke(input: NoteContent) = with(input) {
        when (this) {
            is NoteContent.Text -> NoteContentEntity.Text(
                id = id,
                content = content,
            )
            is NoteContent.Image -> NoteContentEntity.Image(
                id = id,
                contentUrl = contentUrl,
            )
            is NoteContent.Audio -> NoteContentEntity.Audio(
                id = id,
                contentUrl = contentUrl,
            )
        }
    }
}
