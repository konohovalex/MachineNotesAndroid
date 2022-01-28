package ru.konohovalex.feature.notes.domain.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.NoteUpdateParams
import ru.konohovalex.feature.notes.domain.model.NoteContentDomainModel
import ru.konohovalex.feature.notes.domain.model.NoteUpdateParamsDomainModel
import javax.inject.Inject

internal class NoteUpdateParamsDomainModelToNoteUpdateParamsMapper
@Inject constructor(
    private val noteContentDomainModelToNoteContentMapper: Mapper<NoteContentDomainModel, NoteContent>,
) : Mapper<NoteUpdateParamsDomainModel, NoteUpdateParams> {
    override fun invoke(input: NoteUpdateParamsDomainModel) = with(input) {
        NoteUpdateParams(
            id = id,
            dateTimeLastEdited = dateTimeLastEdited,
            noteContent = noteContent.map(noteContentDomainModelToNoteContentMapper),
        )
    }
}
