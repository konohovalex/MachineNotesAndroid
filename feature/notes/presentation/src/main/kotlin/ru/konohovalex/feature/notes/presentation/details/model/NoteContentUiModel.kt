package ru.konohovalex.feature.notes.presentation.details.model

internal sealed class NoteContentUiModel(open val id: String) {
    data class Text(
        override val id: String,
        val content: String,
    ) : NoteContentUiModel(id)

    data class Image(
        override val id: String,
        val contentUrl: String,
    ) : NoteContentUiModel(id)

    data class Audio(
        override val id: String,
        val contentUrl: String,
    ) : NoteContentUiModel(id)
}
