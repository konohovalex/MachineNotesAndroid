package ru.konohovalex.feature.account.data.profile.source.storage.contract

import ru.konohovalex.core.data.arch.source.storage.Storage
import ru.konohovalex.feature.account.data.profile.model.entity.ProfileEntity

internal interface ProfileStorageContract : Storage {
    companion object {
        const val PREFERENCES_FILE_NAME = "profile_preferences_storage"
    }

    suspend fun getProfile(): ProfileEntity?
    suspend fun updateProfile(profileEntity: ProfileEntity): ProfileEntity
    suspend fun getAuthToken(): String?
    suspend fun getRefreshToken(): String?
}
