package ru.konohovalex.core.ui.compose.model

import androidx.annotation.StringRes

sealed class TextWrapper {
    data class StringResource(@StringRes val resourceId: Int) : TextWrapper()

    data class PlainText(val value: String) : TextWrapper()
}
