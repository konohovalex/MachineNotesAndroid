package ru.konohovalex.feature.notes.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.notes.data.model.NoteContent
import ru.konohovalex.feature.notes.data.model.entity.NoteContentEntity
import javax.inject.Inject

internal class NoteContentEntityToNoteContentMapper
@Inject constructor() : Mapper<NoteContentEntity, NoteContent> {
    override fun invoke(input: NoteContentEntity) = with(input) {
        when {
            content != null -> NoteContent.Text(
                id = id,
                content = content,
            )
            imageContentUrl != null -> NoteContent.Image(
                id = id,
                contentUrl = imageContentUrl,
            )
            audioContentUrl != null -> NoteContent.Audio(
                id = id,
                contentUrl = audioContentUrl,
            )
            else -> throw IllegalStateException(
                "Unable to map ${NoteContentEntity::class.simpleName} to ${NoteContent::class.simpleName}, " +
                        "since all of ${NoteContentEntity.TEXT_CONTENT_COLUMN_NAME}, " +
                        "${NoteContentEntity.IMAGE_CONTENT_URL_COLUMN_NAME} " +
                        "and ${NoteContentEntity.AUDIO_CONTENT_URL_COLUMN_NAME} " +
                        "are null"
            )
        }
    }
}
