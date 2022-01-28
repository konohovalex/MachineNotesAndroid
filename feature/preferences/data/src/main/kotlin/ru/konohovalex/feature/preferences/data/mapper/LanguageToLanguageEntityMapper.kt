package ru.konohovalex.feature.preferences.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.entity.LanguageEntity
import javax.inject.Inject

class LanguageToLanguageEntityMapper
@Inject constructor() : Mapper<Language, LanguageEntity> {
    override fun invoke(input: Language) = when (input) {
        Language.ENG -> LanguageEntity.ENG
        Language.RUS -> LanguageEntity.RUS
    }
}
