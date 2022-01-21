package ru.konohovalex.feature.preferences.data.source.storage.contract

import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.core.data.arch.source.storage.Storage
import ru.konohovalex.feature.preferences.data.model.ThemeMode

internal interface PreferencesStorageContract : Storage {
    suspend fun getCurrentLanguage(): Language
    suspend fun updateCurrentLanguage(language: Language): Language
    suspend fun getCurrentThemeMode(): ThemeMode
    suspend fun updateCurrentThemeMode(themeMode: ThemeMode): ThemeMode
}
