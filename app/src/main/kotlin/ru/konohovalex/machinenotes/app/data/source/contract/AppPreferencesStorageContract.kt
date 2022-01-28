package ru.konohovalex.machinenotes.app.data.source.contract

import ru.konohovalex.core.data.arch.source.storage.Storage

interface AppPreferencesStorageContract : Storage {
    companion object {
        const val PREFERENCES_FILE_NAME = "app_preferences_storage"
    }

    suspend fun getIsFirstLaunch(): Boolean
    suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean): Boolean
}
