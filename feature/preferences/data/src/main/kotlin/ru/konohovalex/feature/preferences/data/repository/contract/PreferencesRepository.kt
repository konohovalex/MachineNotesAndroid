package ru.konohovalex.feature.preferences.data.repository.contract

import ru.konohovalex.core.data.arch.repository.Repository
import ru.konohovalex.core.data.model.OperationResult
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.ThemeMode

interface PreferencesRepository : Repository {
    suspend fun getCurrentLanguage(): OperationResult<Language>
    suspend fun updateCurrentLanguage(language: Language): OperationResult<Language>
    suspend fun getCurrentThemeMode(): OperationResult<ThemeMode>
    suspend fun updateCurrentThemeMode(themeMode: ThemeMode): OperationResult<ThemeMode>
}
