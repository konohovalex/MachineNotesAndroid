package ru.konohovalex.feature.notes.presentation.details.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.presentation.details.model.NoteContentUiModel
import javax.inject.Inject

internal class NoteContentDomainModelToNoteContentUiModelMapper
@Inject constructor() : Mapper<NoteContentDomainModel, NoteContentUiModel> {
    override fun invoke(input: NoteContentDomainModel) = with(input) {
        when (this) {
            is NoteContentDomainModel.Text -> NoteContentUiModel.Text(
                id = id,
                content = content,
            )
            is NoteContentDomainModel.Image -> NoteContentUiModel.Image(
                id = id,
                contentUrl = contentUrl,
            )
            is NoteContentDomainModel.Audio -> NoteContentUiModel.Audio(
                id = id,
                contentUrl = contentUrl,
            )
        }
    }
}
