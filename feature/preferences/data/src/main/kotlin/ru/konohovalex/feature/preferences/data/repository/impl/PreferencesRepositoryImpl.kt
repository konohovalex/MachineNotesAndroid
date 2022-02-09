package ru.konohovalex.feature.preferences.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.konohovalex.core.utils.extension.withIo
import ru.konohovalex.core.utils.model.Mapper
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.Preferences
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.model.entity.PreferencesEntity
import ru.konohovalex.feature.preferences.data.repository.contract.PreferencesRepositoryContract
import ru.konohovalex.feature.preferences.data.source.storage.contract.PreferencesStorageContract
import javax.inject.Inject

internal class PreferencesRepositoryImpl
@Inject constructor(
    private val preferencesStorage: PreferencesStorageContract,
    private val preferencesEntityToPreferencesMapper: Mapper<PreferencesEntity, Preferences>,
    private val preferencesToPreferencesEntityMapper: Mapper<Preferences, PreferencesEntity>,
) : PreferencesRepositoryContract {
    // tbd should there be safe update ops?
    override suspend fun observePreferences(): Flow<Preferences> = withIo {
        preferencesStorage.observePreferences()
            .map { preferencesEntityToPreferencesMapper.invoke(it) }
    }

    override suspend fun updatePreferences(preferences: Preferences): Preferences = withIo {
        val preferencesEntity = preferencesToPreferencesEntityMapper.invoke(preferences)
        val updatedPreferencesEntity = preferencesStorage.updatePreferences(preferencesEntity)
        preferencesEntityToPreferencesMapper.invoke(updatedPreferencesEntity)
    }

    override suspend fun updateLanguage(language: Language): Language =
        updatePreferences(
            getCurrentPreferences()
                .copy(language = language)
        ).language

    override suspend fun updateThemeMode(themeMode: ThemeMode): ThemeMode =
        updatePreferences(
            getCurrentPreferences()
                .copy(themeMode = themeMode)
        ).themeMode

    private suspend fun getCurrentPreferences(): Preferences = withIo {
        preferencesStorage.observePreferences()
            .value
            .let(preferencesEntityToPreferencesMapper::invoke)
    }
}
