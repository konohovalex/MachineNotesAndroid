package ru.konohovalex.feature.preferences.presentation.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import javax.inject.Inject

internal class LanguageDomainModelToLanguageUiModelMapper
@Inject constructor() : Mapper<LanguageDomainModel, LanguageUiModel> {
    override fun invoke(input: LanguageDomainModel) = when (input) {
        LanguageDomainModel.ENG -> LanguageUiModel.ENG
        LanguageDomainModel.RUS -> LanguageUiModel.RUS
    }
}
