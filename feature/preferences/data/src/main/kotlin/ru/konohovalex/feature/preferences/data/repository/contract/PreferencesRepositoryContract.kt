package ru.konohovalex.feature.preferences.data.repository.contract

import kotlinx.coroutines.flow.Flow
import ru.konohovalex.core.data.arch.repository.Repository
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.Preferences
import ru.konohovalex.feature.preferences.data.model.ThemeMode

interface PreferencesRepositoryContract : Repository {
    suspend fun observePreferences(): Flow<Preferences>
    suspend fun updatePreferences(preferences: Preferences): Preferences
    suspend fun updateLanguage(language: Language): Language
    suspend fun updateThemeMode(themeMode: ThemeMode): ThemeMode
}
