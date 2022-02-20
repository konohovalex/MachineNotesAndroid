package ru.konohovalex.feature.account.data.profile.source.storage.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.konohovalex.core.utils.extension.fromJson
import ru.konohovalex.feature.account.data.profile.model.entity.ProfileEntity
import ru.konohovalex.feature.account.data.profile.source.storage.contract.ProfileStorageContract
import javax.inject.Inject

internal class ProfileStorageImpl
@Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val preferencesDataStore: DataStore<Preferences>,
    private val gson: Gson,
) : ProfileStorageContract {
    companion object {
        private val KEY_PROFILE = stringPreferencesKey(name = "key_profile")
    }

    private var profileStateFlow: StateFlow<ProfileEntity>? = null

    override suspend fun observeProfile(): StateFlow<ProfileEntity> =
        getProfileStateFlow()

    override suspend fun updateProfile(profileEntity: ProfileEntity): ProfileEntity =
        preferencesDataStore
            .edit {
                it[KEY_PROFILE] = gson.toJson(profileEntity)
            }
            .let {
                it[KEY_PROFILE]?.let(::mapJsonToProfileEntity)
                    ?: profileUpdateError(profileEntity)
            }

    private suspend fun getProfileStateFlow(): StateFlow<ProfileEntity> =
        profileStateFlow ?: initProfileStateFlow()

    private suspend fun initProfileStateFlow(): StateFlow<ProfileEntity> =
        preferencesDataStore
            .data
            .map {
                it[KEY_PROFILE]?.let(::mapJsonToProfileEntity)
                    ?: ProfileEntity.empty()
            }
            .stateIn(coroutineScope)
            .also { profileStateFlow = it }

    private fun mapJsonToProfileEntity(profileEntityJson: String): ProfileEntity =
        gson.fromJson<ProfileEntity>(profileEntityJson)

    private fun profileUpdateError(profileEntity: ProfileEntity): Nothing =
        throw IllegalStateException(
            "Expecting ${profileEntity.javaClass.simpleName} with userName: [${profileEntity.userName}], but got null",
        )
}
