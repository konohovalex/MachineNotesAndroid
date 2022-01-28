package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.feature.preferences.presentation.R

internal enum class LanguageUiModel(val textWrapper: TextWrapper.StringResource) {
    ENG(TextWrapper.StringResource(resourceId = R.string.language_tumbler_english)),
    RUS(TextWrapper.StringResource(resourceId = R.string.language_tumbler_russian)),
}
