package ru.konohovalex.feature.preferences.data.source.storage.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import ru.konohovalex.feature.preferences.data.model.Language
import ru.konohovalex.feature.preferences.data.model.ThemeMode
import ru.konohovalex.feature.preferences.data.source.storage.contract.PreferencesStorageContract
import javax.inject.Inject

internal class PreferencesStorage
@Inject constructor(
    private val preferencesDataStore: DataStore<Preferences>,
) : PreferencesStorageContract {
    companion object {
        const val PREFERENCES_FILE_NAME = "preferences"

        private val KEY_LANGUAGE = stringPreferencesKey(name = "key_language")
        private val KEY_THEME_MODE = stringPreferencesKey(name = "key_theme_mode")
    }

    override suspend fun getCurrentLanguage(): Language =
        preferencesDataStore
            .data
            .firstOrNull()
            ?.get(KEY_LANGUAGE)
            ?.let { Language.valueOf(it) }
            ?: Language.ENG

    override suspend fun updateCurrentLanguage(language: Language): Language {
        preferencesDataStore
            .edit {
                it[KEY_LANGUAGE] = language.name
            }
        return getCurrentLanguage()
    }

    override suspend fun getCurrentThemeMode(): ThemeMode =
        preferencesDataStore
            .data
            .firstOrNull()
            ?.get(KEY_THEME_MODE)
            ?.let { ThemeMode.valueOf(it) }
            ?: ThemeMode.SYSTEM

    override suspend fun updateCurrentThemeMode(themeMode: ThemeMode): ThemeMode {
        preferencesDataStore
            .edit {
                it[KEY_THEME_MODE] = themeMode.name
            }
        return getCurrentThemeMode()
    }
}
