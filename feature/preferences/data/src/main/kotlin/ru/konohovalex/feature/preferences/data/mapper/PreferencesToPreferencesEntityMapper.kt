package ru.konohovalex.feature.preferences.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.Preferences
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.model.entity.LanguageEntity
import ru.konohovalex.feature.preferences.data.model.entity.PreferencesEntity
import ru.konohovalex.feature.preferences.data.model.entity.ThemeModeEntity
import javax.inject.Inject

class PreferencesToPreferencesEntityMapper
@Inject constructor(
    private val languageToLanguageEntityMapper: Mapper<Language, LanguageEntity>,
    private val themeModeToThemeModeEntityMapper: Mapper<ThemeMode, ThemeModeEntity>,
) : Mapper<Preferences, PreferencesEntity> {
    override fun invoke(input: Preferences) = with(input) {
        PreferencesEntity(
            languageEntity = languageToLanguageEntityMapper.invoke(language),
            themeModeEntity = themeModeToThemeModeEntityMapper.invoke(themeMode),
        )
    }
}
