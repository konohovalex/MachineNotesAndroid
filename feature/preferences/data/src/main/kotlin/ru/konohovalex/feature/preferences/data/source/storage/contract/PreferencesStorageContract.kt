package ru.konohovalex.feature.preferences.data.source.storage.contract

import kotlinx.coroutines.flow.StateFlow
import ru.konohovalex.core.data.arch.source.storage.Storage
import ru.konohovalex.feature.preferences.data.model.entity.PreferencesEntity

internal interface PreferencesStorageContract : Storage {
    companion object {
        const val PREFERENCES_FILE_NAME = "preferences_storage"
    }

    suspend fun observePreferences(): StateFlow<PreferencesEntity>
    suspend fun updatePreferences(preferencesEntity: PreferencesEntity): PreferencesEntity
}
