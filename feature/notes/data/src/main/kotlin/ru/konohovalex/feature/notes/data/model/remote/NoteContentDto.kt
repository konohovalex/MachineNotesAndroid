package ru.konohovalex.feature.notes.data.model.remote

internal sealed class NoteContentDto {
    abstract val id: String

    data class Text(
        override val id: String,
        val content: String,
    ) : NoteContentDto()

    data class Image(
        override val id: String,
        val contentUrl: String,
    ) : NoteContentDto()

    data class Audio(
        override val id: String,
        val contentUrl: String,
    ) : NoteContentDto()
}
