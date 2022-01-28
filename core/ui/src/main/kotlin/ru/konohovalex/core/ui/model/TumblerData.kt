package ru.konohovalex.core.ui.model

data class TumblerData<D>(
    val titleTextWrapper: TextWrapper? = null,
    val infoTextWrapper: TextWrapper? = null,
    val positions: List<Position<D>>,
)
