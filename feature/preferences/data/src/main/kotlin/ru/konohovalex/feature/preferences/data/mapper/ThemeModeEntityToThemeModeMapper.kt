package ru.konohovalex.feature.preferences.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.model.entity.ThemeModeEntity
import javax.inject.Inject

class ThemeModeEntityToThemeModeMapper
@Inject constructor() : Mapper<ThemeModeEntity, ThemeMode> {
    override fun invoke(input: ThemeModeEntity) = when (input) {
        ThemeModeEntity.LIGHT -> ThemeMode.LIGHT
        ThemeModeEntity.SYSTEM -> ThemeMode.SYSTEM
        ThemeModeEntity.DARK -> ThemeMode.DARK
    }
}
