package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.ui.model.TextWrapper
import ru.konohovalex.feature.preferences.presentation.R

enum class LanguageUiModel(
    val languageCode: LanguageCode,
    val textWrapper: TextWrapper.StringResource,
) {
    ENG(
        languageCode = LanguageCode.ENG,
        textWrapper = TextWrapper.StringResource(resourceId = R.string.language_tumbler_english),
    ),
    RUS(
        languageCode = LanguageCode.RUS,
        textWrapper = TextWrapper.StringResource(resourceId = R.string.language_tumbler_russian),
    ),
}
