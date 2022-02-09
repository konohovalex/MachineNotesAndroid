package ru.konohovalex.feature.notes.presentation.details.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.presentation.details.model.NoteContentUiModel
import javax.inject.Inject

internal class NoteContentUiModelToNoteContentDomainModelMapper
@Inject constructor() : Mapper<NoteContentUiModel, NoteContentDomainModel> {
    override fun invoke(input: NoteContentUiModel) = with(input) {
        when (this) {
            is NoteContentUiModel.Text -> NoteContentDomainModel.Text(
                id = id,
                content = content,
            )
            is NoteContentUiModel.Image -> NoteContentDomainModel.Image(
                id = id,
                contentUrl = contentUrl,
            )
            is NoteContentUiModel.Audio -> NoteContentDomainModel.Audio(
                id = id,
                contentUrl = contentUrl,
            )
        }
    }
}
