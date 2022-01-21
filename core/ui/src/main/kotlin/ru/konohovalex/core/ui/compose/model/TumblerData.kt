package ru.konohovalex.core.ui.compose.model

data class TumblerData<D>(
    val titleTextWrapper: TextWrapper? = null,
    val infoTextWrapper: TextWrapper? = null,
    val positions: List<Position<D>>,
)
