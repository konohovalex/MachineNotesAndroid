package ru.konohovalex.feature.preferences.presentation.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel
import javax.inject.Inject

internal class ThemeModeUiModelToThemeModeDomainModelMapper
@Inject constructor() : Mapper<ThemeModeUiModel, ThemeModeDomainModel> {
    override fun invoke(input: ThemeModeUiModel) = when (input) {
        ThemeModeUiModel.LIGHT -> ThemeModeDomainModel.LIGHT
        ThemeModeUiModel.SYSTEM -> ThemeModeDomainModel.SYSTEM
        ThemeModeUiModel.DARK -> ThemeModeDomainModel.DARK
    }
}
