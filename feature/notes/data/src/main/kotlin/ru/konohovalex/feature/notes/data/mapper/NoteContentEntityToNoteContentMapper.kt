package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import javax.inject.Inject

internal class NoteContentEntityToNoteContentMapper
@Inject constructor() : Mapper<NoteContentEntity, NoteContent> {
    override fun invoke(input: NoteContentEntity) = with(input) {
        when (this) {
            is NoteContentEntity.Text -> NoteContent.Text(
                id = id,
                content = content,
            )
            is NoteContentEntity.Image -> NoteContent.Image(
                id = id,
                contentUrl = contentUrl,
            )
            is NoteContentEntity.Audio -> NoteContent.Audio(
                id = id,
                contentUrl = contentUrl,
            )
        }
    }
}
