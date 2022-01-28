package ru.konohovalex.feature.preferences.data.source.storage.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import ru.konohovalex.feature.preferences.data.model.entity.LanguageEntity
import ru.konohovalex.feature.preferences.data.model.entity.ThemeModeEntity
import ru.konohovalex.feature.preferences.data.source.storage.contract.PreferencesStorageContract
import javax.inject.Inject

internal class PreferencesStorageImpl
@Inject constructor(
    private val preferencesDataStore: DataStore<Preferences>,
) : PreferencesStorageContract {
    companion object {
        private val KEY_LANGUAGE = stringPreferencesKey(name = "key_language")
        private val KEY_THEME_MODE = stringPreferencesKey(name = "key_theme_mode")
    }

    override suspend fun getCurrentLanguage(): LanguageEntity =
        preferencesDataStore
            .data
            .firstOrNull()
            ?.get(KEY_LANGUAGE)
            ?.let { LanguageEntity.valueOf(it) }
            ?: LanguageEntity.ENG

    override suspend fun updateCurrentLanguage(language: LanguageEntity): LanguageEntity {
        preferencesDataStore
            .edit {
                it[KEY_LANGUAGE] = language.name
            }
        return language
    }

    override suspend fun getCurrentThemeMode(): ThemeModeEntity =
        preferencesDataStore
            .data
            .firstOrNull()
            ?.get(KEY_THEME_MODE)
            ?.let { ThemeModeEntity.valueOf(it) }
            ?: ThemeModeEntity.SYSTEM

    override suspend fun updateCurrentThemeMode(themeMode: ThemeModeEntity): ThemeModeEntity {
        preferencesDataStore
            .edit {
                it[KEY_THEME_MODE] = themeMode.name
            }
        return themeMode
    }
}
