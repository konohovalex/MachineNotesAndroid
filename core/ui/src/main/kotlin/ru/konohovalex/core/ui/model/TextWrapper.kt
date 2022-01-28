package ru.konohovalex.core.ui.model

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

sealed class TextWrapper {
    class StringResource(
        @StringRes val resourceId: Int,
        val formatArgs: Array<Any> = arrayOf(),
    ) : TextWrapper()

    class QuantityStringResource(
        @PluralsRes val resourceId: Int,
        val quantity: Int,
        val formatArgs: Array<Any> = arrayOf(),
    ) : TextWrapper()

    data class PlainText(val value: String) : TextWrapper()

    data class Composite(
        val textWrappers: List<TextWrapper>,
        val divider: String? = null,
    ) : TextWrapper()
}
