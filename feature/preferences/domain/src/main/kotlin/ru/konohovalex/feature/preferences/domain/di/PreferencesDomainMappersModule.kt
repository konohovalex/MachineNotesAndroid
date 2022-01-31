package ru.konohovalex.feature.preferences.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.Preferences
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.domain.mapper.LanguageDomainModelToLanguageMapper
import ru.konohovalex.feature.preferences.domain.mapper.LanguageToLanguageDomainModelMapper
import ru.konohovalex.feature.preferences.domain.mapper.PreferencesDomainModelToPreferencesMapper
import ru.konohovalex.feature.preferences.domain.mapper.PreferencesToPreferencesDomainModelMapper
import ru.konohovalex.feature.preferences.domain.mapper.ThemeModeDomainModelToThemeModeMapper
import ru.konohovalex.feature.preferences.domain.mapper.ThemeModeToThemeModeDomainModelMapper
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.domain.model.PreferencesDomainModel
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel

@Module
@DisableInstallInCheck
internal interface PreferencesDomainMappersModule {
    @Binds
    fun bindPreferencesToPreferencesDomainModelMapper(
        mapper: PreferencesToPreferencesDomainModelMapper,
    ): Mapper<Preferences, PreferencesDomainModel>

    @Binds
    fun bindLanguageToLanguageDomainModelMapper(
        mapper: LanguageToLanguageDomainModelMapper,
    ): Mapper<Language, LanguageDomainModel>

    @Binds
    fun bindLanguageDomainModelToLanguageMapper(
        mapper: LanguageDomainModelToLanguageMapper,
    ): Mapper<LanguageDomainModel, Language>


    @Binds
    fun bindPreferencesDomainModelToPreferencesMapper(
        mapper: PreferencesDomainModelToPreferencesMapper,
    ): Mapper<PreferencesDomainModel, Preferences>

    @Binds
    fun bindThemeModeToThemeModeDomainModelMapper(
        mapper: ThemeModeToThemeModeDomainModelMapper,
    ): Mapper<ThemeMode, ThemeModeDomainModel>

    @Binds
    fun bindThemeModeDomainModelToThemeModeMapper(
        mapper: ThemeModeDomainModelToThemeModeMapper,
    ): Mapper<ThemeModeDomainModel, ThemeMode>
}
