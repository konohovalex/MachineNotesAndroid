package ru.konohovalex.feature.preferences.domain.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import javax.inject.Inject

internal class LanguageToLanguageDomainModelMapper
@Inject constructor() : Mapper<Language, LanguageDomainModel> {
    override fun invoke(input: Language) = when (input) {
        Language.ENG -> LanguageDomainModel.ENG
        Language.RUS -> LanguageDomainModel.RUS
    }
}
