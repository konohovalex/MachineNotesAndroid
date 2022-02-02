package ru.konohovalex.feature.preferences.presentation.extension

import ru.konohovalex.feature.preferences.presentation.model.LanguageCode
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import java.util.Locale

fun LanguageUiModel?.localeOrDefault(default: Locale = Locale(LanguageCode.ENG.value)): Locale =
    this?.languageCode?.value?.let(::Locale) ?: default
