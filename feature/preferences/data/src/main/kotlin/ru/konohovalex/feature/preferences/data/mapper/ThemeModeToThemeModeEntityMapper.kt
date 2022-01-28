package ru.konohovalex.feature.preferences.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.model.entity.ThemeModeEntity
import javax.inject.Inject

class ThemeModeToThemeModeEntityMapper
@Inject constructor() : Mapper<ThemeMode, ThemeModeEntity> {
    override fun invoke(input: ThemeMode) = when (input) {
        ThemeMode.LIGHT -> ThemeModeEntity.LIGHT
        ThemeMode.SYSTEM -> ThemeModeEntity.SYSTEM
        ThemeMode.DARK -> ThemeModeEntity.DARK
    }
}
