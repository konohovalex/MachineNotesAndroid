package ru.konohovalex.feature.preferences.domain.model

import ru.konohovalex.feature.preferences.data.model.Language

enum class LanguageDomainModel(val languageCode: String) {
    ENG(Language.ENG.languageCode),
    RUS(Language.RUS.languageCode),
}
