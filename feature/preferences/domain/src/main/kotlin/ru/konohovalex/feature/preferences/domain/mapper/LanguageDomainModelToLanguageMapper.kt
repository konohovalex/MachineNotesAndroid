package ru.konohovalex.feature.preferences.domain.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import javax.inject.Inject

internal class LanguageDomainModelToLanguageMapper
@Inject constructor() : Mapper<LanguageDomainModel, Language> {
    override fun invoke(input: LanguageDomainModel) = when (input) {
        LanguageDomainModel.ENG -> Language.ENG
        LanguageDomainModel.RUS -> Language.RUS
    }
}
