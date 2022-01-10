package ru.konohovalex.feature.notes.presentation.list.model

import androidx.annotation.StringRes

sealed class TextWrapper {
    data class StringResource(@StringRes val resourceId: Int) : TextWrapper()

    data class PlainText(val value: String) : TextWrapper()
}
