package ru.konohovalex.feature.notes.domain.model

sealed class NoteContentDomainModel(open val id: String) {
    data class Text(
        override val id: String,
        val content: String,
    ) : NoteContentDomainModel(id)

    data class Image(
        override val id: String,
        val contentUrl: String,
    ) : NoteContentDomainModel(id)

    data class Audio(
        override val id: String,
        val contentUrl: String,
    ) : NoteContentDomainModel(id)
}
