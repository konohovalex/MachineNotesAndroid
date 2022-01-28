package ru.konohovalex.feature.preferences.data.repository.impl

import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.core.utils.model.OperationResult
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.model.entity.LanguageEntity
import ru.konohovalex.feature.preferences.data.model.entity.ThemeModeEntity
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepositoryContract
import ru.konohovalex.feature.preferences.data.source.storage.contract.PreferencesStorageContract
import javax.inject.Inject

internal class PreferencesRepositoryImpl
@Inject constructor(
    private val preferencesStorage: PreferencesStorageContract,
    private val languageEntityToLanguageMapper: Mapper<LanguageEntity, Language>,
    private val languageToLanguageEntityMapper: Mapper<Language, LanguageEntity>,
    private val themeModeEntityToThemeModeMapper: Mapper<ThemeModeEntity, ThemeMode>,
    private val themeModeToThemeModeEntityMapper: Mapper<ThemeMode, ThemeModeEntity>,
) : PreferencesRepositoryContract {
    override suspend fun getCurrentLanguage(): OperationResult<Language> =
        try {
            val languageEntity = preferencesStorage.getCurrentLanguage()
            val language = languageEntityToLanguageMapper.invoke(languageEntity)
            OperationResult.Success(language)
        }
        catch (throwable: Throwable) {
            OperationResult.Error(throwable)
        }

    override suspend fun updateCurrentLanguage(language: Language): OperationResult<Language> =
        try {
            val languageEntity = languageToLanguageEntityMapper.invoke(language)
            val updatedLanguageEntity = preferencesStorage.updateCurrentLanguage(languageEntity)
            val updatedLanguage = languageEntityToLanguageMapper.invoke(updatedLanguageEntity)
            OperationResult.Success(updatedLanguage)
        }
        catch (throwable: Throwable) {
            OperationResult.Error(throwable)
        }

    override suspend fun getCurrentThemeMode(): OperationResult<ThemeMode> =
        try {
            val themeModeEntity = preferencesStorage.getCurrentThemeMode()
            val themeMode = themeModeEntityToThemeModeMapper.invoke(themeModeEntity)
            OperationResult.Success(themeMode)
        }
        catch (throwable: Throwable) {
            OperationResult.Error(throwable)
        }

    override suspend fun updateCurrentThemeMode(themeMode: ThemeMode): OperationResult<ThemeMode> =
        try {
            val themeModeEntity = themeModeToThemeModeEntityMapper.invoke(themeMode)
            val updatedThemeModeEntity = preferencesStorage.updateCurrentThemeMode(themeModeEntity)
            val updatedThemeMode = themeModeEntityToThemeModeMapper.invoke(updatedThemeModeEntity)
            OperationResult.Success(updatedThemeMode)
        }
        catch (throwable: Throwable) {
            OperationResult.Error(throwable)
        }
}
