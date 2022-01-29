package ru.konohovalex.feature.preferences.data.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.Preferences
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.model.entity.LanguageEntity
import ru.konohovalex.feature.preferences.data.model.entity.PreferencesEntity
import ru.konohovalex.feature.preferences.data.model.entity.ThemeModeEntity
import javax.inject.Inject

class PreferencesEntityToPreferencesMapper
@Inject constructor(
    private val languageEntityToLanguageMapper: Mapper<LanguageEntity, Language>,
    private val themeModeEntityToThemeModeMapper: Mapper<ThemeModeEntity, ThemeMode>,
) : Mapper<PreferencesEntity, Preferences> {
    override fun invoke(input: PreferencesEntity) = with(input) {
        Preferences(
            language = languageEntityToLanguageMapper.invoke(languageEntity),
            themeMode = themeModeEntityToThemeModeMapper.invoke(themeModeEntity),
        )
    }
}
