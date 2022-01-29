package ru.konohovalex.feature.preferences.domain.mapper

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.Preferences
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.domain.model.PreferencesDomainModel
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import javax.inject.Inject

class PreferencesDomainModelToPreferencesMapper
@Inject constructor(
    private val languageDomainModelToLanguageMapper: Mapper<LanguageDomainModel, Language>,
    private val themeModeDomainModelToThemeModeMapper: Mapper<ThemeModeDomainModel, ThemeMode>,
) : Mapper<PreferencesDomainModel, Preferences> {
    override fun invoke(input: PreferencesDomainModel) = with(input) {
        Preferences(
            language = languageDomainModelToLanguageMapper.invoke(languageDomainModel),
            themeMode = themeModeDomainModelToThemeModeMapper.invoke(themeModeDomainModel),
        )
    }
}
