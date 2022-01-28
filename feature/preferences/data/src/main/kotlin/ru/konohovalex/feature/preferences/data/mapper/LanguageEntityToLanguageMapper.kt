package ru.konohovalex.feature.preferences.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.entity.LanguageEntity
import javax.inject.Inject

class LanguageEntityToLanguageMapper
@Inject constructor() : Mapper<LanguageEntity, Language> {
    override fun invoke(input: LanguageEntity) = when (input) {
        LanguageEntity.ENG -> Language.ENG
        LanguageEntity.RUS -> Language.RUS
    }
}
