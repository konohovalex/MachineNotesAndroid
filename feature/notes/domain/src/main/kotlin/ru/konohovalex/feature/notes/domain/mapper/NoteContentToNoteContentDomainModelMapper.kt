package ru.konohovalex.feature.notes.domain.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import javax.inject.Inject

internal class NoteContentToNoteContentDomainModelMapper
@Inject constructor() : Mapper<NoteContent, NoteContentDomainModel> {
    override fun invoke(input: NoteContent) = with(input) {
        when (this) {
            is NoteContent.Text -> NoteContentDomainModel.Text(
                id = id,
                content = content,
            )
            is NoteContent.Audio -> NoteContentDomainModel.Audio(
                id = id,
                contentUrl = contentUrl,
            )
            is NoteContent.Image -> NoteContentDomainModel.Image(
                id = id,
                contentUrl = contentUrl,
            )
        }
    }
}
