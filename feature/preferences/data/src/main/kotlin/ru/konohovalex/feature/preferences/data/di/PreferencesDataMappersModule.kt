package ru.konohovalex.feature.preferences.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.mapper.LanguageEntityToLanguageMapper
import ru.konohovalex.feature.preferences.data.mapper.LanguageToLanguageEntityMapper
import ru.konohovalex.feature.preferences.data.mapper.PreferencesEntityToPreferencesMapper
import ru.konohovalex.feature.preferences.data.mapper.PreferencesToPreferencesEntityMapper
import ru.konohovalex.feature.preferences.data.mapper.ThemeModeEntityToThemeModeMapper
import ru.konohovalex.feature.preferences.data.mapper.ThemeModeToThemeModeEntityMapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.Preferences
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.model.entity.LanguageEntity
import ru.konohovalex.feature.preferences.data.model.entity.PreferencesEntity
import ru.konohovalex.feature.preferences.data.model.entity.ThemeModeEntity

@Module
@InstallIn(SingletonComponent::class)
interface PreferencesDataMappersModule {
    @Binds
    fun bindPreferencesEntityToPreferencesMapper(
        mapper: PreferencesEntityToPreferencesMapper,
    ): Mapper<PreferencesEntity, Preferences>

    @Binds
    fun bindLanguageEntityToLanguageMapper(
        mapper: LanguageEntityToLanguageMapper,
    ): Mapper<LanguageEntity, Language>

    @Binds
    fun bindLanguageToLanguageEntityMapper(
        mapper: LanguageToLanguageEntityMapper,
    ): Mapper<Language, LanguageEntity>


    @Binds
    fun bindPreferencesToPreferencesEntityMapper(
        mapper: PreferencesToPreferencesEntityMapper,
    ): Mapper<Preferences, PreferencesEntity>

    @Binds
    fun bindThemeModeEntityToThemeModeMapper(
        mapper: ThemeModeEntityToThemeModeMapper,
    ): Mapper<ThemeModeEntity, ThemeMode>

    @Binds
    fun bindThemeModeToThemeModeEntityMapper(
        mapper: ThemeModeToThemeModeEntityMapper,
    ): Mapper<ThemeMode, ThemeModeEntity>
}
