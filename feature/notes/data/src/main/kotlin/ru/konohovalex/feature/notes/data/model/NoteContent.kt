package ru.konohovalex.feature.notes.data.model

sealed class NoteContent(open val id: String) {
    data class Text(
        override val id: String,
        val content: String,
    ) : NoteContent(id)

    data class Image(
        override val id: String,
        val contentUrl: String,
    ) : NoteContent(id)

    data class Audio(
        override val id: String,
        val contentUrl: String,
    ) : NoteContent(id)
}
