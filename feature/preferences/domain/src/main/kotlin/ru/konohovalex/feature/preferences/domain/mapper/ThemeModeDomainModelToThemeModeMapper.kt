package ru.konohovalex.feature.preferences.domain.mapper

import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import javax.inject.Inject

internal class ThemeModeDomainModelToThemeModeMapper
@Inject constructor() : Mapper<ThemeModeDomainModel, ThemeMode> {
    override fun invoke(input: ThemeModeDomainModel) = when (input) {
        ThemeModeDomainModel.LIGHT -> ThemeMode.LIGHT
        ThemeModeDomainModel.SYSTEM -> ThemeMode.SYSTEM
        ThemeModeDomainModel.DARK -> ThemeMode.DARK
    }
}
