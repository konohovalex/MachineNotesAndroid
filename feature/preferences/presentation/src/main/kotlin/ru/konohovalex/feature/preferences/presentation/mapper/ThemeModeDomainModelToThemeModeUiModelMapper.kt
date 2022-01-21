package ru.konohovalex.feature.preferences.presentation.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel
import javax.inject.Inject

internal class ThemeModeDomainModelToThemeModeUiModelMapper
@Inject constructor() : Mapper<ThemeModeDomainModel, ThemeModeUiModel> {
    override fun invoke(input: ThemeModeDomainModel) = when (input) {
        ThemeModeDomainModel.LIGHT -> ThemeModeUiModel.LIGHT
        ThemeModeDomainModel.SYSTEM -> ThemeModeUiModel.SYSTEM
        ThemeModeDomainModel.DARK -> ThemeModeUiModel.DARK
    }
}
