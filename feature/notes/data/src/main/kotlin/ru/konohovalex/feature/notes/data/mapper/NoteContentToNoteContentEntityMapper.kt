package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.extension.safeCast
import ru.konohovalex.core.utils.model.ParameterizedMapper
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import ru.konohovalex.feature.notes.data.model.entity.NoteEntity
import javax.inject.Inject

internal class NoteContentToNoteContentEntityMapper
@Inject constructor() : ParameterizedMapper<NoteContent, NoteEntity, NoteContentEntity> {
    override fun invoke(parameter: NoteEntity) = { input: NoteContent ->
        NoteContentEntity(
            id = input.id,
            noteId = parameter.id,
            content = input.safeCast<NoteContent.Text>()?.content,
            imageContentUrl = input.safeCast<NoteContent.Image>()?.contentUrl,
            audioContentUrl = input.safeCast<NoteContent.Audio>()?.contentUrl,
        )
    }
}
