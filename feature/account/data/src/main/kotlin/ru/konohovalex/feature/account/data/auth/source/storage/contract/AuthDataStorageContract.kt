package ru.konohovalex.feature.account.data.auth.source.storage.contract

import ru.konohovalex.core.data.arch.source.storage.Storage
import ru.konohovalex.feature.account.data.auth.model.entity.AuthTokenEntity

interface AuthDataStorageContract : Storage {
    companion object {
        const val PREFERENCES_FILE_NAME = "auth_data_preferences"
    }

    fun getAuthToken(): AuthTokenEntity?
    fun updateAuthToken(authToken: AuthTokenEntity): AuthTokenEntity

    fun getUserId(): String?
    fun updateUserId(userId: String): String
}
