package ru.konohovalex.feature.account.data.profile.source.storage.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import ru.konohovalex.core.utils.extension.fromJson
import ru.konohovalex.feature.account.data.profile.model.entity.ProfileEntity
import ru.konohovalex.feature.account.data.profile.source.storage.contract.ProfileStorageContract
import javax.inject.Inject

internal class ProfileStorageImpl
@Inject constructor(
    private val preferencesDataStore: DataStore<Preferences>,
    private val gson: Gson,
) : ProfileStorageContract {
    companion object {
        private val KEY_PROFILE = stringPreferencesKey(name = "key_profile")
    }

    override suspend fun getProfile(): ProfileEntity? =
        preferencesDataStore
            .data
            .firstOrNull()
            ?.get(KEY_PROFILE)
            ?.let { gson.fromJson<ProfileEntity>(it) }

    override suspend fun updateProfile(profileEntity: ProfileEntity): ProfileEntity {
        preferencesDataStore
            .edit {
                it[KEY_PROFILE] = gson.toJson(profileEntity)
            }
        return profileEntity
    }

    override suspend fun getAuthToken(): String? = getProfile()?.authToken

    override suspend fun getRefreshToken(): String? = getProfile()?.refreshToken
}
