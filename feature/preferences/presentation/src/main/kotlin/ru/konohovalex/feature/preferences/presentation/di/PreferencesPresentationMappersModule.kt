package ru.konohovalex.feature.preferences.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.domain.model.LanguageDomainModel
import ru.konohovalex.feature.preferences.domain.model.ThemeModeDomainModel
import ru.konohovalex.feature.preferences.presentation.mapper.LanguageDomainModelToLanguageUiModelMapper
import ru.konohovalex.feature.preferences.presentation.mapper.LanguageUiModelToLanguageDomainModelMapper
import ru.konohovalex.feature.preferences.presentation.mapper.ThemeModeDomainModelToThemeModeUiModelMapper
import ru.konohovalex.feature.preferences.presentation.mapper.ThemeModeUiModelToThemeModeDomainModelMapper
import ru.konohovalex.feature.preferences.presentation.model.LanguageUiModel
import ru.konohovalex.feature.preferences.presentation.model.ThemeModeUiModel

@Module
@DisableInstallInCheck
internal interface PreferencesPresentationMappersModule {
    @Binds
    fun bindLanguageDomainModelToLanguageUiModelMapper(
        mapper: LanguageDomainModelToLanguageUiModelMapper,
    ): Mapper<LanguageDomainModel, LanguageUiModel>

    @Binds
    fun bindLanguageUiModelToLanguageDomainModelMapper(
        mapper: LanguageUiModelToLanguageDomainModelMapper,
    ): Mapper<LanguageUiModel, LanguageDomainModel>


    @Binds
    fun bindThemeModeDomainModelToThemeModeUiModelMapper(
        mapper: ThemeModeDomainModelToThemeModeUiModelMapper,
    ): Mapper<ThemeModeDomainModel, ThemeModeUiModel>

    @Binds
    fun bindThemeModeUiModelToThemeModeDomainModelMapper(
        mapper: ThemeModeUiModelToThemeModeDomainModelMapper,
    ): Mapper<ThemeModeUiModel, ThemeModeDomainModel>
}
