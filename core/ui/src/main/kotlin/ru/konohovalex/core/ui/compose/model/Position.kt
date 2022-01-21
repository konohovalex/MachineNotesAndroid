package ru.konohovalex.core.ui.compose.model

sealed class Position<D>(open val data: D) {
    data class Text<D>(
        override val data: D,
        val textWrapper: TextWrapper,
    ) : Position<D>(data)

    data class Image<D>(
        override val data: D,
        val imageWrapper: ImageWrapper,
    ) : Position<D>(data)
}
