package ru.konohovalex.feature.preferences.presentation.model

import ru.konohovalex.core.ui.compose.model.TextWrapper
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.presentation.R

internal enum class LanguageUiModel(
    val languageCode: String,
    val textWrapper: TextWrapper.StringResource,
) {
    ENG(
        languageCode = LanguageDomainModel.ENG.languageCode,
        textWrapper = TextWrapper.StringResource(resourceId = R.string.language_tumbler_english),
    ),
    RUS(
        languageCode = LanguageDomainModel.RUS.languageCode,
        textWrapper = TextWrapper.StringResource(resourceId = R.string.language_tumbler_russian),
    ),
}
