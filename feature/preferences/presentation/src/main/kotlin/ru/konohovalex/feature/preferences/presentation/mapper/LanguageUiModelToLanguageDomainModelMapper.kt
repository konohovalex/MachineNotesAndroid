package ru.konohovalex.feature.preferences.presentation.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import javax.inject.Inject

internal class LanguageUiModelToLanguageDomainModelMapper
@Inject constructor() : Mapper<LanguageUiModel, LanguageDomainModel> {
    override fun invoke(input: LanguageUiModel) = when (input) {
        LanguageUiModel.ENG -> LanguageDomainModel.ENG
        LanguageUiModel.RUS -> LanguageDomainModel.RUS
    }
}
