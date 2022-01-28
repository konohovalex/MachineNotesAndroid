package ru.konohovalex.feature.preferences.data.source.storage.contract

import ru.konohovalex.core.data.arch.source.storage.Storage
import ru.konohovalex.feature.preferences.data.model.entity.LanguageEntity
import ru.konohovalex.feature.preferences.data.model.entity.ThemeModeEntity

internal interface PreferencesStorageContract : Storage {
    companion object {
        const val PREFERENCES_FILE_NAME = "preferences_storage"
    }

    suspend fun getCurrentLanguage(): LanguageEntity
    suspend fun updateCurrentLanguage(language: LanguageEntity): LanguageEntity
    suspend fun getCurrentThemeMode(): ThemeModeEntity
    suspend fun updateCurrentThemeMode(themeMode: ThemeModeEntity): ThemeModeEntity
}
