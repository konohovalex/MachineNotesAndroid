package ru.konohovalex.feature.notes.domain.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import javax.inject.Inject

internal class NoteContentDomainModelToNoteContentMapper
@Inject constructor() : Mapper<NoteContentDomainModel, NoteContent> {
    override fun invoke(input: NoteContentDomainModel) = with(input) {
        when (this) {
            is NoteContentDomainModel.Text -> NoteContent.Text(
                id = id,
                content = content,
            )
            is NoteContentDomainModel.Audio -> NoteContent.Audio(
                id = id,
                contentUrl = contentUrl,
            )
            is NoteContentDomainModel.Image -> NoteContent.Image(
                id = id,
                contentUrl = contentUrl,
            )
        }
    }
}
