package ru.konohovalex.feature.preferences.domain.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.Preferences
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.domain.model.PreferencesDomainModel
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import javax.inject.Inject

class PreferencesToPreferencesDomainModelMapper
@Inject constructor(
    private val languageToLanguageDomainModelMapper: Mapper<Language, LanguageDomainModel>,
    private val themeModeToThemeModeDomainModelMapper: Mapper<ThemeMode, ThemeModeDomainModel>,
) : Mapper<Preferences, PreferencesDomainModel> {
    override fun invoke(input: Preferences) = with(input) {
        PreferencesDomainModel(
            languageDomainModel = languageToLanguageDomainModelMapper.invoke(language),
            themeModeDomainModel = themeModeToThemeModeDomainModelMapper.invoke(themeMode),
        )
    }
}
