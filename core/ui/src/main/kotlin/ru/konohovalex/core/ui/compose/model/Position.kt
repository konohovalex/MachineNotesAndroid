package ru.konohovalex.core.ui.compose.model

sealed class Position(open val id: String) {
    data class Text(
        override val id: String,
        val textWrapper: TextWrapper,
    ) : Position(id)

    data class Image(
        override val id: String,
        val imageWrapper: ImageWrapper,
    ) : Position(id)
}
