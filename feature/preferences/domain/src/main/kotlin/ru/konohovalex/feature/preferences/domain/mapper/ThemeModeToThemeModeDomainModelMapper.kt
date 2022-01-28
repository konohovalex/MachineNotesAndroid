package ru.konohovalex.feature.preferences.domain.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import javax.inject.Inject

internal class ThemeModeToThemeModeDomainModelMapper
@Inject constructor() : Mapper<ThemeMode, ThemeModeDomainModel> {
    override fun invoke(input: ThemeMode) = when (input) {
        ThemeMode.LIGHT -> ThemeModeDomainModel.LIGHT
        ThemeMode.SYSTEM -> ThemeModeDomainModel.SYSTEM
        ThemeMode.DARK -> ThemeModeDomainModel.DARK
    }
}
