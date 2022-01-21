package ru.konohovalex.feature.preferences.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.utils.Mapper
import ru.konohovalex.feature.preferences.domain.mapper.LanguageDomainModelToLanguageMapper
import ru.konohovalex.feature.preferences.domain.mapper.LanguageToLanguageDomainModelMapper
import ru.konohovalex.feature.preferences.domain.mapper.ThemeModeDomainModelToThemeModeMapper
import ru.konohovalex.feature.preferences.domain.mapper.ThemeModeToThemeModeDomainModelMapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel

@Module
@InstallIn(SingletonComponent::class)
internal interface PreferencesDomainMappersModule {
    @Binds
    fun bindLanguageToLanguageDomainModelMapper(
        mapper: LanguageToLanguageDomainModelMapper,
    ): Mapper<Language, LanguageDomainModel>

    @Binds
    fun bindLanguageDomainModelToLanguageMapper(
        mapper: LanguageDomainModelToLanguageMapper,
    ): Mapper<LanguageDomainModel, Language>

    @Binds
    fun bindThemeModeToThemeModeDomainModelMapper(
        mapper: ThemeModeToThemeModeDomainModelMapper,
    ): Mapper<ThemeMode, ThemeModeDomainModel>

    @Binds
    fun bindThemeModeDomainModelToThemeModeMapper(
        mapper: ThemeModeDomainModelToThemeModeMapper,
    ): Mapper<ThemeModeDomainModel, ThemeMode>
}
